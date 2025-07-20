package com.AirportBag.Airport_Bag_Details.Controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.AirportBag.Airport_Bag_Details.Dto.BagScanRequest;
import com.AirportBag.Airport_Bag_Details.Model.BagScan;
import com.AirportBag.Airport_Bag_Details.Service.BaggageService;

import java.util.*;

@RestController
@RequestMapping("/baggage")
public class BaggageController {

    private final BaggageService service;

    public BaggageController(BaggageService service) {
        this.service = service;
    }

    @PostMapping("/scan")
    public ResponseEntity<Map<String, Object>> logScan(@RequestBody BagScanRequest request) {
        BagScan scan = service.logScan(request);
        Map<String, Object> response = new HashMap<>();
        response.put("scan_internal_id", scan.getId());
        response.put("status", "logged");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/scans/bag/{bagTagId}")
    public ResponseEntity<?> getBagScans(
            @PathVariable String bagTagId,
            @RequestParam(required = false) boolean latest) {

    	if (latest) {
    	    return service.getLatestScanForBag(bagTagId)
    	            .<ResponseEntity<?>>map(ResponseEntity::ok)
    	            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Scan not found"));
    	}
        return ResponseEntity.ok(service.getScansForBag(bagTagId));
    }


    @GetMapping("/scans/gate/{destinationGate}")
    public ResponseEntity<List<BagScan>> getGateScans(@PathVariable String destinationGate) {
        return ResponseEntity.ok(service.getScansForGate(destinationGate));
    }

    @GetMapping("/active/gate/{destinationGate}")
    public ResponseEntity<List<Map<String, Object>>> getActiveBags(
            @PathVariable String destinationGate,
            @RequestParam(defaultValue = "60") int since_minutes) {
        return ResponseEntity.ok(service.getActiveBagsForGate(destinationGate, since_minutes));
    }

    @GetMapping("/stats/gate-counts")
    public ResponseEntity<List<Map<String, Object>>> getGateBagCounts(
            @RequestParam(defaultValue = "60") int since_minutes) {
        return ResponseEntity.ok(service.getBagCountsPerGate(since_minutes));
    }
}
