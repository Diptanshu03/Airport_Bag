package com.AirportBag.Airport_Bag_Details.repo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.AirportBag.Airport_Bag_Details.Model.BagScan;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BagScanRepository extends JpaRepository<BagScan, Long> {
    List<BagScan> findByBagTagIdOrderByScanTimestampDesc(String bagTagId);
    List<BagScan> findByDestinationGateOrderByScanTimestampDesc(String gate);
    
    Optional<BagScan> findFirstByBagTagIdOrderByScanTimestampDesc(String bagTagId);
    
    @Query("SELECT b FROM BagScan b WHERE b.destinationGate = :gate AND b.scanTimestamp >= :since")
    List<BagScan> findRecentScansByGate(String gate, LocalDateTime since);

    @Query("SELECT b.destinationGate, COUNT(DISTINCT b.bagTagId) " +
           "FROM BagScan b WHERE b.scanTimestamp >= :since " +
           "GROUP BY b.destinationGate")
    List<Object[]> countBagsPerGate(LocalDateTime since);
}

