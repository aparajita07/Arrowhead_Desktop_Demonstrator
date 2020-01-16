package ai.aitia.demo.energy_forecast.outdoor_provider.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aitia.demo.car_common.dto.ServoRequestDTO;
import com.aitia.demo.car_common.dto.ServoResponseDTO;
import com.jcraft.jsch.JSchException;

import ai.aitia.demo.energy_forecast.outdoor_provider.CarProviderConstants;
//import ai.aitia.demo.energy_forecast.outdoor_provider.TemperatureReader;
import ai.aitia.demo.energy_forecast.outdoor_provider.database.DTOConverter;
import ai.aitia.demo.energy_forecast.outdoor_provider.database.InMemoryCarDB;
import ai.aitia.demo.energy_forecast.outdoor_provider.entity.Car;
import eu.arrowhead.common.exception.BadPayloadException;

@RestController
@RequestMapping(CarProviderConstants.CAR_URI)
public class OutdoorServiceController {
	
	//=================================================================================================
	// members

	
	
	@Autowired
	private InMemoryCarDB carDB;
	//static TemperatureReader tempReader;
	
		//------

	//=================================================================================================
	// methods

	//-------------------------------------------------------------------------------------------------
	/*@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody public List<CarResponseDTO> getCars(@RequestParam(name = CarProviderConstants.REQUEST_PARAM_ServoPosition, required = false) final String ServoPosition,
													  @RequestParam(name = CarProviderConstants.REQUEST_PARAM_Location, required = false) final String Location) {
		final List<CarResponseDTO> response = new ArrayList<>();
		for (final Car car : carDB.getAll()) {
			boolean toAdd = true;
			if (ServoPosition != null && !ServoPosition.isBlank() && !car.getServoPosition().equalsIgnoreCase(ServoPosition)) {
				toAdd = false;
			}
			if (Location != null && !Location.isBlank() && !car.getLocation().equalsIgnoreCase(Location)) {
				toAdd = false;
			}
			if (toAdd) {
				response.add(DTOConverter.convertCarToCarResponseDTO(car));
			}
		}
		return response;
	}
	
	//-------------------------------------------------------------------------------------------------
	
	@GetMapping(path = CarProviderConstants.BY_ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody public CarResponseDTO getCarById(@PathVariable(value = CarProviderConstants.PATH_VARIABLE_ID) final int id) {
		return DTOConverter.convertCarToCarResponseDTO(carDB.getById(id));
	}
	
	//-------------------------------------------------------------------------------------------------
	/*@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody public CarResponseDTO createCar(@RequestBody final CarRequestDTO dto) {
		if (dto.getServiceType() == null || dto.getServiceType().isBlank()) {
			throw new BadPayloadException("ServiceType is null or blank");
		}
		if (dto.getLocation() == null || dto.getLocation().isBlank()) {
			throw new BadPayloadException("Location is null or blank");
		}
		final Car car = carDB.create(dto.getServiceType(), dto.getLocation());
		return DTOConverter.convertCarToCarResponseDTO(car);
	}*/
	
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody public ServoResponseDTO createCar(@RequestBody final ServoRequestDTO dto) throws NumberFormatException, JSchException {
		if (dto.getServoPosition() == null || dto.getServoPosition().isBlank()) {
			throw new BadPayloadException("ServoPosition is null or blank");
		}
		if (dto.getLocation() == null || dto.getLocation().isBlank()) {
			throw new BadPayloadException("Location is null or blank");
		}
		final Car car = carDB.create(dto.getServoPosition(), dto.getLocation());
		return DTOConverter.convertCarToServoResponseDTO(car);
	}
	
	
	//-------------------------------------------------------------------------------------------------
	/*@PutMapping(path = CarProviderConstants.BY_ID_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody public ServoResponseDTO updateCar(@PathVariable(name = CarProviderConstants.PATH_VARIABLE_ID) final Double id, @RequestBody final ServoRequestDTO dto) {
		if (dto.getServoPosition() == null || dto.getServoPosition().isBlank()) {
			throw new BadPayloadException("ServiceType is null or blank");
		}
		if (dto.getLocation() == null || dto.getLocation().isBlank()) {
			throw new BadPayloadException("Location is null or blank");
		}
		final Car car = carDB.updateById(id, dto.getServiceType(), dto.getLocation());
		return DTOConverter.convertCarToServoResponseDTO(car);
	}*/
	

	//-------------------------------------------------------------------------------------------------
	@DeleteMapping(path = CarProviderConstants.BY_ID_PATH)
	public void removeCarById(@PathVariable(value = CarProviderConstants.PATH_VARIABLE_ID) final int id) {
		carDB.removeById(id);
	}
}
