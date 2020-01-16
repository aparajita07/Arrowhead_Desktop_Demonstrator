package ai.aitia.demo.energy_forecast.outdoor_provider.database;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.jcraft.jsch.JSchException;

import ai.aitia.demo.energy_forecast.outdoor_provider.entity.Car;
import ai.aitia.demo.energy_forecast.outdoor_provider.ServoMotor;
import eu.arrowhead.common.exception.InvalidParameterException;

@Component
public class InMemoryCarDB extends ConcurrentHashMap<Double, Car> {

	//=================================================================================================
	// members
	
	//private static final long serialVersionUID = -2462387539362748691L;
	
	private double idCounter;
	
	//=================================================================================================
	// methods

	//-------------------------------------------------------------------------------------------------
	public Car create(final String ServoPosition, final String Location) throws NumberFormatException, JSchException {
		if (ServoPosition == null || ServoPosition.isBlank()) {
			throw new InvalidParameterException("Data is null or empty");
		}		
		if (Location == null || Location.isBlank()) {
			throw new InvalidParameterException("Location is null or empty");
		}
		
		idCounter=Double.parseDouble(ServoPosition);
		ServoMotor.moveServo(Integer.parseInt(ServoPosition));

		this.put(idCounter, new Car(idCounter, ServoPosition.toLowerCase().trim(), Location.toLowerCase().trim()));
		return this.get(idCounter);
	}
	
	//-------------------------------------------------------------------------------------------------
	public List<Car> getAll() {
		return List.copyOf(this.values());
	}
	
	//-------------------------------------------------------------------------------------------------
	public Car getById(final int id) {
		if (this.containsKey(id)) {
			return this.get(id);
		} else {
			throw new InvalidParameterException("id '" + id + "' not exists");
		}
	}
	
	//-------------------------------------------------------------------------------------------------
	public Car updateById(final Double id, final String ServoPosition, final String Location) {
		if (this.containsKey(id)) {
			final Car car = this.get(id);
			if (ServoPosition!= null && !ServoPosition.isBlank()) {
				car.setServoPosition(ServoPosition);
			}
			if (Location != null && !Location.isBlank()) {
				car.setLocation(Location);
			}
			this.put(id, car);
			return car;
		} else {
			throw new InvalidParameterException("id '" + id + "' not exists");
		}
	}
	
	//-------------------------------------------------------------------------------------------------
	public void removeById(final int id) {
		if (this.containsKey(id)) {
			this.remove(id);
		}
	}
}
