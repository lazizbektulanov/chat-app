package uz.task5.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String subject;
    @Column(columnDefinition = "text",nullable = false)
    private String body;
    @Column(nullable = false)
    private Long sender_id;
    @Column(nullable = false)
    private Long receiver_id;
    @Column(nullable = false)
    private Long chatId;
    @OrderBy
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp sentAt;

    public Message(String subject, String body, Long sender_id, Long receiver_id, Long chatId) {
        this.subject = subject;
        this.body = body;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.chatId = chatId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Message message = (Message) o;
        return id != null && Objects.equals(id, message.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
