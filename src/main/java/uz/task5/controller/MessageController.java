package uz.task5.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;
import uz.task5.payload.MessageDto;
import uz.task5.projection.MessageProjection;
import uz.task5.service.MessageService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class MessageController {

    private final MessageService messageService;

    @ModelAttribute("messageDto")
    public MessageDto registerDto() {
        return new MessageDto();
    }

    @MessageMapping("/usermessage")
    public void sendMessage(MessageDto messageDto) throws InterruptedException {
        messageService.sendMessage(messageDto);
    }

    @GetMapping("/api/messages/{senderId}/{receiverId}")
    public List<MessageProjection> getPersonalChatMessages(@PathVariable String receiverId,
                                                           @PathVariable String senderId){
        return messageService.getPersonalChatMessages(senderId,receiverId);
    }
}
