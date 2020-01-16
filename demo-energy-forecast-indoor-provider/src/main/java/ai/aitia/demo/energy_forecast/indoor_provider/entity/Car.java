package ai.aitia.demo.energy_forecast.indoor_provider.entity;

public class Car {

	//=================================================================================================
	// members

	private final double id;
	private String ServiceType;
	private String Location;
	//private String Data;

	//=================================================================================================
	// methods

	//-------------------------------------------------------------------------------------------------
	public Car(final double idCounter, final String ServiceType, final String Location) {
		this.id = idCounter;
		this.ServiceType = ServiceType;
		this.Location = Location;
	}
	
	/*public Car(final double idCounter, final String Data) {
		this.id = idCounter;
		this.Data = Data;
	}*/
	//-------------------------------------------------------------------------------------------------
	public double getId() { return id; }
	public String getServiceType() { return ServiceType; }
	public String getLocation() { return Location; }
	//public String getData() { return Data; }

	//-------------------------------------------------------------------------------------------------
	public void setServiceType(final String ServiceType) { this.ServiceType = ServiceType; }
    public void setLocation(final String Location) { this.Location = Location; }	
	//public void setData(final String Data) { this.Data = Data; }	
}
