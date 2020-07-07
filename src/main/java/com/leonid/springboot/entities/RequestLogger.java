package com.leonid.springboot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "requestLogs", schema = "project")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestLogger {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reqLog_seq")
    @SequenceGenerator(name = "reqLog_seq", schema = "project", sequenceName = "reqLog_id_sequence", allocationSize = 1)
    @Column(name = "requestLog_id", updatable = false, nullable = false)
    private int rlogId;

    private long timestamp;
    private String statusValue;
    private long requestedTime;

    public RequestLogger(long timestamp, String status, long requestedTime) {
        this.timestamp = timestamp;
        this.statusValue = status;
        this.requestedTime = requestedTime;
    }
}
