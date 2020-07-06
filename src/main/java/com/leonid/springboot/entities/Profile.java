package com.leonid.springboot.entities;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "profiles", schema = "project")
@Data
@NoArgsConstructor
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_seq")
    @SequenceGenerator(name = "profile_seq", schema = "project", sequenceName = "profile_id_sequence", allocationSize = 1)
    @Column(name = "profile_id", updatable = false, nullable = false)
    private int profileId;
    private String username;
    private String email;

    @ManyToOne
    @JoinColumn(name = "status_id_fk", referencedColumnName = "status_id")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "gender_id_fk", referencedColumnName = "gender_id")
    private Gender gender;

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Profile(Status status, String username, String email, Gender gender) {
        this.username = username;
        this.email = email;
        this.status = status;
        this.gender = gender;

}
}