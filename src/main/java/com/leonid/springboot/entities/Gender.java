package com.leonid.springboot.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "genders", schema = "project")
public class Gender {
    @Id
    @SequenceGenerator(name = "genderIdSeq",sequenceName = "project.genders_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genderIdSeq")
    @Column(name = "id")
    private int id;

    @Getter
    @Column(name = "genderval")
    private String val;

    public Gender() {
    }
}
