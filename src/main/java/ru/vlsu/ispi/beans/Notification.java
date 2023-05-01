package ru.vlsu.ispi.beans;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.vlsu.ispi.enums.DirectionType;

import java.time.LocalDateTime;
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

    private LocalDateTime localTime;

    private boolean isViewed;

    @ManyToOne
    private User executor;

    @OneToOne
    private Action action;

    @Enumerated(value = EnumType.STRING)
    private DirectionType direction;

    public Notification() {

    }
}