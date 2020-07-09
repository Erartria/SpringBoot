package com.leonid.springboot.controllers;

import com.leonid.springboot.dto.LogDTO;
import com.leonid.springboot.models.LogModel;
import com.leonid.springboot.service.LogServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController()
@AllArgsConstructor
public class LogController {
    private final LogServiceImpl logService;

    @GetMapping("/logs")
    public List<LogDTO> getLogs() {
        return fromServiceToControllerLogList(logService.getAll());
    }

    @GetMapping("/logs/{status}")
    public List<LogDTO> getLogsByTimestampAndStatus(
            @PathVariable(value = "status") String status,
            @RequestParam(value = "timestamp", required = false, defaultValue = "0") long time) {
        return fromServiceToControllerLogList(logService.getByStatusAndTimestamp(time, status));
    }

    private static LogDTO fromLogModelToLogDTO(LogModel log) {
        return new LogDTO(log.getId(), log.getProfileId(), log.getChangedTime(), log.getNewStatus());
    }

    private static List<LogDTO> fromServiceToControllerLogList(List<LogModel> logModelList) {
        List<LogDTO> logDTOList = new ArrayList<>();
        for (LogModel log :
                logModelList) {
            logDTOList.add(fromLogModelToLogDTO(log));
        }
        return logDTOList;
    }
}
