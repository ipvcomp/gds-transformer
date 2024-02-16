package com.ipurvey.gdstransformerservice.gds.service;

import com.ipurvey.gdstransformerservice.amadeus.collections.SchedulerLog;
import com.ipurvey.gdstransformerservice.amadeus.repo.SchedulerLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchedulerLogService {

    private final SchedulerLogRepository schedulerLogRepository;

    @Autowired
    public SchedulerLogService(SchedulerLogRepository schedulerLogRepository) {
        this.schedulerLogRepository = schedulerLogRepository;
    }

    public SchedulerLog saveSchedulerLog(SchedulerLog schedulerLog) {
        return schedulerLogRepository.save(schedulerLog);
    }

    // Add other methods as needed
}
