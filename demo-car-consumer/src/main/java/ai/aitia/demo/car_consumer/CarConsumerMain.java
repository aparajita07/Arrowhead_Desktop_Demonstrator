package ai.aitia.demo.car_consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;

import com.aitia.demo.car_common.dto.CarRequestDTO;
import com.aitia.demo.car_common.dto.CarResponseDTO;
import com.aitia.demo.car_common.dto.ServoRequestDTO;
import com.aitia.demo.car_common.dto.ServoResponseDTO;

import eu.arrowhead.client.library.ArrowheadService;
import eu.arrowhead.common.CommonConstants;
import eu.arrowhead.common.SSLProperties;
import eu.arrowhead.common.Utilities;
import eu.arrowhead.common.dto.shared.OrchestrationFlags.Flag;
import eu.arrowhead.common.dto.shared.OrchestrationFormRequestDTO;
import eu.arrowhead.common.dto.shared.OrchestrationFormRequestDTO.Builder;
import eu.arrowhead.common.dto.shared.OrchestrationResponseDTO;
import eu.arrowhead.common.dto.shared.OrchestrationResultDTO;
import eu.arrowhead.common.dto.shared.ServiceInterfaceResponseDTO;
import eu.arrowhead.common.dto.shared.ServiceQueryFormDTO;
import eu.arrowhead.common.exception.InvalidParameterException;

@SpringBootApplication
@ComponentScan(basePackages = {CommonConstants.BASE_PACKAGE, CarConsumerConstants.BASE_PACKAGE})
public class CarConsumerMain implements ApplicationRunner {
    
    //=================================================================================================
	// members
	
    @Autowired
	private ArrowheadService arrowheadService;
 
    @Autowired
	protected SSLProperties sslProperties;
    
    private final Logger logger = LogManager.getLogger(CarConsumerMain.class);
    
    //=================================================================================================
	// methods

	//------------------------------------------------------------------------------------------------
    public static void main( final String[] args ) {
    	SpringApplication.run(CarConsumerMain.class, args);
    }

    //-------------------------------------------------------------------------------------------------
    @Override
	public void run(final ApplicationArguments args) throws Exception {
    	
    	getTempServoServiceOrchestrationAndConsumption();

	}
    
    public void getTempServoServiceOrchestrationAndConsumption() throws InterruptedException {
    	
    	double tempValue=0.00;
    	//----------------------------------------------------------------------------------------

    	logger.info("Orchestration request for " + CarConsumerConstants.GET_TEMP_SERVICE_DEFINITION + " service:");
  
    	final ServiceQueryFormDTO serviceQueryForm1 = new ServiceQueryFormDTO.Builder(CarConsumerConstants.GET_TEMP_SERVICE_DEFINITION)
    																		.interfaces(getInterface())
    																		.build();
    	
		final Builder orchestrationFormBuilder1 = arrowheadService.getOrchestrationFormBuilder();
		final OrchestrationFormRequestDTO orchestrationFormRequest1= orchestrationFormBuilder1.requestedService(serviceQueryForm1)
																					   .flag(Flag.MATCHMAKING, true)
																					   .flag(Flag.OVERRIDE_STORE, true)
																					   .build();
		
		printOut(orchestrationFormRequest1);		
		
		final OrchestrationResponseDTO orchestrationResponse1 = arrowheadService.proceedOrchestration(orchestrationFormRequest1);

		logger.info("Orchestration response for :"+ CarConsumerConstants.GET_TEMP_SERVICE_DEFINITION + " service:");
		printOut(orchestrationResponse1);	
		
		if (orchestrationResponse1 == null) {
			logger.info("No orchestration response received");
		} else if (orchestrationResponse1.getResponse().isEmpty()) {
			logger.info("No provider found during the orchestration");
		} 
			
		
		logger.info("Orchestration request for " + CarConsumerConstants.GET_SERVO_SERVICE_DEFINITION + " service:");
		  
    	final ServiceQueryFormDTO serviceQueryForm2 = new ServiceQueryFormDTO.Builder(CarConsumerConstants.GET_SERVO_SERVICE_DEFINITION)
    																		.interfaces(getInterface())
    																		.build();
    	
		final Builder orchestrationFormBuilder2 = arrowheadService.getOrchestrationFormBuilder();
		final OrchestrationFormRequestDTO orchestrationFormRequest2= orchestrationFormBuilder2.requestedService(serviceQueryForm2)
																					   .flag(Flag.MATCHMAKING, true)
																					   .flag(Flag.OVERRIDE_STORE, true)
																					   .build();

		printOut(orchestrationFormRequest2);	
		
		final OrchestrationResponseDTO orchestrationResponse2 = arrowheadService.proceedOrchestration(orchestrationFormRequest2);

		logger.info("Orchestration response for :"+ CarConsumerConstants.GET_SERVO_SERVICE_DEFINITION + " service:");
		printOut(orchestrationResponse2);
		
		if (orchestrationResponse2 == null) {
			logger.info("No orchestration response received");
		} else if (orchestrationResponse2.getResponse().isEmpty()) {
			logger.info("No provider found during the orchestration");
		}
		
		    for(int i=0;i<5;i++) {
			
		    final OrchestrationResultDTO orchestrationResult1 = orchestrationResponse1.getResponse().get(0);
			validateOrchestrationResult(orchestrationResult1, CarConsumerConstants.GET_TEMP_SERVICE_DEFINITION);

				final CarRequestDTO tempReq = new CarRequestDTO("Temperature", "Indoor");
				
			
					logger.info("Fetch Temperature Request:");
					printOut(tempReq);
					final String token = orchestrationResult1.getAuthorizationTokens() == null ? null : orchestrationResult1.getAuthorizationTokens().get(getInterface());
					final CarResponseDTO tempFetched = arrowheadService.consumeServiceHTTP(CarResponseDTO.class, HttpMethod.valueOf(orchestrationResult1.getMetadata().get(CarConsumerConstants.HTTP_METHOD)),
							orchestrationResult1.getProvider().getAddress(), orchestrationResult1.getProvider().getPort(), orchestrationResult1.getServiceUri(),
							getInterface(), token, tempReq, new String[0]);
					logger.info("Temperature Provider response");
					//printOut(carCreated);
					System.out.println("\n\nIndoor Temperature is "+tempFetched.getId()+" degree celsius");
					System.out.println("\n\n");
					tempValue=tempFetched.getId();
					Thread.sleep(2000);		

			//---------------------------------------------------------------------------------------

				final OrchestrationResultDTO orchestrationResult2 = orchestrationResponse2.getResponse().get(0);
				validateOrchestrationResult(orchestrationResult2, CarConsumerConstants.GET_SERVO_SERVICE_DEFINITION);
				
				if(tempValue >= 25.00){
					final ServoRequestDTO servoReq = new ServoRequestDTO("200", "Indoor");
					logger.info("Sending Servo Position Request:");
					printOut(servoReq);
					final String token1 = orchestrationResult2.getAuthorizationTokens() == null ? null : orchestrationResult2.getAuthorizationTokens().get(getInterface());
					final ServoResponseDTO posSent = arrowheadService.consumeServiceHTTP(ServoResponseDTO.class, HttpMethod.valueOf(orchestrationResult2.getMetadata().get(CarConsumerConstants.HTTP_METHOD)),
							orchestrationResult2.getProvider().getAddress(), orchestrationResult2.getProvider().getPort(), orchestrationResult2.getServiceUri(),
							getInterface(), token1, servoReq, new String[0]);
					logger.info("Servo Provider response");
					//printOut(carCreated);
					System.out.println("\n\nSetting the position of the Servo to "+posSent.getId()+" degrees"+" for indoor temeperature "+tempValue+" degree celsius");
					System.out.println("\n");
					Thread.sleep(2000);		
		        }
		        else if(tempValue < 25.00){
		        	final ServoRequestDTO servoReq = new ServoRequestDTO("100", "Indoor");
					logger.info("Sending Servo Position Request:");
					printOut(servoReq);
					final String token2 = orchestrationResult2.getAuthorizationTokens() == null ? null : orchestrationResult2.getAuthorizationTokens().get(getInterface());
					final ServoResponseDTO posSent = arrowheadService.consumeServiceHTTP(ServoResponseDTO.class, HttpMethod.valueOf(orchestrationResult2.getMetadata().get(CarConsumerConstants.HTTP_METHOD)),
							orchestrationResult2.getProvider().getAddress(), orchestrationResult2.getProvider().getPort(), orchestrationResult2.getServiceUri(),
							getInterface(), token2, servoReq, new String[0]);
					logger.info("Servo Provider response");
					//printOut(carCreated);
					System.out.println("\n\nSetting the position of the Servo to "+posSent.getId()+" degrees"+" for indoor temeperature "+tempValue+" degree celsius");
					System.out.println("\n");
					Thread.sleep(2000);
		        }
           }
	}
    	
    
   /* public void getTempServoServiceOrchestrationAndConsumption() throws InterruptedException {
    	double tempValue=0.00;
    	//----------------------------------------------------------------------------------------
    	
    	for (int i=0;i<3;i++) {
    		if(i==0) {
    	logger.info("Orchestration request for " + CarConsumerConstants.GET_TEMP_SERVICE_DEFINITION + " service:");
    		}
    	final ServiceQueryFormDTO serviceQueryForm1 = new ServiceQueryFormDTO.Builder(CarConsumerConstants.GET_TEMP_SERVICE_DEFINITION)
    																		.interfaces(getInterface())
    																		.build();
    	
		final Builder orchestrationFormBuilder1 = arrowheadService.getOrchestrationFormBuilder();
		final OrchestrationFormRequestDTO orchestrationFormRequest1= orchestrationFormBuilder1.requestedService(serviceQueryForm1)
																					   .flag(Flag.MATCHMAKING, true)
																					   .flag(Flag.OVERRIDE_STORE, true)
																					   .build();
		if(i==0) {
		printOut(orchestrationFormRequest1);}		
		
		final OrchestrationResponseDTO orchestrationResponse1 = arrowheadService.proceedOrchestration(orchestrationFormRequest1);
		if(i==0) {
		logger.info("Orchestration response for :"+ CarConsumerConstants.GET_TEMP_SERVICE_DEFINITION + " service:");
		printOut(orchestrationResponse1);}		
		
		if (orchestrationResponse1 == null) {
			logger.info("No orchestration response received");
		} else if (orchestrationResponse1.getResponse().isEmpty()) {
			logger.info("No provider found during the orchestration");
		} else {
			final OrchestrationResultDTO orchestrationResult1 = orchestrationResponse1.getResponse().get(0);
			validateOrchestrationResult(orchestrationResult1, CarConsumerConstants.GET_TEMP_SERVICE_DEFINITION);
			
				final CarRequestDTO tempReq = new CarRequestDTO("Temperature", "Indoor");
				
			
					logger.info("Fetch Temperature Request:");
					printOut(tempReq);
					final String token = orchestrationResult1.getAuthorizationTokens() == null ? null : orchestrationResult1.getAuthorizationTokens().get(getInterface());
					final CarResponseDTO tempFetched = arrowheadService.consumeServiceHTTP(CarResponseDTO.class, HttpMethod.valueOf(orchestrationResult1.getMetadata().get(CarConsumerConstants.HTTP_METHOD)),
							orchestrationResult1.getProvider().getAddress(), orchestrationResult1.getProvider().getPort(), orchestrationResult1.getServiceUri(),
							getInterface(), token, tempReq, new String[0]);
					logger.info("Temperature Provider response");
					//printOut(carCreated);
					System.out.println("\n\nIndoor Temperature is "+tempFetched.getId()+" degree celsius");
					System.out.println("\n\n");
					tempValue=tempFetched.getId();
					Thread.sleep(2000);		
			}
			//---------------------------------------------------------------------------------------
		    if(i==0) {
			logger.info("Orchestration request for " + CarConsumerConstants.GET_SERVO_SERVICE_DEFINITION + " service:");}
		  
	    	final ServiceQueryFormDTO serviceQueryForm2 = new ServiceQueryFormDTO.Builder(CarConsumerConstants.GET_SERVO_SERVICE_DEFINITION)
	    																		.interfaces(getInterface())
	    																		.build();
	    	
			final Builder orchestrationFormBuilder2 = arrowheadService.getOrchestrationFormBuilder();
			final OrchestrationFormRequestDTO orchestrationFormRequest2= orchestrationFormBuilder2.requestedService(serviceQueryForm2)
																						   .flag(Flag.MATCHMAKING, true)
																						   .flag(Flag.OVERRIDE_STORE, true)
																						   .build();
			if(i==0) {
			printOut(orchestrationFormRequest2);}	
			
			final OrchestrationResponseDTO orchestrationResponse2 = arrowheadService.proceedOrchestration(orchestrationFormRequest2);
			if(i==0) {
			logger.info("Orchestration response for :"+ CarConsumerConstants.GET_SERVO_SERVICE_DEFINITION + " service:");
			printOut(orchestrationResponse2);	}	
			
			if (orchestrationResponse2 == null) {
				logger.info("No orchestration response received");
			} else if (orchestrationResponse2.getResponse().isEmpty()) {
				logger.info("No provider found during the orchestration");
			} else {
				final OrchestrationResultDTO orchestrationResult2 = orchestrationResponse2.getResponse().get(0);
				validateOrchestrationResult(orchestrationResult2, CarConsumerConstants.GET_SERVO_SERVICE_DEFINITION);
				
				if(tempValue >= 25.00){
					final ServoRequestDTO servoReq = new ServoRequestDTO("200", "Indoor");
					logger.info("Sending Servo Position Request:");
					printOut(servoReq);
					final String token = orchestrationResult2.getAuthorizationTokens() == null ? null : orchestrationResult2.getAuthorizationTokens().get(getInterface());
					final ServoResponseDTO posSent = arrowheadService.consumeServiceHTTP(ServoResponseDTO.class, HttpMethod.valueOf(orchestrationResult2.getMetadata().get(CarConsumerConstants.HTTP_METHOD)),
							orchestrationResult2.getProvider().getAddress(), orchestrationResult2.getProvider().getPort(), orchestrationResult2.getServiceUri(),
							getInterface(), token, servoReq, new String[0]);
					logger.info("Servo Provider response");
					//printOut(carCreated);
					System.out.println("\n\nSetting the position of the Servo to "+posSent.getId()+" degrees"+" for indoor temeperature "+tempValue+" degree celsius");
					System.out.println("\n");
					Thread.sleep(2000);		
		        }
		        else if(tempValue < 25.00){
		        	final ServoRequestDTO servoReq = new ServoRequestDTO("100", "Indoor");
					logger.info("Sending Servo Position Request:");
					printOut(servoReq);
					final String token = orchestrationResult2.getAuthorizationTokens() == null ? null : orchestrationResult2.getAuthorizationTokens().get(getInterface());
					final ServoResponseDTO posSent = arrowheadService.consumeServiceHTTP(ServoResponseDTO.class, HttpMethod.valueOf(orchestrationResult2.getMetadata().get(CarConsumerConstants.HTTP_METHOD)),
							orchestrationResult2.getProvider().getAddress(), orchestrationResult2.getProvider().getPort(), orchestrationResult2.getServiceUri(),
							getInterface(), token, servoReq, new String[0]);
					logger.info("Servo Provider response");
					//printOut(carCreated);
					System.out.println("\n\nSetting the position of the Servo to "+posSent.getId()+" degrees"+" for indoor temeperature "+tempValue+" degree celsius");
					System.out.println("\n");
					Thread.sleep(2000);
		        }
			
			}
		}	
    }*/
    
    //=================================================================================================
	// assistant methods
    
    //-------------------------------------------------------------------------------------------------
    private String getInterface() {
    	return sslProperties.isSslEnabled() ? CarConsumerConstants.INTERFACE_SECURE : CarConsumerConstants.INTERFACE_INSECURE;
    }
    
    //-------------------------------------------------------------------------------------------------
    private void validateOrchestrationResult(final OrchestrationResultDTO orchestrationResult, final String serviceDefinitin) {
    	if (!orchestrationResult.getService().getServiceDefinition().equalsIgnoreCase(serviceDefinitin)) {
			throw new InvalidParameterException("Requested and orchestrated service definition do not match");
		}
    	
    	boolean hasValidInterface = false;
    	for (final ServiceInterfaceResponseDTO serviceInterface : orchestrationResult.getInterfaces()) {
			if (serviceInterface.getInterfaceName().equalsIgnoreCase(getInterface())) {
				hasValidInterface = true;
				break;
			}
		}
    	if (!hasValidInterface) {
    		throw new InvalidParameterException("Requested and orchestrated interface do not match");
		}
    }
    
    //-------------------------------------------------------------------------------------------------
    private void printOut(final Object object) {
    	System.out.println(Utilities.toPrettyJson(Utilities.toJson(object)));
    }
}
