var stompClient = null;


alert('Hello')

function connect(currentUserId) {
    var socket = new SockJS('/chat');
    stompClient = Stomp.over(socket)
    console.log(stompClient)
    window.user = currentUserId
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/user/' + currentUserId + '/topic/messages', function (message) {
            showMessage(JSON.parse(message.body));
        })
    })
}

function sendMessage() {
    const data = JSON.stringify({
        'subject': $("#subject").val(),
        'body': $("#body").val(),
        'senderId': window.user,
        'receiverId': $("#receiverId").val()
    })
    console.log(stompClient)
    stompClient.send("/app/usermessage", {}, data);

    const dataObj = JSON.parse(data);
    showMessage(dataObj)

}


function showMessage(message) {
    var isRight = '';
    if(message.senderId==window.user){
        isRight='onright'
    }
    $("#messages").append(`<div class="message_display ${isRight}">
                    <div class="message_item">
                        <div class="message_subject">${message.subject}</div> 
                        <div class="message_body">${message.body}</div>  
                    </div>
                </div>`);
}


function getMessagesByReceiverId(receiverId) {

    fetch('/api/messages/' + window.user + '/' + receiverId)
        .then(function (res) {
            res.json()
                .then(data => {
                    data.map(message => {
                        console.log(message);
                        showMessage(message)
                    })

                })
        })

}