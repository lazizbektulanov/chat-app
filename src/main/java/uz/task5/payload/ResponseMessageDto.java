package uz.task5.payload;

import lombok.Data;

@Data
public class ResponseMessageDto {

    private String subject;
    private String senderId;
    private String receiverId;
    private String body;
}
