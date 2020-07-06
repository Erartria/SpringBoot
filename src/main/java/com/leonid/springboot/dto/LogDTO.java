package com.leonid.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogDTO {
    private int id;
    private int profileID;
    private long changedTime;
    private String newStatus;
}
