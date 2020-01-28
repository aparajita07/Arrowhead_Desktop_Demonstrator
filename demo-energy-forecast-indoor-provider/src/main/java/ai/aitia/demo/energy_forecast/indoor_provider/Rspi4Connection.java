package ai.aitia.demo.energy_forecast.indoor_provider;

import java.util.Properties;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class Rspi4Connection {
	public Session Rspi4Connection() throws JSchException{
		String user = "<UserName>";
	    String password = "<password>";
	    String host = "130.240.234.166";
	    int port = 22;

	    Properties props = new Properties();
	    props.put("StrictHostKeyChecking", "no");
	    JSch jsch = new JSch();
	    Session session = jsch.getSession(user, host, port);
	    session.setPassword(password);
	    session.setConfig(props);
	    //System.out.println("Establishing Connection...");
	    session.connect();
	    //System.out.println("Connection established.");
	    return session;
 
	}
}
