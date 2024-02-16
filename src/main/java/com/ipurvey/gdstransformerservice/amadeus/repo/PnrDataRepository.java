package com.ipurvey.gdstransformerservice.amadeus.repo;

import com.ipurvey.gdstransformerservice.amadeus.collections.Pnr;
import com.ipurvey.gdstransformerservice.amadeus.collections.ProcessingStatus;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Slf4j
public class PnrDataRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public PnrDataRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Pnr savePnrData(Pnr pnrData) {
        return mongoTemplate.save(pnrData);
    }

    public Pnr findById(String id) {
        return mongoTemplate.findById(id, Pnr.class);
    }


    public Pnr findByBookingReference(String bookingReference) {
        Query query = new Query();
        query.addCriteria(Criteria.where("bookingInfo.bookingReference").is(bookingReference));
        return mongoTemplate.findOne(query, Pnr.class);
    }

    public List<Pnr> findByStatus(ProcessingStatus processingStatus) {
        Query query = new Query();
        query.addCriteria(Criteria.where("processingStatus").is(processingStatus));
        return mongoTemplate.find(query, Pnr.class);
    }

    public List<Pnr> findByStatusAndUpdateStatus(ProcessingStatus currentStatus, ProcessingStatus futureStatus) {
        Query query = new Query(Criteria.where("processingStatus").is(currentStatus.toString()));
        log.info("query is {} ", query);
        List<Pnr> pnrs = mongoTemplate.find(query, Pnr.class);

        return pnrs;
    }

    public void updateStatus(String id, Pnr pnr, ProcessingStatus processingStatus) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = Update.update("processingStatus", processingStatus);
        log.info("query is {} ", query);
        mongoTemplate.findAndModify(query, update, Pnr.class);
        log.info("modified   {} , {}",id, processingStatus);
    }
}
