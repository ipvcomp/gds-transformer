package com.ipurvey.gdstransformerservice.amadeus.repo;

import com.ipurvey.gdstransformerservice.amadeus.collections.Pnr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;



@Repository
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
        query.addCriteria( Criteria.where("bookingInfo.bookingReference").is(bookingReference));
        return  mongoTemplate.findOne(query,Pnr.class);
    }
}
