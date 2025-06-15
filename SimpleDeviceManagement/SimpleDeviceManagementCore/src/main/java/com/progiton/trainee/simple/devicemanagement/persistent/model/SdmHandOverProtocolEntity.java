package com.progiton.trainee.simple.devicemanagement.persistent.model;

import java.time.Instant;




import com.progiton.trainee.simple.devicemanagement.model.SdmHandOverProtocol;

import jakarta.persistence.*;


@Entity
@Table(name = "handover_protocols")
public class SdmHandOverProtocolEntity extends SdmBaseEntity<Long> implements SdmHandOverProtocol {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "device_id", nullable = false)
    private SdmDeviceEntity device;

    @ManyToOne
    @JoinColumn(name = "receiver_user_id", nullable = false)
    private SdmUserEntity receiver;

    @ManyToOne
    @JoinColumn(name = "performed_by_user_id", nullable = false)
    private SdmUserEntity performedBy;

    @Column(name = "handover_date", nullable = false)
    private Instant handoverDate;

    @Column(length = 500)
    private String comments;

    //TODO (LR) was ist mit beide Felder unten los. Warum sind die nicht in DB
    // Wenn ein Feld nicht persistier werden soll, soll als Transient bezeichnet werden.
    @Column(name = "is_confirmed")
    private Boolean isConfirmed = false;

    @Column(name = "confirmed_at")
    private Instant confirmedAt;
    
    
    public SdmHandOverProtocolEntity() {
        // Default constructor (JPA needs this)
    }

    public SdmHandOverProtocolEntity(Long id, SdmDeviceEntity device, SdmUserEntity receiver, SdmUserEntity performedBy,
    		Instant handoverDate, String comments, Boolean isConfirmed,
    		Instant confirmedAt) {
        this.id = id;
        this.device = device;
        this.receiver = receiver;
        this.performedBy = performedBy;
        this.handoverDate = handoverDate;
        this.comments = comments;
        this.isConfirmed = isConfirmed;
        this.confirmedAt = confirmedAt;
    }
    
    
 // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public SdmDeviceEntity getDevice() { return device; }
    public void setDevice(SdmDeviceEntity device) { this.device = device; }

    public SdmUserEntity getReceiver() { return receiver; }
    public void setReceiver(SdmUserEntity receiver) { this.receiver = receiver; }
    
    public SdmUserEntity getPerformedBy() { return performedBy; }
    public void setPerformedBy(SdmUserEntity performedBy) { this.performedBy = performedBy; }

    // === Interface Getters for TO projection ===
    @Override
    public String getDeviceSerialNumber() {
        return device != null ? device.getSerialNumber() : null;
    }

    @Override
    public String getReceiverUsername() {
        return receiver != null ? receiver.getUsername() : null;
    }

    @Override
    public String getPerformedByUsername() {
        return performedBy != null ? performedBy.getUsername() : null;
    }


	@Override
	public Instant getHandoverDate() {
		return handoverDate;
	}
	
	public void setHandoverDate(Instant handoverDate) { this.handoverDate = handoverDate; }

	@Override
	public String getComments() {
		return comments;
	}
	
    public void setComments(String comments) { this.comments = comments; }


	@Override
	public Boolean getIsConfirmed() {
		return isConfirmed;
	}
	

    public void setIsConfirmed(Boolean isConfirmed) { this.isConfirmed = isConfirmed; }


	@Override
	public Instant getConfirmedAt() {
		return confirmedAt;
	}
	
    public void setConfirmedAt(Instant confirmedAt) { this.confirmedAt = confirmedAt; }

}
