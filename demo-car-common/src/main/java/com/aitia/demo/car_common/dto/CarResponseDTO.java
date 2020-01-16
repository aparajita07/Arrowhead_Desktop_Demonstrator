package com.aitia.demo.car_common.dto;

//import java.io.Serializable;

public class CarResponseDTO {

	//=================================================================================================
	// members

	//private static final long serialVersionUID = -8371510478751740542L;
	
	private double id;
	private String ServiceType;
	private String Location;
	//private String Data;

	//=================================================================================================
	// methods

	//-------------------------------------------------------------------------------------------------
	public CarResponseDTO() {}
	
	//-------------------------------------------------------------------------------------------------
	public CarResponseDTO(final double d, final String ServiceType, final String Location) {
		this.id = d;
		this.ServiceType = ServiceType;
		this.Location = Location;
	}
	
	//-------------------------------------------------------------------------------------------------
	public double getId() { return id; }
	//public String getData() { return Data; }
	public String getServiceType() { return ServiceType; }
	public String getLocation() { return Location; }

	//-------------------------------------------------------------------------------------------------
	public void setId(final double id) {this.id = id; }
	//public void setData(final String Data) { this.Data = Data; }
	public void setServiceType(final String ServiceType) { this.ServiceType = ServiceType; }
	public void setLocation(final String Location) { this.Location = Location; }	
}
