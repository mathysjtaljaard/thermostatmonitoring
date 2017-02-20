package org.taljaard.comm;

import java.util.Enumeration;
import java.util.HashSet;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;

public class SerialPortReader {
	
	public static void main(String[] args) {
		
		HashSet<CommPortIdentifier> h = new HashSet<CommPortIdentifier>();
        Enumeration<CommPortIdentifier> thePorts = CommPortIdentifier.getPortIdentifiers();
		while(thePorts.hasMoreElements()) {
			CommPortIdentifier com = thePorts.nextElement();
			System.out.println("com.getPortType()" + com.getPortType());
			System.out.println("com.getName()" + com.getName());
			System.out.println("com.getCurrentOwner()" + com.getCurrentOwner());
            switch (com.getPortType()) {
            case CommPortIdentifier.PORT_SERIAL:
                try {
                    CommPort thePort = com.open("CommUtil", 50);
                    thePort.close();
                    h.add(com);
                } catch (PortInUseException e) {
                    System.out.println("Port, "  + com.getName() + ", is in use.");
                } catch (Exception e) {
                    System.err.println("Failed to open port " +  com.getName());
                    e.printStackTrace();
                }
            }
		}
	}

}
