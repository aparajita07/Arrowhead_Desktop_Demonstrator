package ai.aitia.demo.energy_forecast.outdoor_provider;

import com.jcraft.jsch.JSchException;

import ai.aitia.demo.energy_forecast.outdoor_provider.ExecCommand;

public class ServoMotor {
    
    public static void moveServo(int pos) throws JSchException {
        
        /*
         * Here we are controlling the servo. This is done by using the piCom.ExecCommand. What this command does, is that it
         * executing the inputted argument-string in a terminal.
         * 
         * For example, the piCom.ExecCommand("echo Hello world") command would basically outputting 'Hello world' in a terminal
        */

    	ExecCommand piCom = new ExecCommand();
    	
    	try {
    	piCom.ExecCommand("gpio mode 26 pwm"); //Powers on GPIO 26 (Pin 32)
    	piCom.ExecCommand("gpio pwm-ms");
    	piCom.ExecCommand("gpio pwmc 192");
    	piCom.ExecCommand("gpio pwmr 2000");
    	
    	if((100 <= pos) && (pos <= 200)){  //If the position is a valid one, then move the servo to the given position
    	piCom.ExecCommand("gpio pwm-ms");
    	  }
    	}
    	catch (Exception e) {
            System.out.println("Exception occured: " + e.getMessage());
        }
        System.out.println("servo moved to position "+pos);
    }
}
