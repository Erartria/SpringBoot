package com.leonid.springboot.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "logs",schema = "project")
@Data
@NoArgsConstructor
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "log_seq")
    @SequenceGenerator(name = "log_seq", schema = "project", sequenceName = "logs_id_sequence", allocationSize = 1)
    @Column(name = "log_id", updatable = false, nullable = false)
    private int logId;
    @ManyToOne
    @JoinColumn(name = "profile_id_fk", referencedColumnName = "profile_id")
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = "status_id_fk", referencedColumnName = "status_id")
    private Status status;

    private long changedTime;


    public Log(Profile profile, Status status, long changedTime) {
        this.logId = logId;
        this.profile = profile;
        this.status = status;
        this.changedTime = changedTime;
    }
}
