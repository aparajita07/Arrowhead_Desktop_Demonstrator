package com.aitia.demo.car_common.dto;

public class ServoResponseDTO {

	//=================================================================================================
	// members
	
	private double pos;
	private String ServoPosition;
	private String Location;

	//=================================================================================================
	// methods

	//-------------------------------------------------------------------------------------------------
	public ServoResponseDTO() {}
	
	//-------------------------------------------------------------------------------------------------
	public ServoResponseDTO(final double d, final String ServoPosition, final String Location) {
		this.pos = d;
		this.ServoPosition = ServoPosition;
		this.Location = Location;
	}
	
	//-------------------------------------------------------------------------------------------------
	public double getId() { return pos; }
	public String getServiceType() { return ServoPosition; }
	public String getLocation() { return Location; }

	//-------------------------------------------------------------------------------------------------
	public void setId(final double id) {this.pos = id; }
	public void setServiceType(final String ServiceType) { this.ServoPosition = ServiceType; }
	public void setLocation(final String Location) { this.Location = Location; }	
}
