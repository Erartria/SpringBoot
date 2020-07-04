package com.leonid.springboot.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "logs", schema = "project")
public class Log {
    @Id
    @SequenceGenerator(name = "logIdSeq",sequenceName = "project.logs_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "logIdSeq")
    @Column(name = "id")
    private int id;

    @Getter
    @Setter
    @Column(name = "statuschangedtime")
    private long statusChangedTime;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profileid")
    private Profile profileId;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "statusid")
    private Status newStatus;
}
