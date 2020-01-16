package com.aitia.demo.car_common.dto;

//import java.io.Serializable;

public class CarRequestDTO  {

	//=================================================================================================
	// members

	//private static final long serialVersionUID = -5363562707054976998L;

	private String ServiceType;
	private String Location;
	//private String Data;

	//=================================================================================================
	// methods
	
	//-------------------------------------------------------------------------------------------------
	public CarRequestDTO(final String ServiceType, final String Location) {
		this.ServiceType = ServiceType;
		this.Location = Location;
	}
	
	/*public CarRequestDTO(final String Data) {
		this.Data = Data;
	}*/

	//-------------------------------------------------------------------------------------------------
	 //public String getData() { return Data; }
	  public String getServiceType() { return ServiceType; }
	  public String getLocation() { return Location; }

	//-------------------------------------------------------------------------------------------------
	  //public void setData(final String Data) { this.Data = Data; }
	  public void setServiceType(final String ServiceType) { this.ServiceType = ServiceType; }
	  public void setLocation(final String Location) { this.Location = Location; }	
}
