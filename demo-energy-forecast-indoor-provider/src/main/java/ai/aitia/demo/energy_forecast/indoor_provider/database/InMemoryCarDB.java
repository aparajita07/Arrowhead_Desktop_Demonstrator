package ai.aitia.demo.energy_forecast.indoor_provider.database;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.jcraft.jsch.JSchException;

import ai.aitia.demo.energy_forecast.indoor_provider.entity.Car;
import ai.aitia.demo.energy_forecast.indoor_provider.TemperatureReader;
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
	public Car create(final String Data, final String Location) throws JSchException {
		if (Data == null || Data.isBlank()) {
			throw new InvalidParameterException("Data is null or empty");
		}		
		if (Location == null || Location.isBlank()) {
			throw new InvalidParameterException("Location is null or empty");
		}
		
		//idCounter++;
		
		idCounter=TemperatureReader.readTemperature();
		this.put(idCounter, new Car(idCounter, Data.toLowerCase().trim(), Location.toLowerCase().trim()));
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
	public Car updateById(final Double id, final String ServiceType, final String Location) {
		if (this.containsKey(id)) {
			final Car car = this.get(id);
			if (ServiceType!= null && !ServiceType.isBlank()) {
				car.setServiceType(ServiceType);
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
