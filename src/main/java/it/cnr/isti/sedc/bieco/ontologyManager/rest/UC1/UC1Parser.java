/**
 * 
 */
package it.cnr.isti.sedc.bieco.ontologyManager.rest.UC1;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JEditorPane;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Said Daoudagh
 *
 */
public class UC1Parser {

	
	
	public static final String XSI_TYPE = "xsi:type";
	public static HashSet<String> eCoreattributes = new HashSet<String>();
	public static HashSet<String> eCoreElements = new HashSet<String>();
	
	public static HashMap<String, HashSet<String>> eCoreattributesHashMap = new HashMap<String, HashSet<String>>();
	
	public static HashSet<EClassifiers> SOSs = new HashSet<EClassifiers>();
	public static HashSet<EClassifiers> CSs = new HashSet<EClassifiers>();
	public static HashSet<EClassifiers> RUMI = new HashSet<EClassifiers>();
	public static HashSet<EClassifiers> Service = new HashSet<EClassifiers>();
	public static HashSet<EClassifiers> Message = new HashSet<EClassifiers>();
	
	public static HashSet<EClassifiers> HMI = new HashSet<EClassifiers>();
	public static HashSet<EClassifiers> Prime_Mover = new HashSet<EClassifiers>();
	
	public static HashMap<String, EClassifiers> elementsEClassifiersMap = new HashMap<String, EClassifiers>();
	
	public static HashMap<String, EClassifiers> servicessMap = new HashMap<String, EClassifiers>();
	public static HashMap<String, EClassifiers> rumisMap = new HashMap<String, EClassifiers>();
	public static HashMap<String, EClassifiers> cssMap = new HashMap<String, EClassifiers>();

	public static HashMap<String, EClassifiers> sossMap = new HashMap<String, EClassifiers>();
	public static HashMap<String, EClassifiers> messagesMap = new HashMap<String, EClassifiers>();
	
	
	
	private static int id;
	
	
	
	/**
	 * 
	 */
	public UC1Parser() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		File file = new File("UC1_model.ecore");
		
		file = new File("UC1_model_Correct_1_secRes_2.ecore");
		
		
		String filePath = file.getAbsolutePath();
		String fileName = file.getName();
		
		
		System.out.println(filePath);
		System.out.println(fileName);

		Node nodeFileEPackage = getFileAsDom(file);
		System.err.println(nodeFileEPackage.getLocalName());
		
		
		parseNode(nodeFileEPackage);
		
		System.out.println("Attributes :: "+eCoreattributes);
		System.out.println(eCoreElements);
		
		System.out.println(eCoreattributesHashMap);
		
		
		Set<String> keySet = eCoreattributesHashMap.keySet();
		for (String key : keySet) {
			System.out.println(key+" :: "+eCoreattributesHashMap.get(key).size()+ " :: "+eCoreattributesHashMap.get(key));
		}
		
		
		
		
		/*
		System.out.println("SoSs -> " + SOSs);
		System.out.println("CSs -> "+CSs);
		System.out.println("RUMIs -> "+RUMI);
		System.out.println("Services -> "+Service);
		System.out.println("Messages -> "+Message);
		*/
		System.out.println(elementsEClassifiersMap);
		
		System.out.println("Services -> "+ Service.size()+ "-> "+Service);
		
		
		for (EClassifiers service : Service) {
			System.out.println(service.getOMJson());
		}
		
		
		
		parseRUMIS();
		parseCSs();
		parseSoSs();
		
		
		
//		NodeList epackageNodeList = nodeFileEPackage.getChildNodes();
//		for (int i = 0; i < epackageNodeList.getLength(); i++) {
//			Node epackageChild = epackageNodeList.item(i);
//			// System.out.println(epackageChild.getLocalName());
//
//			if (epackageChild.getLocalName() != null) {
//				// System.out.println(epackageChild.getLocalName());
//				Node instanceTypeName = epackageChild.getAttributes().getNamedItem("instanceTypeName");
//
//				if (instanceTypeName.getTextContent().equals("CS")) {
//					System.out.println("instanceTypeName: " + instanceTypeName.getTextContent());
//
//					Node name = epackageChild.getAttributes().getNamedItem("name");
//					System.out.println("Name: " + name.getTextContent());
//
//					NamedNodeMap allAttributes = epackageChild.getAttributes();
//					System.out.println("allAttributes -> " + allAttributes.toString());
//					for (int j = 0; j < allAttributes.getLength(); j++) {
//						System.out.println("START :: @@@@@@@@");
//						Node currentItem = allAttributes.item(j);
//						if (currentItem != null) {
//							System.out.println("NodeName : " + currentItem.getNodeName());
//							System.out.println("NodeContent : " + currentItem.getTextContent());
//						}
//						System.out.println("ENDS :: @@@@@@@@");
//					}
//				}
//			}
//		}
	}
	
	
	
	
	
	
	
	 private static String convertXmlToString(Document doc) {
	        DOMSource domSource = new DOMSource(doc);
	        StringWriter writer = new StringWriter();
	        StreamResult result = new StreamResult(writer);
	        TransformerFactory tf = TransformerFactory.newInstance();
	        Transformer transformer = null;
	        try {
	            transformer = tf.newTransformer();
	            transformer.transform(domSource, result);
	        } catch (TransformerException e) {
	            throw new RuntimeException(e);
	        }
	        return writer.toString();
	    }

	    private static Document convertStringToXml(String xmlString) {

	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

	        try {

	            // optional, but recommended
	            // process XML securely, avoid attacks like XML External Entities (XXE)
	            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

	            DocumentBuilder builder = dbf.newDocumentBuilder();

	            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));

	            return doc;

	        } catch (ParserConfigurationException | IOException | SAXException e) {
	            throw new RuntimeException(e);
	        }

	    }
	
	
	
	
	public static String ecoreParser(String ecoreFileContent) {
		// TODO Auto-generated method stub
		

		Document document = convertStringToXml(ecoreFileContent);

		
        document.getChildNodes().item(0);
		
		
		parseNode(document.getChildNodes().item(0));
		
		System.out.println("Attributes :: "+eCoreattributes);
		System.out.println(eCoreElements);
		
		System.out.println(eCoreattributesHashMap);
		
		
		Set<String> keySet = eCoreattributesHashMap.keySet();
		for (String key : keySet) {
			System.out.println(key+" :: "+eCoreattributesHashMap.get(key).size()+ " :: "+eCoreattributesHashMap.get(key));
		}
		
		System.out.println(elementsEClassifiersMap);
		
		System.out.println("Services -> "+ Service.size()+ "-> "+Service);
		
		
		for (EClassifiers service : Service) {
			System.out.println(service.getOMJson());
		}
		
		
		
		parseRUMIS();
		parseCSs();
		String amadeosOntology = parseSoSs();
		
		
		
		return amadeosOntology;
	}
	
	
	
	
	
	
	
	

	private static String parseSoSs() {
		// TODO Auto-generated method stub
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("{\"SoSs\":[");
		
		
		for (EClassifiers sos : SOSs) {
			sos.parseNode();
			
			String sosAsString = sos.getOMJson();
			System.out.println(sosAsString);	
			builder.append(sosAsString);
		}
		
		builder.append("]}");
		
		System.err.println(builder.toString());
		
		
		return builder.toString();
	}

	private static void parseCSs() {
		// TODO Auto-generated method stub
		for (EClassifiers cs : CSs) {
			System.out.println(cs.getName());
			cs.parseNode();
			System.out.println(cs.getOMJson());	
		}
	}

	private static void parseRUMIS() {
		// TODO Auto-generated method stub
		
		for (EClassifiers rumi : RUMI) {
			rumi.parseNode();
			System.out.println(rumi.getOMJson());	
		}
	}

	private static void parseNode(Node nodeFileEPackage) {
		// TODO Auto-generated method stub
		NodeList epackageNodeList = nodeFileEPackage.getChildNodes();
		
		for (int i = 0; i < epackageNodeList.getLength(); i++) {
			
			Node epackageChild = epackageNodeList.item(i);
			System.err.println(epackageChild.getLocalName());
			
			String elementName = epackageChild.getNodeName();
			
			switch (elementName) {
			case UtilsUC1.eClassifiers:
				System.err.println(UtilsUC1.eClassifiers);
				EClassifiers current = new EClassifiers(epackageChild);
				System.err.println(current);
				String name = current.getName(); 
				elementsEClassifiersMap.put(name, current);
				
				switch (current.getInstanceTypeName()) {
				case UtilsUC1.SoS:
					current.setMyID(UtilsUC1.getNextSoSID());
					SOSs.add(current);
					sossMap.put(current.getName(), current);
					break;
				case UtilsUC1.CS:
					current.setMyID(UtilsUC1.getNextCSID());
					CSs.add(current);
					cssMap.put(current.getName(), current);
					break;
				case UtilsUC1.RUMI:
					current.setMyID(UtilsUC1.getNextRUMIID());
					RUMI.add(current);
					rumisMap.put(current.getName(), current);
					break;
				case UtilsUC1.Service:
					id = UtilsUC1.getNextSkillID();
					System.out.println(id);
					current.setMyID(id);
					Service.add(current);
					
					servicessMap.put(current.getName(), current);
					break;
				case UtilsUC1.Message:
					
					
					current.setMyID(UtilsUC1.getNextMessageID());
					Message.add(current);
					messagesMap.put(current.getName(), current);
					break;
				default:
					break;
				}
				break;
			
			case UtilsUC1.eStructuralFeatures:
				//System.out.println("SAID");
				//System.exit(0);
				break;
			default:
				break;
			}
			
			
			eCoreElements.add(epackageChild.getLocalName());
			parseAttributes(epackageChild);
			parseNode(epackageChild);
			
			
			
		}
	}

	private static void parseAttributes(Node epackageChild) {
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
				
				eCoreattributes.add(currentItem.getNodeName());
				
				addCurrentItemToHasMap(currentItem);
			}
			System.out.println("ENDS :: @@@@@@@@");
		}
		}
	}

	private static void addCurrentItemToHasMap(Node currentItem) {
		// TODO Auto-generated method stub
		String name = currentItem.getNodeName();
		eCoreattributesHashMap.get(name);
		
		if(eCoreattributesHashMap.get(name) == null) {
			eCoreattributesHashMap.put(name, new HashSet());
		}
		eCoreattributesHashMap.get(name).add(currentItem.getTextContent());
		
	}

	public static Node getFileAsDom(File xacmlPolicyFile) {
		try {

			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setNamespaceAware(true);
			DocumentBuilder docBuilder;

			docBuilder = docBuilderFactory.newDocumentBuilder();

			Document xacmlPolicyAsDoc = docBuilder.parse(xacmlPolicyFile);

			// DomUtils domUtils = new DomUtils();
			// domUtils.serialize(xacmlPolicyAsDoc, System.out);

			return xacmlPolicyAsDoc.getChildNodes().item(0);

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}