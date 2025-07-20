package com.AirportBag.Airport_Bag_Details.Dto;



public class BagScanRequest {
    private String bagTagId;
    private String destinationGate;
    private String locationScanned;
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
    
}

