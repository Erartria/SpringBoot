package com.leonid.springboot.entities;

import com.leonid.springboot.models.Gender;
import com.leonid.springboot.models.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "profiles",schema = "project")
@NoArgsConstructor
@Data public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_seq")
    @SequenceGenerator(name = "profile_seq", schema = "project", sequenceName = "profile_id_sequence",allocationSize = 1)
    @Column(name = "profile_id",updatable = false, nullable = false)
    private int profileId;
    private String username;
    private String email;

    /*@Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "statusValue", column = @Column(name = "status_value"))
    })*/
    @ManyToOne
    @JoinColumn(name ="status_id_fk",insertable = false, updatable = false, referencedColumnName = "status_id")
    private Status status;

   @ManyToOne
    @JoinColumn(name ="gender_id_fk",insertable = false, updatable = false, referencedColumnName = "gender_id")
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
        super();
        //this.status = status;
        this.username = username;
        this.email = email;
        //this.gender = gender;
    }

    public Profile(String username, String email) {
        super();
        this.username = username;
        this.email = email;
    }
}