package com.ipurvey.gdstransformerservice.amadeus.repo;

import com.ipurvey.gdstransformerservice.amadeus.collections.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Collation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Repository
public class BookingRepository  {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public BookingRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Booking save(Booking booking){
        return mongoTemplate.save(booking);
    }


    public Booking findByBookingReference(String bookingReference) {
        Query query = new Query();
        query.collation(Collation.of(Locale.ENGLISH).strength(Collation.ComparisonLevel.secondary()));
        query.addCriteria(Criteria.where("bookingRef").is(bookingReference));

        query.fields().include("status");
        query.fields().include("bookingRef");
        query.fields().include("id");
        return mongoTemplate.findOne(query,Booking.class);
    }

    public Optional<Booking> findById(String id) {
        return null;
    }
}
