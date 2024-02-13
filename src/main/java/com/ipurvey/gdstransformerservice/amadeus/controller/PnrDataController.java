package com.ipurvey.gdstransformerservice.amadeus.controller;

import com.ipurvey.gdstransformerservice.amadeus.collections.PnrData;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pnr")
public class PnrDataController {


    @PostMapping("/create")
    public void createPnrData(@RequestBody PnrData pnrData) {
        System.out.println("Received PNR data to create: " + pnrData);
    }

    @GetMapping("/get/{id}")
    public PnrData getPnrData(@PathVariable String id) {
        return new PnrData(null);
    }

    @PutMapping("/update/{id}")
    public void updatePnrData(@PathVariable String id, @RequestBody PnrData updatedPnrData) {
        System.out.println("Received updated PNR data for ID " + id + ": " + updatedPnrData);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePnrData(@PathVariable String id) {
        System.out.println("Deleting PNR data for ID: " + id);
    }
}
