package com.eb.server.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String answer;
}

// gamePlayer will have a question that MIGHT be null
// gamePlayer will have a bonus modifier starting from 0,
// depending on the performance of the question it will recieve some points from 0 to 1.
// after 30 seconds the player will not be able to answer any more questions