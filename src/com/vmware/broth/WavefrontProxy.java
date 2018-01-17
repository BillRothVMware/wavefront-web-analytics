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
	
	public boolean send(String sMetricName,  double value, String tags, String source) {
		
		if(sMetricName.isEmpty())
			return false;
		Socket clientSocket;
		try {
			clientSocket = new Socket(sHostname, port);
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			
			String sendStr = sMetricName + " " + String.valueOf(value);
			if (!tags.isEmpty()) {
				sendStr += " " + tags;
			}
			sendStr += " " + "source=" + source;
			
			
			out.write(sendStr);    
			    
		    out.close();
		    clientSocket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
