package com.ipurvey.gdstransformerservice.amadeus.repo;

import com.ipurvey.gdstransformerservice.amadeus.collections.SchedulerLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SchedulerLogRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public SchedulerLogRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public SchedulerLog save(SchedulerLog schedulerLog) {
        return mongoTemplate.save(schedulerLog);
    }

}
