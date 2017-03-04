package net.ukrtel.ddns.ff.organizer.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class Task {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id", nullable = false, updatable = false)
    private User author;

    private String description;
    private long timestampOfCreation;

    @ManyToOne(optional = false)
    @JoinColumn(name = "performer_id", updatable = false)
    private User performer;

    private long timestampOfClose;

    private TaskStatus status;
}
