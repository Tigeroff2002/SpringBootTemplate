package ru.vlsu.ispi.beans;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@Entity
@Table(name = "Reviews")
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "Text")
    private String textreview;

    @OneToOne
    private Event event;

    private float mark;

    public Review() {

    }

    public Review(String textreview, Event event, float mark){
        this.textreview = textreview;
        this.event = event;
        this.mark = mark;
    }
}