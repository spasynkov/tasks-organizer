package net.ukrtel.ddns.ff.organizer.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User author;

    private String description;
    private long timestampOfCreation;

    @ManyToOne
    private User performer;

    private Long timestampOfClose;

    private TaskStatus status;
}
