package com.ipurvey.gdstransformerservice.amadeus.collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "scheduler_logs")
public class SchedulerLog extends BaseEntity {
    @Id
    private String id;
    private String schedulerName;
    private LocalDateTime lastRunAt;


}
