package ai.aitia.demo.car_provider;

public class CarProviderConstants {
	
	//=================================================================================================
	// members
	
	public static final String BASE_PACKAGE = "ai.aitia";
	
	public static final String CREATE_CAR_SERVICE_DEFINITION = "create-car";
	public static final String GET_CAR_SERVICE_DEFINITION = "get-car";
	public static final String GET_TEMP_SERVICE_DEFINITION = "get-temp";
	public static final String INTERFACE_SECURE = "HTTPS-SECURE-JSON";
	public static final String INTERFACE_INSECURE = "HTTP-INSECURE-JSON";
	public static final String HTTP_METHOD = "http-method";
	public static final String CAR_URI = "/temp";
	public static final String BY_ID_PATH = "/{id}";
	public static final String PATH_VARIABLE_ID = "id";
	public static final String REQUEST_PARAM_KEY_ServiceType = "request-param-ServiceType";
	public static final String REQUEST_PARAM_ServiceType = "ServiceType";
	public static final String REQUEST_PARAM_KEY_Location = "request-param-Location";
	public static final String REQUEST_PARAM_Location = "Location";
	public static final String REQUEST_PARAM_KEY_TEMP = "request-param-Data";
	public static final String REQUEST_PARAM_TEMP = "Data";	
	
	
	//=================================================================================================
	// assistant methods

	//-------------------------------------------------------------------------------------------------
	private CarProviderConstants() {
		throw new UnsupportedOperationException();
	}
}
