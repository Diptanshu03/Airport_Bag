package com.AirportBag.Airport_Bag_Details.Service;

import org.springframework.stereotype.Service;

import com.AirportBag.Airport_Bag_Details.Dto.BagScanRequest;
import com.AirportBag.Airport_Bag_Details.Model.BagScan;
import com.AirportBag.Airport_Bag_Details.repo.BagScanRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class BaggageService {

    private final BagScanRepository repository;

    public BaggageService(BagScanRepository repository) {
        this.repository = repository;
    }

    public BagScan logScan(BagScanRequest req) {
        BagScan scan = new BagScan();
        scan.setBagTagId(req.getBagTagId());
        scan.setDestinationGate(req.getDestinationGate());
        scan.setLocationScanned(req.getLocationScanned());
        return repository.save(scan);
    }

    public List<BagScan> getScansForBag(String bagTagId) {
        return repository.findByBagTagIdOrderByScanTimestampDesc(bagTagId);
    }

    public Optional<BagScan> getLatestScanForBag(String bagTagId) {
        return repository.findFirstByBagTagIdOrderByScanTimestampDesc(bagTagId);
    }

    public List<BagScan> getScansForGate(String gate) {
        return repository.findByDestinationGateOrderByScanTimestampDesc(gate);
    }

    public List<Map<String, Object>> getActiveBagsForGate(String gate, int minutes) {
        LocalDateTime since = LocalDateTime.now().minusMinutes(minutes);
        List<BagScan> recentScans = repository.findRecentScansByGate(gate, since);

        Map<String, BagScan> latestByBag = new HashMap<>();
        for (BagScan scan : recentScans) {
            latestByBag.put(scan.getBagTagId(), scan); // overwrite with latest
        }

        List<Map<String, Object>> result = new ArrayList<>();
        for (BagScan scan : latestByBag.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("bag_tag_id", scan.getBagTagId());
            map.put("last_scan_at", scan.getScanTimestamp());
            map.put("last_location", scan.getLocationScanned());
            result.add(map);
        }
        return result;
    }

    public List<Map<String, Object>> getBagCountsPerGate(int minutes) {
        LocalDateTime since = LocalDateTime.now().minusMinutes(minutes);
        List<Object[]> data = repository.countBagsPerGate(since);

        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : data) {
            Map<String, Object> map = new HashMap<>();
            map.put("destination_gate", row[0]);
            map.put("unique_bag_count", row[1]);
            result.add(map);
        }
        return result;
    }
}

