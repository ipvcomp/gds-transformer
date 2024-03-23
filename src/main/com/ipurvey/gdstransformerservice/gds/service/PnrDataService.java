package com.ipurvey.gdstransformerservice.gds.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.ipurvey.gdstransformerservice.amadeus.collections.Pnr;
import com.ipurvey.gdstransformerservice.amadeus.collections.PnrDto;
import com.ipurvey.gdstransformerservice.amadeus.collections.ProcessingStatus;
import com.ipurvey.gdstransformerservice.amadeus.repo.PnrDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipurvey.gdstransformerservice.amadeus.client.AmadeusClientService;
import java.util.List;

@Service
public class PnrDataService {

    private final PnrDataRepository  pnrDataRepository;
    private final AmadeusClientService amadeusClientService;

    @Autowired
    public PnrDataService( PnrDataRepository pnrDataRepository, AmadeusClientService amadeusClientService) {
        this.pnrDataRepository = pnrDataRepository;
        this.amadeusClientService = amadeusClientService;
    }

    public Pnr savePnrData(Pnr pnrData) {
        try{
            return pnrDataRepository.savePnrData(pnrData);
        }catch (Exception exception){
            exception.printStackTrace();
        }
      return  null;
    }
    public Pnr updatePnrData(String bookingReference, Pnr newPnrData) {
       Pnr pnr = pnrDataRepository.findByBookingReference(bookingReference);
        if (pnr != null) {
            pnr.setBookingInfo(newPnrData.getBookingInfo());
            pnr.setContactInfo(newPnrData.getContactInfo());
            pnr.setPassengerInfo(newPnrData.getPassengerInfo());
            pnr.setFlightInfo(newPnrData.getFlightInfo());
            pnr.setProcessingStatus(newPnrData.getProcessingStatus());
            return pnrDataRepository.savePnrData(newPnrData);
        }
        return pnr;
    }

    public PnrDto getPnrByPnrNumber(String pnr) throws JsonProcessingException {
        return amadeusClientService.getPnrData(pnr);
    }

    public List<Pnr> findByStatus(ProcessingStatus processingStatus) {
        return pnrDataRepository.findByStatus(processingStatus);
    }

    public void updateStatus(String id, Pnr pnr, ProcessingStatus processingStatus) {
         pnrDataRepository.updateStatus(id,pnr,processingStatus);
    }
}
