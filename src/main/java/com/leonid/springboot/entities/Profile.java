package com.leonid.springboot.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "profiles",schema = "project")
public class Profile {
    @Id
    @SequenceGenerator(name = "profileIdSeq",sequenceName = "project.profiles_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profileIdSeq")
    @Column(name = "id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    @Getter
    @Setter
    @Column(name = "name")
    private String name;
    @Getter
    @Setter
    @Column(name = "surname")
    private String surName;
    @Getter
    @Setter
    @Column(name = "age")
    private int age;
    @Getter
    @Setter
    @Column(name = "email")
    private String email;
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "statusid")
    private Status statusId;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "genderid")
    private Gender genderId;

    public Gender getGenderId() {
        return genderId;
    }

    public Profile() {
    }

    public Profile(String name, String surname, int age, String email, Status statusId, Gender genderId) {
        this.name = name;
        this.surName = surname;
        this.age = age;
        this.email = email;
        this.statusId = statusId;
        this.genderId = genderId;
    }
}
