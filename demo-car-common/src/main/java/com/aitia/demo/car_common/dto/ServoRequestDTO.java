package com.aitia.demo.car_common.dto;

public class ServoRequestDTO {
	
	//=================================================================================================
		// members

		private String ServoPosition;
		private String Location;
		//private String Data;

		//=================================================================================================
		// methods
		
		//-------------------------------------------------------------------------------------------------
		public ServoRequestDTO(final String ServoPosition, final String Location) {
			this.ServoPosition = ServoPosition;
			this.Location = Location;
		}
		
		/*public CarRequestDTO(final String Data) {
			this.Data = Data;
		}*/

		//-------------------------------------------------------------------------------------------------
		 //public String getData() { return Data; }
		  public String getServoPosition() { return ServoPosition; }
		  public String getLocation() { return Location; }

		//-------------------------------------------------------------------------------------------------
		  //public void setData(final String Data) { this.Data = Data; }
		  public void setServoPosition(final String ServiceType) { this.ServoPosition = ServiceType; }
		  public void setLocation(final String Location) { this.Location = Location; }	
}
