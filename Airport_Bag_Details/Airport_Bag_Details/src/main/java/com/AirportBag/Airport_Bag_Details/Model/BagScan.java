package com.AirportBag.Airport_Bag_Details.Model;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class BagScan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bagTagId;
    private String destinationGate;
    private String locationScanned;
    private LocalDateTime scanTimestamp;

    @PrePersist
    public void prePersist() {
        scanTimestamp = LocalDateTime.now();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBagTagId() {
		return bagTagId;
	}

	public void setBagTagId(String bagTagId) {
		this.bagTagId = bagTagId;
	}

	public String getDestinationGate() {
		return destinationGate;
	}

	public void setDestinationGate(String destinationGate) {
		this.destinationGate = destinationGate;
	}

	public String getLocationScanned() {
		return locationScanned;
	}

	public void setLocationScanned(String locationScanned) {
		this.locationScanned = locationScanned;
	}

	public LocalDateTime getScanTimestamp() {
		return scanTimestamp;
	}

	public void setScanTimestamp(LocalDateTime scanTimestamp) {
		this.scanTimestamp = scanTimestamp;
	}

    
}

