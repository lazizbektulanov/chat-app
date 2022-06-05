package uz.task5.service;


import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import uz.task5.model.ChatRoom;
import uz.task5.model.Message;
import uz.task5.payload.MessageDto;
import uz.task5.payload.ResponseMessageDto;
import uz.task5.projection.MessageProjection;
import uz.task5.repository.ChatRoomRepository;
import uz.task5.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final SimpMessagingTemplate messagingTemplate;

    private final ChatRoomRepository chatRoomRepository;

    private final MessageRepository messageRepository;


    public void sendMessage(MessageDto messageDto) {
        Long chatId = getChatId(messageDto);
        Message savedMessage = messageRepository.save(new Message(
                messageDto.getSubject(),
                messageDto.getBody(),
                Long.valueOf(messageDto.getSenderId()),
                Long.valueOf(messageDto.getReceiverId()),
                chatId
        ));
        messagingTemplate.convertAndSendToUser(
                messageDto.getReceiverId(),
                "/topic/messages",
                savedMessage
        );
    }

    private Long getChatId(MessageDto messageDto) {
        if (chatRoomRepository.existsBySenderIdAndReceiverId(
                Long.valueOf(messageDto.getSenderId()),
                Long.valueOf(messageDto.getReceiverId())
        )) {
            ChatRoom chatRoomBySenderIdAndReceiverId =
                    chatRoomRepository.getBySenderIdAndReceiverId(
                            Long.valueOf(messageDto.getSenderId()),
                            Long.valueOf(messageDto.getReceiverId())
                    );
            return chatRoomBySenderIdAndReceiverId.getChatId();
        } else if (chatRoomRepository.existsByReceiverIdAndSenderId(
                Long.valueOf(messageDto.getSenderId()),
                Long.valueOf(messageDto.getReceiverId())
        )) {
            ChatRoom chatRoomByReceiverIdAndSenderId =
                    chatRoomRepository.getByReceiverIdAndSenderId(
                            Long.valueOf(messageDto.getSenderId()),
                            Long.valueOf(messageDto.getReceiverId())
                    );
            return chatRoomByReceiverIdAndSenderId.getChatId();
        } else {
            Long chatId = Long.valueOf(messageDto.getSenderId() + messageDto.getReceiverId());
            chatRoomRepository.save(new ChatRoom(
                    chatId,
                    Long.valueOf(messageDto.getSenderId()),
                    Long.valueOf(messageDto.getReceiverId())
            ));
            chatRoomRepository.save(new ChatRoom(
                    chatId,
                    Long.valueOf(messageDto.getReceiverId()),
                    Long.valueOf(messageDto.getSenderId())
            ));
            return chatId;
        }
    }

    public List<MessageProjection> getPersonalChatMessages(String senderId, String receiverId) {
        Long chatId = Long.valueOf(senderId+receiverId);
        List<MessageProjection> messagesByChatId = messageRepository.findMessagesByChatId(chatId);
        return messagesByChatId;
    }
}
