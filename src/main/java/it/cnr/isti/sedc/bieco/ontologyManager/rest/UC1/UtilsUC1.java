package it.cnr.isti.sedc.bieco.ontologyManager.rest.UC1;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class UtilsUC1 {
	

	
	// Two Core Elements: eStructuralFeatures is contained in eClassifiers
	public static final String eStructuralFeatures = "eStructuralFeatures";
	public static final String eClassifiers = "eClassifiers";
	
	// attributes	
	public static final String XSI_TYPE = "xsi:type";
	// xsi:type
	public static final String EClass = "ecore:EClass"; // contained in eClassifiers
	public static final String EReference = "ecore:EReference"; 
	public static final String EAttribute= "ecore:EAttribute";
	
	
	public static final String defaultValueLiteral = "defaultValueLiteral"; 
	public static final String instanceTypeName = "instanceTypeName"; 
	// isntanceTypeName
	public static final String CS = "CS"; 
	public static final String Prime_Mover = "Prime_Mover";
	public static final String Message = "Message";
	public static final String HMI = "HMI";
	public static final String SoS = "SoS";
	public static final String Service = "Service"; 
	public static final String RUMI = "RUMI";
	
	
	public static final String name = "name"; 
	public static final String eType = "eType";
	
	
	
	private static int skillID = 0;
	public static int getNextSkillID(){
		return skillID ++;
	}
	
	private static int RUMIID = 0;
	public static int getNextRUMIID() {
		// TODO Auto-generated method stub
		return RUMIID++;
	}
	
		
	
	
	public static void parseAttributes(Node epackageChild) {
		// TODO Auto-generated method stub
		NamedNodeMap allAttributes = epackageChild.getAttributes();
		
		if(allAttributes != null) {
		System.out.println("allAttributes -> " + allAttributes.toString());
		for (int j = 0; j < allAttributes.getLength(); j++) {
			System.out.println("START :: @@@@@@@@");
			Node currentItem = allAttributes.item(j);
			if (currentItem != null) {
				
				String currentItemName = currentItem.getNodeName();
				System.out.println("NodeName : " + currentItem.getNodeName());
				System.out.println("NodeContent : " + currentItem.getTextContent());
				
			}
			System.out.println("ENDS :: @@@@@@@@");
		}
		}
	}



	private static int csID = 0;
	public static int getNextCSID() {
		// TODO Auto-generated method stub
		return csID++;
	}
	
	private static int sosID = 0;
	public static int getNextSoSID() {
		// TODO Auto-generated method stub
		return sosID++;
	}
	
	
	private static int messageID = 0;
	public static int getNextMessageID() {
		// TODO Auto-generated method stub
		return messageID++;
	}
	
}
