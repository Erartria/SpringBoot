package com.leonid.springboot.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "statuses", schema = "project")
public class Status {
    @Id
    @SequenceGenerator(name = "genderIdSeq",sequenceName = "genders_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genderIdSeq")
    @Column(name = "id")
    private int id;

    @Getter
    @Setter
    @Column(name = "statusval")
    private String val;

    public Status() {
    }

    public Status(String status) {
        this.val=status;
    }

}
