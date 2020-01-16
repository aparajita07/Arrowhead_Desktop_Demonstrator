package ai.aitia.demo.car_provider.controller;

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

import com.aitia.demo.car_common.dto.CarRequestDTO;
import com.aitia.demo.car_common.dto.CarResponseDTO;
import com.jcraft.jsch.JSchException;

import ai.aitia.demo.car_provider.CarProviderConstants;
import ai.aitia.demo.car_provider.TemperatureReader;
import ai.aitia.demo.car_provider.database.DTOConverter;
import ai.aitia.demo.car_provider.database.InMemoryCarDB;
import ai.aitia.demo.car_provider.entity.Car;
import eu.arrowhead.common.exception.BadPayloadException;

@RestController
@RequestMapping(CarProviderConstants.CAR_URI)
public class CarServiceController {
	
	//=================================================================================================
	// members

	
	
	@Autowired
	private InMemoryCarDB carDB;
	static TemperatureReader tempReader;
	
		//------

	//=================================================================================================
	// methods

	//-------------------------------------------------------------------------------------------------
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody public List<CarResponseDTO> getCars(@RequestParam(name = CarProviderConstants.REQUEST_PARAM_ServiceType, required = false) final String ServiceType,
													  @RequestParam(name = CarProviderConstants.REQUEST_PARAM_Location, required = false) final String Location) {
		final List<CarResponseDTO> response = new ArrayList<>();
		for (final Car car : carDB.getAll()) {
			boolean toAdd = true;
			if (ServiceType != null && !ServiceType.isBlank() && !car.getServiceType().equalsIgnoreCase(ServiceType)) {
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
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody public CarResponseDTO createCar(@RequestBody final CarRequestDTO dto) throws JSchException {
		if (dto.getServiceType() == null || dto.getServiceType().isBlank()) {
			throw new BadPayloadException("ServiceType is null or blank");
		}
		if (dto.getLocation() == null || dto.getLocation().isBlank()) {
			throw new BadPayloadException("Location is null or blank");
		}
		final Car car = carDB.create(dto.getServiceType(), dto.getLocation());
		return DTOConverter.convertCarToCarResponseDTO(car);
	}
	
	//-------------------------------------------------------------------------------------------------
	@PutMapping(path = CarProviderConstants.BY_ID_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody public CarResponseDTO updateCar(@PathVariable(name = CarProviderConstants.PATH_VARIABLE_ID) final Double id, @RequestBody final CarRequestDTO dto) {
		if (dto.getServiceType() == null || dto.getServiceType().isBlank()) {
			throw new BadPayloadException("ServiceType is null or blank");
		}
		if (dto.getLocation() == null || dto.getLocation().isBlank()) {
			throw new BadPayloadException("Location is null or blank");
		}
		final Car car = carDB.updateById(id, dto.getServiceType(), dto.getLocation());
		return DTOConverter.convertCarToCarResponseDTO(car);
	}
	
	/*@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody public List<CarResponseDTO> getCars(@RequestParam(name = CarProviderConstants.REQUEST_PARAM_TEMP, required = false) final String Data) {
		final List<CarResponseDTO> response = new ArrayList<>();
		for (final Car car : carDB.getAll()) {
			boolean toAdd = true;
			if (Data != null && !Data.isBlank() && !car.getData().equalsIgnoreCase(Data)) {
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
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody public CarResponseDTO createCar(@RequestBody final CarRequestDTO dto) {
		if (dto.getData() == null || dto.getData().isBlank()) {
			throw new BadPayloadException("Data is null or blank");
		}
		
		final Car car = carDB.create(dto.getData());
		return DTOConverter.convertCarToCarResponseDTO(car);
	}
	
	//-------------------------------------------------------------------------------------------------
	@PutMapping(path = CarProviderConstants.BY_ID_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody public CarResponseDTO updateCar(@PathVariable(name = CarProviderConstants.PATH_VARIABLE_ID) final Double id, @RequestBody final CarRequestDTO dto) {
		if (dto.getData() == null || dto.getData().isBlank()) {
			throw new BadPayloadException("ServiceType is null or blank");
		}
		
		final Car car = carDB.updateById(id, dto.getData());
		return DTOConverter.convertCarToCarResponseDTO(car);
	}*/
	
	//-------------------------------------------------------------------------------------------------
	@DeleteMapping(path = CarProviderConstants.BY_ID_PATH)
	public void removeCarById(@PathVariable(value = CarProviderConstants.PATH_VARIABLE_ID) final int id) {
		carDB.removeById(id);
	}
}
