package net.ukrtel.ddns.ff.organizer.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table
public class User {
    @Id
    @GeneratedValue
    Long id;

    private String username;
    private String password;
    private boolean isAdmin;
}
