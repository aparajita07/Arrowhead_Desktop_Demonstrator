package ai.aitia.demo.energy_forecast.outdoor_provider.entity;

public class Car {

	//=================================================================================================
	// members

	private final double id;
	private String ServoPosition;
	private String Location;
	//private String Data;

	//=================================================================================================
	// methods

	//-------------------------------------------------------------------------------------------------
	public Car(final double idCounter, final String ServoPosition, final String Location) {
		this.id = idCounter;
		this.ServoPosition = ServoPosition;
		this.Location = Location;
	}
	
	/*public Car(final double idCounter, final String Data) {
		this.id = idCounter;
		this.Data = Data;
	}*/
	//-------------------------------------------------------------------------------------------------
	public double getId() { return id; }
	public String getServoPosition() { return ServoPosition; }
	public String getLocation() { return Location; }
	//public String getData() { return Data; }

	//-------------------------------------------------------------------------------------------------
	public void setServoPosition(final String ServoPosition) { this.ServoPosition = ServoPosition; }
    public void setLocation(final String Location) { this.Location = Location; }	
	//public void setData(final String Data) { this.Data = Data; }	
}
