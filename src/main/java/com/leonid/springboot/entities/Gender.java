package com.leonid.springboot.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "genderes", schema = "project")
@NoArgsConstructor
@Data
public class Gender {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gender_seq")
    @SequenceGenerator(name = "gender_seq", schema = "project", sequenceName = "gender_id_sequence", allocationSize = 1)
    @Column(name = "gender_id", updatable = false, nullable = false)
    private int genderId;
    private String genderValue;


    public Gender(String genderValue) {
        this.genderValue = genderValue;
    }

    public Gender(int genderId, String genderValue) {
        this.genderId = genderId;
        this.genderValue = genderValue;
    }

    @Override
    public String toString() {
        return "Gender{" +
                "genderId=" + genderId +
                ", genderValue='" + genderValue +
                '}';
    }
}
