package uz.task5.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.task5.model.Message;
import uz.task5.projection.MessageProjection;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {


    @Query(nativeQuery = true,
    value = "select m.id as id, m.subject as subject, m.body as body, " +
            "m.sender_id as senderId, m.receiver_id as receiverId from messages m " +
            "where m.chat_id=:chatId")
    List<MessageProjection> findMessagesByChatId(Long chatId);
}
