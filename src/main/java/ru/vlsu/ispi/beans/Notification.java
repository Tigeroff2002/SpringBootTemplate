package ru.vlsu.ispi.beans;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@Entity
@Table(name = "Notifications")
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "Text")
    private String text;

    private LocalTime time;

    @ManyToOne
    private User executor;

    public Notification() {

    }

    public Notification(String text, LocalTime time, User executor){
        this.text = text;
        this.time = time;
        this.executor = executor;
    }
}