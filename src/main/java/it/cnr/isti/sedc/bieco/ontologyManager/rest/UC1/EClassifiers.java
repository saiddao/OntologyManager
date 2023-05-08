package it.cnr.isti.sedc.bieco.ontologyManager.rest.UC1;

import java.util.HashSet;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class EClassifiers extends ECoreUC1{
	
	private String instanceTypeName; 
	
	private HashSet<EStructuralFeatures> eStructuralFeatures;
	private HashSet<EClassifiers> myServices;

	private HashSet<EClassifiers> myRUMIS;
	private HashSet<EClassifiers> myCSs;
	
	public EClassifiers() {
		// TODO Auto-generated constructor stub
		super();
	}
	public EClassifiers(String type, String eName) {
		super(type, eName);
		// TODO Auto-generated constructor stub
	}
	public EClassifiers(Node eNode) {
		// TODO Auto-generated constructor stub
		super(eNode);
		
		this.instanceTypeName = eNode.getAttributes().getNamedItem(UtilsUC1.instanceTypeName).getTextContent();
		
		
	}
	public HashSet<EStructuralFeatures> geteStructuralFeatures() {
		return eStructuralFeatures;
	}
	public void seteStructuralFeatures(HashSet<EStructuralFeatures> eStructuralFeatures) {
		this.eStructuralFeatures = eStructuralFeatures;
	}
	public String getInstanceTypeName() {
		return instanceTypeName;
	}
	public void setInstanceTypeName(String instanceTypeName) {
		this.instanceTypeName = instanceTypeName;
	}
	@Override
	public String toString() {
		return "EClassifiers [MyID="+getMyID()+", instanceTypeName=" + instanceTypeName + ", eStructuralFeatures=" + eStructuralFeatures
				+ ", getXsi_type()=" + getXsi_type() + ", getName()=" + getName() + "]";
	}
	@Override
	public void parseNode() {
		// TODO Auto-generated method stub
		
		switch (getInstanceTypeName()) {
		case UtilsUC1.RUMI:
			
			myServices = new HashSet<EClassifiers>();
			System.out.println("\n########### RUMI NAME :: "+getName());
			
			NodeList nodeList = geteNode().getChildNodes();	
			for (int i = 0; i < nodeList.getLength(); i++) {
				System.out.println("nodeList.getLength() :: "+nodeList.getLength());
				Node nodeChild = nodeList.item(i);
				
				if(nodeChild.getNodeType() == Node.ELEMENT_NODE) {
					System.err.println(nodeChild.getLocalName());
					System.out.println(nodeChild.toString());
					EStructuralFeatures eStructuralFeature = new EStructuralFeatures(nodeChild);
					System.out.println(eStructuralFeature.toString());
					
					System.out.println(eStructuralFeature.getCleanedType());
					
					EClassifiers service = UC1Parser.servicessMap.get(eStructuralFeature.getCleanedType());
					System.out.println("Service #### "+service);
					
					if(service != null) {
						myServices.add(service);
					}
				}
			}
			break;
		case UtilsUC1.CS:
			myRUMIS = new HashSet<EClassifiers>();
			System.out.println("\n########### CS NAME :: "+getName());
			
			
			NodeList rumiList = geteNode().getChildNodes();	
			for (int i = 0; i < rumiList.getLength(); i++) {
				System.out.println("rumiList.getLength() :: "+rumiList.getLength());
				Node nodeChild = rumiList.item(i);
				if(nodeChild.getNodeType() == Node.ELEMENT_NODE) {
					UtilsUC1.parseAttributes(nodeChild);
					Node eTypeAttribute = nodeChild.getAttributes().getNamedItem(UtilsUC1.eType);
					if (eTypeAttribute != null) {
						String rumID = eTypeAttribute.getTextContent();
						EClassifiers rumiCurrent = UC1Parser.rumisMap.get(rumID.substring(3));
						if (rumiCurrent != null) {
							System.out.println("RUMI Already Parsed :: "+rumiCurrent.toString());													
							myRUMIS.add(rumiCurrent);
						}
					}
					
					/*
					System.err.println(nodeChild.getLocalName());
					System.out.println(nodeChild.toString());
					EStructuralFeatures eStructuralFeature = new EStructuralFeatures(nodeChild);
					System.out.println(eStructuralFeature.toString());
					
					if(eStructuralFeature.getCleanedType() != null) {
						System.out.println(eStructuralFeature.getCleanedType());
						EClassifiers rumi = UC1Parser.rumisMap.get(eStructuralFeature.getCleanedType());
						System.out.println("Rumi #### "+rumi);
						if(rumi != null) {
							myRUMIS.add(rumi);
						}
					}
					*/
				}
			}
			
			break;
		case UtilsUC1.SoS:
			myCSs = new HashSet<EClassifiers>();
			System.out.println("\n########### SoS NAME :: "+getName());
			myCSs.addAll(UC1Parser.cssMap.values());
			
			/*
			NodeList rumiList = geteNode().getChildNodes();	
			for (int i = 0; i < rumiList.getLength(); i++) {
				System.out.println("rumiList.getLength() :: "+rumiList.getLength());
				Node nodeChild = rumiList.item(i);
				if(nodeChild.getNodeType() == Node.ELEMENT_NODE) {
					UtilsUC1.parseAttributes(nodeChild);
					Node eTypeAttribute = nodeChild.getAttributes().getNamedItem(UtilsUC1.eType);
					if (eTypeAttribute != null) {
						String rumID = eTypeAttribute.getTextContent();
						EClassifiers rumiCurrent = UC1Parser.rumisMap.get(rumID.substring(3));
						if (rumiCurrent != null) {
							System.out.println("RUMI Already Parsed :: "+rumiCurrent.toString());							
							myRUMIS.add(rumiCurrent);
						}
					}
				}
			}
			
			*/
			break;
		default:
			break;
		}
	}
	
	@Override
	public String getOMJson() {
		// TODO Auto-generated method stub
		String elementAsJson = null;
		switch (getInstanceTypeName()) {
		case UtilsUC1.Service:
			elementAsJson = "{"
				+ "\"skillName\": \""
				+ getName()+"\","
						+ "\"skillId\": \""+getMyID()+"\",\"description\": \""+ getName()+"\",\"rules\": ["+getRules()+" ]}";		
			break;
		
		case UtilsUC1.RUMI:
		
			StringBuilder builder = new StringBuilder();
			builder.append("{\"componentName\": \"");
			builder.append(getName());
			builder.append("\", \"componentId\": \"");
			builder.append(getMyID());
			builder.append("\",\"description\": \"");
			builder.append(getName());
			builder.append("\",\"skills\": [");
			int i = 0;
			for (EClassifiers service : myServices) {
				builder.append(service.getOMJson());
				
				if(++i < myServices.size()) {
					builder.append(", ");
				}
			}
					
			builder.append("]}");
			
			elementAsJson = builder.toString();
			break;
		case UtilsUC1.CS:
			
			StringBuilder builderCS = new StringBuilder();
			builderCS.append("{\"deviceName\": \"");
			builderCS.append(getName());
			builderCS.append("\", \"deviceId\": \"");
			builderCS.append(getMyID());
			builderCS.append("\",\"description\": \"");
			builderCS.append(getName());
			builderCS.append("\",\"components\": [");
			int rm_i = 0;
			for (EClassifiers rumi : myRUMIS) {
				builderCS.append(rumi.getOMJson());
				
				if(++rm_i < myRUMIS.size()) {
					builderCS.append(", ");
				}
			}
					
			builderCS.append("]}");
			
			elementAsJson = builderCS.toString();
			break;
			case UtilsUC1.SoS:
			
			StringBuilder builderSoS = new StringBuilder();
			builderSoS.append("{\"sosName\": \"");
			builderSoS.append(getName());
			builderSoS.append("\", \"sosId\": \"");
			builderSoS.append(getMyID());
			builderSoS.append("\",\"description\": \"");
			builderSoS.append(getName());
			builderSoS.append("\",\"devices\": [");
			int cs_i = 0;
			for (EClassifiers cs : myCSs) {
				builderSoS.append(cs.getOMJson());
				
				if(++cs_i < myCSs.size()) {
					builderSoS.append(", ");
				}
			}
					
			builderSoS.append("]}");
			
			elementAsJson = builderSoS.toString();
			break;	
		default:
			break;
		}
		
		return elementAsJson;
	}
	private String getRules() {
		// TODO Auto-generated method stub
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("{");
		builder.append("    \"welldefinedrule\": \" Note: Rule To be Defined -> #PAR_1, #PAR_2, #PAR_3.\", ");
		builder.append("    \"ruleType\": \"Standard\", ");
		builder.append("    \"abstractrule\": \"Maximum number of established simultaneous connections\", ");
		builder.append("    \"ruleName\": \"Name of Rule 1_2\", ");
		builder.append("    \"description\": \"Description of Rule 1_2\", ");
		builder.append("    \"ruleId\": \"1\" ");
		builder.append("} ");
		return builder.toString();
	}
	public HashSet<EClassifiers> getMyRUMIS() {
		return myRUMIS;
	}
	public void setMyRUMIS(HashSet<EClassifiers> myRUMIS) {
		this.myRUMIS = myRUMIS;
	}
}
