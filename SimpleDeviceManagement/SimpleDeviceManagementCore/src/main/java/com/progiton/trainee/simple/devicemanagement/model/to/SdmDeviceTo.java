package com.progiton.trainee.simple.devicemanagement.model.to;

import java.time.Instant;
import java.time.LocalDate;
import com.progiton.trainee.simple.devicemanagement.model.SdmDevice;
import com.progiton.trainee.simple.devicemanagement.model.SdmUser;


public class SdmDeviceTo extends SdmBaseTO implements SdmDevice {

    private String name; 
    private String type;
    private String status;
    private SdmUser<SdmDeviceTo> user;
    private String serialNumber;
    private String manufacturer;
    private String location;
    private LocalDate purchaseDate;
    
 // Constructors
    public SdmDeviceTo() {
    	super();
    }
    public SdmDeviceTo(String name,
            String type,
            String status,
            SdmUser<SdmDeviceTo> user,
            String serialNumber,
            String manufacturer,
            String location,
            LocalDate purchaseDate,
            Instant createdAt,
            Instant updatedAt) {
			super(createdAt, updatedAt);
			this.name = name;
			this.type = type;
			this.status = status;
			this.user = user;
			this.serialNumber = serialNumber;
			this.manufacturer = manufacturer;
			this.location = location;
			this.purchaseDate = purchaseDate;
			}
    
    
 // === Getters and Setters ===

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    @Override
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SdmUser<SdmDeviceTo> getUser() {
        return user;
    }

    public void setUser(SdmUser<SdmDeviceTo> user) {
        this.user = user;
    }

}