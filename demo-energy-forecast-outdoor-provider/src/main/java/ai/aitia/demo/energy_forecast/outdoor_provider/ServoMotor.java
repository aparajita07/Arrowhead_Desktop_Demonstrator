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

    	if (pos==90) {
    		piCom.ExecCommand("python servo90.py");	//If the position is a valid one, then move the servo to the given position
    	}
    	else if(pos==180) {
    		piCom.ExecCommand("python servo180.py");	//If the position is a valid one, then move the servo to the given position
    	}
    	else System.out.println("invalid position for servo motor");

        System.out.println("servo moved to position "+pos);
    }
}
