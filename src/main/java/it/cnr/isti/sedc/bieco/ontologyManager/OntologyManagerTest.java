package it.cnr.isti.sedc.bieco.ontologyManager;

import java.util.ArrayList;

public class OntologyManagerTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		OntologyManagerStandalone manager = new OntologyManagerStandalone();
		ArrayList<SoS> result = (ArrayList<SoS>) manager.getSOSs();
		
		System.out.println("Get SOSs :: Final Result :: -> :: ");
		for (SoS soS : result) {
			System.out.println(soS.toJson());
		}
		
//		System.out.println(result.toString());
		
		
		System.out.println("@@@@@@Get Devices :: START :: -> :: ");
		ArrayList<Device> devices = (ArrayList<Device>) manager.getDevices("2");
		System.out.println("@@@@@@Get Devices :: Final Result :: -> :: ");
		for (Device device : devices) {
			System.out.println(device.toJson());
		}
		
	}

}
