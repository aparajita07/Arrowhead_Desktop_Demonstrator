package ai.aitia.demo.car_provider;

import com.jcraft.jsch.JSchException;
import com.pi4j.component.temperature.TemperatureSensor;
import com.pi4j.io.w1.W1Master;
import com.pi4j.temperature.TemperatureScale;
import java.io.*;
import java.text.DecimalFormat;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class TemperatureReader {

	  public static double readTemperature() throws JSchException{
	      
		  DecimalFormat df2 = new DecimalFormat("##.##");
		  ExecCommand piCom = new ExecCommand();
	    	//piCom.ExecCommand("sudo modprobe w1-gpio");
	    	//piCom.ExecCommand("sudo modprobe w1-therm");
	    	String rawTemp = piCom.ExecCommand("cd /sys/bus/w1/devices/28-00000931128a; cat w1_slave");
	    	String temp1=rawTemp.substring(69);
	    	Double temp2= Double.parseDouble(temp1);
	    	temp2= temp2/1000;
	    	String temperature=df2.format(temp2);
	    	System.out.println("Indoor temperature read: "+temperature);
	    	
	    	Double tempfinal= Double.parseDouble(temperature);
	    	return tempfinal;
	  }
	
}
