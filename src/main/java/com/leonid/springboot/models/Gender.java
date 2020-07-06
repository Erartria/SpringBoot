package com.leonid.springboot.models;

import com.leonid.springboot.entities.Profile;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "genderes",schema = "project")
@NoArgsConstructor
public class Gender {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gender_seq")
    @SequenceGenerator(name = "gender_seq", schema = "project", sequenceName = "gender_id_sequence",allocationSize = 1)
    @Column(name = "gender_id",updatable = false, nullable = false)
    private int genderId;

    //@Column(unique = true)
    private String genderValue;


    public Gender(String genderValue) {
        this.genderValue = genderValue;
    }



    public int getGenderId() {
        return genderId;
    }

    public void setGenderId(int genderId) {
        this.genderId = genderId;
    }

    public String getGenderValue() {
        return genderValue;
    }

    public void setGenderValue(String genderValue) {
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
