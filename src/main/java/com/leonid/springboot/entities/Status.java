package com.leonid.springboot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "statuses", schema = "project")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "status_seq")
    @SequenceGenerator(name = "status_seq", schema = "project", sequenceName = "status_id_sequence", allocationSize = 1)
    @Column(name = "status_id", updatable = false)
    private int statusId;
    private String statusValue;

    public Status(String statusValue) {
        this.statusValue = statusValue;
    }
}
