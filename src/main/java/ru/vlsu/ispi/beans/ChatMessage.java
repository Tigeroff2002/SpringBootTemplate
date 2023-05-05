package ru.vlsu.ispi.beans;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ChatMessages")
@AllArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime time;

    private String text;

    private boolean isViewed;

    private boolean isReplied;

    private boolean isEdited;

    @ManyToOne
    private Event event;

    @ManyToOne
    private User user;

    public ChatMessage(){

    }
}
