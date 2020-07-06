package com.leonid.springboot.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "profiles", schema = "project")
@NoArgsConstructor
@Data
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_seq")
    @SequenceGenerator(name = "profile_seq", schema = "project", sequenceName = "profile_id_sequence", allocationSize = 1)
    @Column(name = "profile_id", updatable = false, nullable = false)
    private int profileId;
    private String username;
    private String email;

    /*@Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "statusValue", column = @Column(name = "status_value"))
    })*/
    @ManyToOne
    @JoinColumn(name = "status_id_fk", referencedColumnName = "status_id")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "gender_id_fk", referencedColumnName = "gender_id")
    private Gender gender;

    public Profile(Status status, String username, String email, Gender gender) {
        this.status = status;
        this.username = username;
        this.email = email;
        this.gender = gender;
    }

    public Profile(String username, String email) {
        this.username = username;
        this.email = email;
    }
}