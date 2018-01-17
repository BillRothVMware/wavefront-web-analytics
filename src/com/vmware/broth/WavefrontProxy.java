package com.vmware.broth;

import java.io.*;
import java.net.*;


public class WavefrontProxy {

	protected int port = 2878;
	protected String sHostname = "localhost";
	protected String sSource = "java";
	
	WavefrontProxy (String host) {
		sHostname = host;
	}
	WavefrontProxy () {
		return;
	}
	
	public boolean send(String sMetricName,  double value, long eTime, String tags, String source) {
		
		if(sMetricName.isEmpty())
			return false;
		Socket serverSocket;
		try {
			serverSocket = new Socket(sHostname, port);
			DataOutputStream out = new DataOutputStream(serverSocket.getOutputStream());
			
			// MetricName
			String sendStr = sMetricName + " " + String.valueOf(value);
			// insert time
			if(eTime == 0)
				sendStr += " " + String.valueOf(java.time.Instant.now().getEpochSecond());
			else {
				sendStr += " " + String.valueOf(eTime);
			}

			// insert tags, if any
			if (!tags.isEmpty()) {
				sendStr += " " + tags;
			}
			
			// insert Source
			// MAKE sure to use the \n. Doesn't work without it.
			//
			sendStr += " " + "source=java\n";
			
			out.writeBytes(sendStr);
			
			out.flush();
			
		    out.close();
		    serverSocket.close();
		    
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
