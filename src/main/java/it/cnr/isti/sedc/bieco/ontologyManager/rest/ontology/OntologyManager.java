package it.cnr.isti.sedc.bieco.ontologyManager.rest.ontology;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.cnr.isti.sedc.bieco.ontologyManager.Component;
import it.cnr.isti.sedc.bieco.ontologyManager.CoreOntology;
import it.cnr.isti.sedc.bieco.ontologyManager.Device;
import it.cnr.isti.sedc.bieco.ontologyManager.OntologyEntitiesNames;
import it.cnr.isti.sedc.bieco.ontologyManager.Rule;
import it.cnr.isti.sedc.bieco.ontologyManager.Skill;
import it.cnr.isti.sedc.bieco.ontologyManager.SoS;
import it.cnr.isti.sedc.bieco.ontologyManager.Student;
import it.cnr.isti.sedc.bieco.ontologyManager.utils.BiecoMessageTypes;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.JsonObject;



/**
 * Root resource (exposed at "myresource" path)
 */

@Path("ontologymanager/biecointerface")

public class OntologyManager {

	private String incomingToken = "MDM2grHjCbdRy";
	private String outcomingToken = "qJACs1J0apruOOJCg";

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to the
	 * client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */

	@GET
	@Produces({ MediaType.TEXT_HTML })
	public String getIt() {
		return homePage();

	}

	private String homePage() {

		
		String result =  "<!DOCTYPE html><head><meta charset=\"utf-8\"><title>OntologyManager</title>" + "</head><style>\n"
				+ "body {\n" + "  background-color: #ddebef;\n" + "}\n"
				+ "</style><body><h2>OntologyManager</h2><h3>Status: " + OntologyStatus() + "</h3>"
				+ "<h4>Ontology logs:</h4><textarea id=\"logs\" name=\"debugLog\" rows=\"30\" cols=\"200\">\n"
				+ getLoggerData() + "</textarea></body></html>";
		
		
		
		result = "<!DOCTYPE html>"
		+ "<html lang=\"en\">"
		+ "<head>"
		+ "    <meta charset=\"UTF-8\">"
		+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"
		+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
		+ "    <title>Form</title>"
		+ "</head>"
		+ "<body>"
		+ "    <form action=\"http://localhost:8283/ontologymanager/biecointerface/getsossHTML\" method=\"SET\">"
		+ "        <button>Get SoSs</button>"
		+ "    </form>"
		+ "</body>"
		+ "</html>";
		
		return result;
		
		
		
		
		/*
		 * return
		 * "<!DOCTYPE html><head><meta charset=\"utf-8\"><title>OntologyManager</title>"
		 * + "</head><style>\n" + "body {\n" + "  background-color: #ddebef;\n" + "}\n"
		 * + "</style><body><h2>OntologyManager</h2><h3>Status: " + OntologyStatus() +
		 * "</h3>" +
		 * "<h4>Ontology logs:</h4><textarea id=\"logs\" name=\"debugLog\" rows=\"30\" cols=\"200\">\n"
		 * + getLoggerData() + "</textarea></body></html>";
		 */

	}
	
	
	@GET
	@Path("/getsossHTML")
	@Produces({ MediaType.TEXT_HTML })
	public String getSOSsHTML() {
		List<SoS> soss = this.loadOntDatabaseSoSs();
		
		StringBuilder resultHTML = new StringBuilder();
		resultHTML.append("<!DOCTYPE html>"
				+ "<html lang=\"en\">"
				+ "<head>"
				+ "    <meta charset=\"UTF-8\">"
				+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
				+ "    <title>Form</title>"
				+ "</head>"
				+ "<body>");
			
		
		resultHTML.append("<form action=\"http://localhost:8283/ontologymanager/biecointerface/getComponentsHTML\">"
		+"  <label for=\"soss\">Choose SoS:</label>"
		+"  <select name=\"soss\" id=\"soss\">");
		
		for (SoS soS : soss) {
			resultHTML.append(soS.getHTML());
			
		}
		
		 		  
		resultHTML.append("</select>"
		  +"  <br><br>"
		  +"<input type=\"getcomponents\" value=\"GetComponents\">"
		  +"</form>");
		
				
				
				
		resultHTML.append( "    <form action=\"http://localhost:8283/ontologymanager/biecointerface/getsossHTML\" method=\"SET\">"
				+ "        <button>Get SoSs</button>"
				+ "    </form>"
				+ "</body>"
				+ "</html>");
		
		
		return resultHTML.toString();
	}
	
	
	
	
	
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response biecointerface(String jsonMessage, @Context HttpHeaders headers) {

		JSONParser parser = new JSONParser();
		Response output = null;
		JSONObject bodyMessage;
		String authorization = headers.getRequestHeader("Authorization").get(0);
		if (authorization.compareTo(outcomingToken) == 0) {

			// JSONObject bodyMessage = new JSONObject(jsonMessage);

			// bodyMessage = (JSONObject) parser.parse(jsonMessage);

			/**
			 * if (((String)
			 * bodyMessage.get("messageType")).compareTo(BiecoMessageTypes.HEARTBEAT) == 0)
			 * { return this.heartbeat(); } else if (((String)
			 * bodyMessage.get("messageType")).compareTo(BiecoMessageTypes.START) == 0) {
			 * return this.start(); } else if (((String)
			 * bodyMessage.get("messageType")).compareTo(BiecoMessageTypes.STOP) == 0) {
			 * return this.stop(); } else if (((String)
			 * bodyMessage.get("messageType")).compareTo(BiecoMessageTypes.GETSTATUS) == 0)
			 * { return this.getStatus(); } else if (((String)
			 * bodyMessage.get("messageType")).compareTo(BiecoMessageTypes.CONFIGURE) == 0)
			 * { return this.configure(); } else if (((String)
			 * bodyMessage.get("messageType")).compareTo(BiecoMessageTypes.DATA) == 0) {
			 * return this.data((String) bodyMessage.get("event")); } else if (((String)
			 * bodyMessage.get("messageType")).compareTo(BiecoMessageTypes.EVENT) == 0) {
			 * return this.event(); } else if (((String)
			 * bodyMessage.get("messageType")).compareTo(BiecoMessageTypes.DEMO) == 0) {
			 * return this.demo(); } else return Response.status(400).entity("invalid
			 * messageType").build();
			 */

			try {
				bodyMessage = (JSONObject) parser.parse(jsonMessage);
				switch ((String) bodyMessage.get("messageType")) {
				case BiecoMessageTypes.HEARTBEAT:
					output = this.heartbeat();
					break;
				case BiecoMessageTypes.START:
					output = this.start();
					break;
				case BiecoMessageTypes.STOP:
					output = this.stop();
					break;
				case BiecoMessageTypes.GETSTATUS:
					output = this.getStatus();
					break;
				case BiecoMessageTypes.CONFIGURE:
					output = this.configure();
					break;
				case BiecoMessageTypes.DATA:
					output = this.data((JSONObject) bodyMessage.get("event"));
					break;
				case BiecoMessageTypes.EVENT:
					output = this.event();
					break;
				case BiecoMessageTypes.DEMO:
					output = this.demo();
					break;
				default:
					output = Response.status(400).entity("invalid messageType").build();
					break;
				}
			} catch (ParseException e) { // TODO Auto-generated catch block e.printStackTrace();
			}

		} else {
			output = Response.status(401).entity("invalid access token").build();
		}
		
		//System.out.println(homePage().toString());
//		output = Response.status(401).entity(homePage()).build(); 
		
		System.out.println(output);
		System.out.println(output.getEntity());
		
		System.out.println(System.getProperty("user.dir"));
		
		
		return output;
	}

	private Response start() {
		return Response.status(200).entity(OntologyStart()).header("Authorization", incomingToken).build();
	}

	private Response stop() {
		return Response.status(200).entity(OntologyStop()).header("Authorization", incomingToken).build();
	}

	private Response heartbeat() {
		return Response.status(200).build();
	}

	private Response getStatus() {
		return Response.status(200).entity(OntologyStatus()).build();
	}

	private Response demo() {
		return Response.status(200).entity(DemoStatus()).build();
	}

	private Response configure() {
		return Response.status(404).build();
	}

	/**
	 * OntologyRequest is a JSON Object containing { "operationName": "_",
	 * "idOntResource" : "45" }
	 * 
	 * We have defined Four operation: 1) getsoss 2) getdevices 3) getcomponents 4)
	 * getskills
	 * 
	 * 
	 * @param ontologyRequest
	 * @return
	 */

	private Response dataOLD(String ontologyRequest) {

		Response output;
		switch (ontologyRequest) {
		case "getsoss":
			output = Response.status(200).entity("sos1,sos2,sos3").build();
			break;
		case "getdevices":
			output = Response.status(200).entity("device1,device2,device3").build();
			break;
		case "getcomponents":
			output = Response.status(200).entity("component1,component2,component3").build();
			break;
		case "getskills":
			output = Response.status(200).entity("skill1,skill2,skill3").build();
			break;
		default:
			output = Response.status(404).entity("Invalid data request: " + ontologyRequest).build();
		}
		return output;
	}

	private Response event() {
		return Response.status(404).build();
	}

	private String OntologyStart() {
		try {
			CoreOntology.getInstance();
			return "Daemon Ontology Manager started";
		} catch (InterruptedException e) {
			return "failed to start Daemon Ontology Manager";
		}
	}

	private String getLoggerData() {
		return CoreOntology.getLoggerData();
	}

	private String OntologyStop() {
		if (CoreOntology.isRunning()) {
			CoreOntology.killInstance();
			return "Daemon Ontology Manager stopped";
		} else
			return "Daemon Ontology Manager was not running";
	}

	private String OntologyStatus() {
		if (CoreOntology.isRunning()) {
			return "Running";
		}
		return "Online";
	}

	private String DemoStatus() {
		try {
			CoreOntology.getInstance();
			CoreOntology.DemoStart();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		return "Starting Daemon Ontology Manager Demo";
	}

	// Daemon Ontology Embedded Version

	@GET
	@Path("/getsoss")
	@Produces(MediaType.APPLICATION_JSON)
	public List<SoS> getSOSs() {
		List<SoS> soss = this.loadOntDatabaseSoSs();
		return soss;
	}
	
	private List<SoS> loadOntDatabaseSoSs() {
		List<SoS> soSs = new ArrayList<SoS>();
		JSONParser parser = new JSONParser();
		System.out.println("########### Start ###########");
		try (FileReader registro = new FileReader("ontology.json");) {
			JSONArray array = (JSONArray) parser.parse(registro);
			for (Object jo : array) {
				soSs.add(SoS.fromJSON((JSONObject) jo));
				System.out.println(soSs.get(soSs.size() - 1));
				System.out.println(soSs.get(soSs.size() - 1).toJson());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return soSs;
	}

	@GET
	@Path("/getdevices")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Device> getDevices(@DefaultValue("") @QueryParam(OntologyEntitiesNames.SOS_ID) String sosID) {
		List<Device> devices = this.loadOntDatabaseDevices(sosID);
		return devices;
	}

	private List<Device> loadOntDatabaseDevices(String sosID) {
		List<Device> devices = new ArrayList<Device>();
		JSONParser parser = new JSONParser();
		System.out.println("########### Start ###########");
		try (FileReader registro = new FileReader("ontology.json");) {
			JSONArray array = (JSONArray) parser.parse(registro);
			/**
			 * for each SoS
			 */
			for (Object joSoS : array) {
				SoS currentSoS = SoS.fromJSON((JSONObject) joSoS);
				/**
				 * if the current SoS is the one we search
				 */

				if (currentSoS.getSosId().equals(sosID)) {
					JSONArray devicesJson = (JSONArray) ((JSONObject) joSoS).get(OntologyEntitiesNames.DEVICES);
					if (devicesJson == null) {
						return devices;
					}
					System.out.println(devicesJson.toJSONString());
					/**
					 * for each Device associated with the SoS
					 */

					for (Object deviceObject : devicesJson) {
						devices.add(Device.fromJSON((JSONObject) deviceObject));
						System.out.println(devices.get(devices.size() - 1));
						System.out.println(devices.get(devices.size() - 1).toJson());
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return devices;

	}

	@GET
	@Path("/getcomponents")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Component> getComponents(@DefaultValue("") @QueryParam(OntologyEntitiesNames.SOS_ID) String sosID,
			@QueryParam(OntologyEntitiesNames.DEVICE_ID) String deviceID) {

		List<Component> components = new ArrayList<Component>();
		JSONParser parser = new JSONParser();
		System.out.println("########### Start ###########");
		try (FileReader registro = new FileReader("ontology.json");) {
			JSONArray array = (JSONArray) parser.parse(registro);
			// for each SoS
			for (Object joSoS : array) {
				SoS currentSoS = SoS.fromJSON((JSONObject) joSoS);
				// if the current SoS is the one we search
				if (currentSoS.getSosId().equals(sosID)) {
					JSONArray devicesJson = (JSONArray) ((JSONObject) joSoS).get(OntologyEntitiesNames.DEVICES);
					if (devicesJson != null) {
						System.out.println(devicesJson.toJSONString());
						// for each Device associated with the SoS
						for (Object deviceObject : devicesJson) {
							Device currentDevice = Device.fromJSON((JSONObject) deviceObject);
							// if current device is the one we search
							if (currentDevice.getDeviceId().equals(deviceID)) {
								JSONArray componentsJson = (JSONArray) ((JSONObject) deviceObject)
										.get(OntologyEntitiesNames.COMPONENTS);
								if (componentsJson != null) {
									System.out.println(componentsJson.toJSONString());
									// for each Device associated with the SoS
									for (Object componentObject : componentsJson) {
										components.add(Component.fromJSON((JSONObject) componentObject));
										System.out.println(components.get(components.size() - 1));
										System.out.println(components.get(components.size() - 1).toJson());
									}
								}
							}
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return components;
	}

	@GET
	@Path("/getskills")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Skill> getSkills(@DefaultValue("") @QueryParam(OntologyEntitiesNames.SOS_ID) String sosID,
			@QueryParam(OntologyEntitiesNames.DEVICE_ID) String deviceID,
			@QueryParam(OntologyEntitiesNames.COMPONENT_ID) String componentID) {

		List<Skill> skills = new ArrayList<Skill>();
		JSONParser parser = new JSONParser();
		System.out.println("########### Start ###########");
		try (FileReader registro = new FileReader("ontology.json");) {
			JSONArray array = (JSONArray) parser.parse(registro);
			// for each SoS
			for (Object joSoS : array) {
				SoS currentSoS = SoS.fromJSON((JSONObject) joSoS);
				// if the currentSoS is the one we search

				if (currentSoS.getSosId().equals(sosID)) {
					JSONArray devicesJson = (JSONArray) ((JSONObject) joSoS).get(OntologyEntitiesNames.DEVICES);
					if (devicesJson != null) {
						System.out.println(devicesJson.toJSONString());
						// for each Device associated with the SoS
						for (Object deviceObject : devicesJson) {
							Device currentDevice = Device.fromJSON((JSONObject) deviceObject);
							// if current device is the one we search
							if (currentDevice.getDeviceId().equals(deviceID)) {
								JSONArray componentsJson = (JSONArray) ((JSONObject) deviceObject)
										.get(OntologyEntitiesNames.COMPONENTS);
								if (componentsJson != null) {
									System.out.println(componentsJson.toJSONString()); // for each Device associated
																						// with the SoS
									for (Object componentObject : componentsJson) {
										Component currentComponent = Component.fromJSON((JSONObject) componentObject); 
										// retrieve component with id  "componentID"
										if (currentComponent.getComponentId().equals(componentID)) {
											JSONArray skillsJson = (JSONArray) ((JSONObject) componentObject)
													.get(OntologyEntitiesNames.SKILLS);

											if (skillsJson != null) {
												System.out.println(skillsJson.toJSONString()); 
												// for each Device associated with the SoS
												for (Object skillObject : skillsJson) {
													skills.add(Skill.fromJSON((JSONObject) skillObject));
													System.out.println(skills.get(skills.size() - 1));
													System.out.println(skills.get(skills.size() - 1).toJson());
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return skills;
	}
	
	/**
	 * 
	 * "OntologyRequest" :{ 
	 * 		"operationName": "getrules", 
	 * 		"sosId" : "", 
	 * 		"deviceId" : "", 
	 * 		"componentId" : "", 
	 * 		"skillId":""} 
	 * 
	 * @param sosID
	 * @param deviceID
	 * @param componentID
	 * @param skillID
	 * @return
	 */
	@GET
	@Path("/getrules")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Rule> getRules(@DefaultValue("") @QueryParam(OntologyEntitiesNames.SOS_ID) String sosID,
			@QueryParam(OntologyEntitiesNames.DEVICE_ID) String deviceID,
			@QueryParam(OntologyEntitiesNames.COMPONENT_ID) String componentID,
			@QueryParam(OntologyEntitiesNames.SKILL_ID) String skillID){

		List<Rule> rules = new ArrayList<Rule>();
		
		JSONParser parser = new JSONParser();
		System.out.println("########### Start ###########");
		try (FileReader registro = new FileReader("ontology.json");) {
			JSONArray array = (JSONArray) parser.parse(registro);
			// for each SoS
			for (Object joSoS : array) {
				SoS currentSoS = SoS.fromJSON((JSONObject) joSoS);
				// if the currentSoS is the one we search

				if (currentSoS.getSosId().equals(sosID)) {
					JSONArray devicesJson = (JSONArray) ((JSONObject) joSoS).get(OntologyEntitiesNames.DEVICES);
					if (devicesJson != null) {
						System.out.println(devicesJson.toJSONString());
						// for each Device associated with the SoS
						for (Object deviceObject : devicesJson) {
							Device currentDevice = Device.fromJSON((JSONObject) deviceObject);
							// if current device is the one we search
							if (currentDevice.getDeviceId().equals(deviceID)) {
								JSONArray componentsJson = (JSONArray) ((JSONObject) deviceObject)
										.get(OntologyEntitiesNames.COMPONENTS);
								if (componentsJson != null) {
									System.out.println(componentsJson.toJSONString()); 
									// for each Device associated with the SoS
									for (Object componentObject : componentsJson) {
										Component currentComponent = Component.fromJSON((JSONObject) componentObject); 
										// retrieve the component with id "componentID"
										if (currentComponent.getComponentId().equals(componentID)) {
											JSONArray skillsJson = (JSONArray) ((JSONObject) componentObject)
													.get(OntologyEntitiesNames.SKILLS);

											if (skillsJson != null) {
												System.out.println(skillsJson.toJSONString()); 
												// for each Skill associated with the component
												for (Object skillObject : skillsJson) {
													
													Skill currentSkill = Skill.fromJSON((JSONObject) skillObject);
													// retreive the Skill with ID "skillID"
													if(currentSkill.getSkillId().equals(skillID)) {
														JSONArray rulesJson = (JSONArray) ((JSONObject) skillObject)
																.get(OntologyEntitiesNames.RULES);
														
														
														System.err.println(rulesJson.toJSONString());
														
														if (rulesJson != null) {
															for (Object ruleObject : rulesJson) {
																Rule currentRule = Rule.fromJSON((JSONObject) ruleObject);
																
																rules.add(currentRule);
																System.out.println(rules.get(rules.size() - 1));
																System.out.println(rules.get(rules.size() - 1).toJson());
															}
														}												
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return rules;
	}
	
	
	/**
	 * Added 2023.03.01: new operation based on what current implementation of BIECO UI
	 * @param sosID
	 * @param deviceID
	 * @param componentID
	 * @param skillIDs
	 * @return
	 */
	@GET
	@Path("/getabstractrules")
	@Produces(MediaType.APPLICATION_JSON)
	private Object getAbstractRules(
		@DefaultValue("") @QueryParam(OntologyEntitiesNames.SOS_ID) String sosID,
		@QueryParam(OntologyEntitiesNames.DEVICE_ID) String deviceID,
		@QueryParam(OntologyEntitiesNames.COMPONENT_ID) String componentID,
		@QueryParam(OntologyEntitiesNames.SKILL_IDS) JSONArray skillIDs){

	List<Rule> rules = new ArrayList<Rule>();
	
	ArrayList<String> arrayListSkillsIDs = new ArrayList<String>();
	
	for (Object skillObject : skillIDs) {
		System.out.println("skillIDs -> "+skillObject);
		if (skillObject instanceof String) {
	        String skillID = (String) skillObject;
	        arrayListSkillsIDs.add(skillID);
	    }
		System.out.println("arrayListSkillsIDs -> "+arrayListSkillsIDs);
	}
	
	//System.out.println("Array -> "+(String[]) skillIDs.toArray());
	
	//String[] skillsAsStringArray = (String[]) skillIDs.toArray();
	
	System.out.println("############### HERE ###################");
	
//	ArrayList<String> arrayListSkillsIDs = new ArrayList<String>(Arrays.asList(skillsAsStringArray));
	

	JSONParser parser = new JSONParser();
	System.out.println("########### Start ###########");
	try (FileReader registro = new FileReader("ontology.json");) {
		JSONArray array = (JSONArray) parser.parse(registro);
		// for each SoS
		for (Object joSoS : array) {
			SoS currentSoS = SoS.fromJSON((JSONObject) joSoS);
			// if the currentSoS is the one we search

			if (currentSoS.getSosId().equals(sosID)) {
				JSONArray devicesJson = (JSONArray) ((JSONObject) joSoS).get(OntologyEntitiesNames.DEVICES);
				if (devicesJson != null) {
					System.out.println(devicesJson.toJSONString());
					// for each Device associated with the SoS
					for (Object deviceObject : devicesJson) {
						Device currentDevice = Device.fromJSON((JSONObject) deviceObject);
						// if current device is the one we search
						if (currentDevice.getDeviceId().equals(deviceID)) {
							JSONArray componentsJson = (JSONArray) ((JSONObject) deviceObject)
									.get(OntologyEntitiesNames.COMPONENTS);
							if (componentsJson != null) {
								System.out.println(componentsJson.toJSONString()); 
								// for each Device associated with the SoS
								for (Object componentObject : componentsJson) {
									Component currentComponent = Component.fromJSON((JSONObject) componentObject); 
									// retrieve the component with id "componentID"
									if (currentComponent.getComponentId().equals(componentID)) {
										JSONArray skillsJson = (JSONArray) ((JSONObject) componentObject)
												.get(OntologyEntitiesNames.SKILLS);

										if (skillsJson != null) {
											System.out.println(skillsJson.toJSONString()); 
											// for each Skill associated with the component
											for (Object skillObject : skillsJson) {
												
												Skill currentSkill = Skill.fromJSON((JSONObject) skillObject);
												// retreive the Skill with ID "skillID"
												
												if(arrayListSkillsIDs.contains(currentSkill.getSkillId())) {
												
//												if(currentSkill.getSkillId().equals(skillID)) {
													JSONArray rulesJson = (JSONArray) ((JSONObject) skillObject)
															.get(OntologyEntitiesNames.RULES);
													
													
													System.err.println(rulesJson.toJSONString());
													
													if (rulesJson != null) {
														for (Object ruleObject : rulesJson) {
															Rule currentRule = Rule.fromJSONAbstractRule((JSONObject) ruleObject, currentSkill.getSkillId());
															
															rules.add(currentRule);
															System.out.println(rules.get(rules.size() - 1));
															System.out.println(rules.get(rules.size() - 1).toString());
															System.out.println("OntologyManager.getAbstractRules()");
														}
													}												
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (ParseException e) {
		e.printStackTrace();
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return rules;
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private static List<SoS> loadOntDatabase() {
		List<SoS> soSs = new ArrayList<SoS>();
		JSONParser parser = new JSONParser();
		System.out.println("########### Start ###########");
		try (FileReader registro = new FileReader("ontology.json");) {
			System.out.println(registro.toString());

			System.out.println(registro);

			JSONArray array = (JSONArray) parser.parse(registro);

			System.out.println(array.toJSONString());
			for (Object jo : array) {

				// to be removed // soSs.add(SoS.fromJSON((JSONObject) jo));

				SoS currentSoS = new SoS((JSONObject) jo);
				currentSoS.parse();
				soSs.add(currentSoS);

				System.out.println(soSs.get(soSs.size() - 1).toJson());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return soSs;
	}

	private void dumpOntDatabase(List<SoS> soSs) {
		JSONArray jsonSos = new JSONArray();
		for (SoS s : soSs) {
			jsonSos.add(s.toJson());
		}
		try (FileWriter registro = new FileWriter("ontology.json");) {
			jsonSos.writeJSONString(registro);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void dumpDatabase(List<Student> students) {
		JSONArray jsonStudents = new JSONArray();
		for (Student s : students) {
			jsonStudents.add(s.toJson());
		}
		try (FileWriter registro = new FileWriter("registro.json");) {
			jsonStudents.writeJSONString(registro);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@POST

	@Path("/biecointerfaceOLD")

	@Consumes(MediaType.APPLICATION_JSON)
	public Response biecointerfaceOLD(String jsonMessage, @Context HttpHeaders headers) {
		JSONParser parser = new JSONParser();
		JSONObject bodyMessage;
		Response output = null;

		System.out.println(jsonMessage);

		try {
			bodyMessage = (JSONObject) parser.parse(jsonMessage);
			switch ((String) bodyMessage.get("messageType")) {
			case "Data":
				output = this.data((JSONObject) bodyMessage.get("event"));
				break;

			default:
				output = Response.status(400).entity("invalid messageType").build();
				break;
			}
		} catch (ParseException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		return output;
	}

	/**
	 * OntologyRequest is a JSON Object containing { "operationName": "_",
	 * "idOntResource" : "45" }
	 * 
	 * We have defined Four operation: 1) getsoss 2) getdevices 3) getcomponents 4)
	 * getskills
	 * 
	 * 
	 * @param ontologyRequest
	 * @return
	 */
	private Response data(JSONObject event) {

		Response output;

		System.out.println(event.toJSONString());

		JSONObject ontologyRequest = (JSONObject) event.get(OntologyEntitiesNames.ONTOLOGY_REQUEST);

		System.out.println(ontologyRequest.toJSONString());

		String operationName = (String) ontologyRequest.get(OntologyEntitiesNames.OPERATION_NAME);
		System.out.println(operationName);

		String result;
		String sosID;
		String deviceID;
		String componentID;
		String skillID;

		switch (operationName) {
		case "getsoss":

			result = getSOSs().toString();
			System.out.println(result);
			output = Response.status(200).entity(result).build();

			break;
		case "getdevices":
			sosID = (String) ontologyRequest.get(OntologyEntitiesNames.SOS_ID);
			System.out.println(sosID);

			result = getDevices(sosID).toString();
			output = Response.status(200).entity(result).build();
			break;
		case "getcomponents":

			sosID = (String) ontologyRequest.get(OntologyEntitiesNames.SOS_ID);
			System.out.println(sosID);

			deviceID = (String) ontologyRequest.get(OntologyEntitiesNames.DEVICE_ID);
			System.out.println(deviceID);

			result = getComponents(sosID, deviceID).toString();

			output = Response.status(200).entity(result).build();
			break;
		case "getskills":

			sosID = (String) ontologyRequest.get(OntologyEntitiesNames.SOS_ID);
			System.out.println(sosID);

			deviceID = (String) ontologyRequest.get(OntologyEntitiesNames.DEVICE_ID);
			System.out.println(deviceID);

			componentID = (String) ontologyRequest.get(OntologyEntitiesNames.COMPONENT_ID);
			System.out.println(componentID);

			result = getSkills(sosID, deviceID, componentID).toString();

			output = Response.status(200).entity(result).build();
			break;
			
		case "getrules":
			
			sosID = (String) ontologyRequest.get(OntologyEntitiesNames.SOS_ID);
			System.out.println(sosID);

			deviceID = (String) ontologyRequest.get(OntologyEntitiesNames.DEVICE_ID);
			System.out.println(deviceID);

			componentID = (String) ontologyRequest.get(OntologyEntitiesNames.COMPONENT_ID);
			System.out.println(componentID);
			
			skillID = (String) ontologyRequest.get(OntologyEntitiesNames.SKILL_ID);
			System.out.println(skillID);

			result = getRules(sosID, deviceID, componentID, skillID).toString();

			output = Response.status(200).entity(result).build();
			
			break;
			
		case "getabstractrules":
			
			
			sosID = (String) ontologyRequest.get(OntologyEntitiesNames.SOS_ID);
			System.out.println(sosID);

			deviceID = (String) ontologyRequest.get(OntologyEntitiesNames.DEVICE_ID);
			System.out.println(deviceID);

			componentID = (String) ontologyRequest.get(OntologyEntitiesNames.COMPONENT_ID);
			System.out.println(componentID);
			
			JSONArray skillIDs = (JSONArray) ontologyRequest.get(OntologyEntitiesNames.SKILL_IDS);
			System.out.println(skillIDs.toJSONString());

			result = getAbstractRules(sosID, deviceID, componentID, skillIDs).toString();

			output = Response.status(200).entity(result).build();
			
			
			
			break;
			
		case "uploadontology":
			
			
			try {
				
			String ontologyContent = (String) ontologyRequest.get(OntologyEntitiesNames.ONTOLOGY_CONTENT);
			System.out.println("Content of the Uploaded Ontology File -> "+ontologyContent);
			
			System.out.println("JSONObject jsonObjectSOSs = (JSONObject) ontologyRequest.get(OntologyEntitiesNames.ONTOLOGY_CONTENT);");
			
			JSONParser parser = new JSONParser();
			JSONObject content = (JSONObject) parser.parse(ontologyContent);
			
			
			
			JSONArray sossArray = (JSONArray) content.get(OntologyEntitiesNames.ONTOLOGY_SOSS); 
			
			if (sossArray == null) {
				System.err.println("PLEASE see this:: SoSs Array is null"+sossArray);;
			}
			
			System.out.println(content.get(OntologyEntitiesNames.ONTOLOGY_SOSS).toString());
			
			System.out.println(sossArray.toJSONString());
			
			dumpOntologyDatabase(sossArray);
			
			output = Response.status(404).entity("Uploading Ontology: " + ontologyRequest).build();
			
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				output = Response.status(404).entity("Uploading Ontology: " + ontologyRequest).build();
				
			}

			
			break;
			
			
		default:
			output = Response.status(404).entity("Invalid data request: " + ontologyRequest).build();
		}
		return output;
	}


	private void dumpOntologyDatabase(JSONArray jsonSos) {
		System.out.println("START ::::::::: Upload Entire Database");
		
		try (FileWriter registro = new FileWriter("ontology.json");) {
			jsonSos.writeJSONString(registro);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("END ::::::::: Upload Entire Database");
	}

}
