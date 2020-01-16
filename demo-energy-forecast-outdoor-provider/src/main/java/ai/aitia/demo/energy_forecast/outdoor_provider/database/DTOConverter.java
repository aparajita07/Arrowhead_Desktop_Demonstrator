package ai.aitia.demo.energy_forecast.outdoor_provider.database;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import com.aitia.demo.car_common.dto.CarResponseDTO;
import com.aitia.demo.car_common.dto.ServoResponseDTO;

import ai.aitia.demo.energy_forecast.outdoor_provider.entity.Car;

public class DTOConverter {

	
	//=================================================================================================
	// methods
	
	//-------------------------------------------------------------------------------------------------
	public static ServoResponseDTO convertCarToServoResponseDTO(final Car car) {
		Assert.notNull(car, "car is null");
		return new ServoResponseDTO(car.getId(), car.getServoPosition(), car.getLocation());
	}
	
	//-------------------------------------------------------------------------------------------------
	public static List<ServoResponseDTO> convertCarListToServoResponseDTOList(final List<Car> cars) {
		Assert.notNull(cars, "car list is null");
		final List<ServoResponseDTO> carResponse = new ArrayList<>(cars.size());
		for (final Car car : cars) {
			carResponse.add(convertCarToServoResponseDTO(car));
		}
		return carResponse;
	}

	//=================================================================================================
	// assistant methods

	//-------------------------------------------------------------------------------------------------
	public DTOConverter() {
		throw new UnsupportedOperationException(); 
	}
}
