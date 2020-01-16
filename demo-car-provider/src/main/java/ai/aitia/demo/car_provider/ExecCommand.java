package ai.aitia.demo.car_provider;

import java.io.IOException;
import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class ExecCommand {

Rspi4Connection conn= new Rspi4Connection();
	
	public String ExecCommand(String command) throws JSchException {
    Session session=conn.Rspi4Connection();
    StringBuilder outputBuffer = new StringBuilder();
    try {
    	 Channel channel=session.openChannel("exec");
         ((ChannelExec)channel).setCommand(command);
         InputStream commandOutput = channel.getInputStream();
         channel.connect();
         int readByte = commandOutput.read();
         
         
         while(readByte != 0xffffffff)
         {
            outputBuffer.append((char)readByte);
            readByte = commandOutput.read();
         }

         //channel.disconnect();
    }
    catch(IOException ioX)
    {
    	ioX.printStackTrace();
    }
   
    //System.out.println(outputBuffer.toString());
	return outputBuffer.toString();
	}
}
