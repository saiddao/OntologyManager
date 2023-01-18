package it.cnr.isti.sedc.bieco.daemonmanager;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


@Path("/")
public class OntologyManager {
	public OntologyManager() {
	}

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
		// TODO Auto-generated method stub
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
			// * for each SoS
			for (Object joSoS : array) {
				SoS currentSoS = SoS.fromJSON((JSONObject) joSoS);
				// * if the current SoS is the one we search
				if (currentSoS.getSosId().equals(sosID)) {
					JSONArray devicesJson = (JSONArray) ((JSONObject) joSoS).get(OntologyEntitiesNames.DEVICES);
					if (devicesJson != null) {
						System.out.println(devicesJson.toJSONString());
						// * for each Device associated with the SoS
						for (Object deviceObject : devicesJson) {
							Device currentDevice = Device.fromJSON((JSONObject) deviceObject);
							// * if current device is the one we search
							if (currentDevice.getDeviceId().equals(deviceID)) {
								JSONArray componentsJson = (JSONArray) ((JSONObject) deviceObject)
										.get(OntologyEntitiesNames.COMPONENTS);
								if (componentsJson != null) {
									System.out.println(componentsJson.toJSONString());
									// * for each Device associated with the SoS
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
										Component currentComponent = Component.fromJSON((JSONObject) componentObject);
										// * retrieve the component with id "componentID"
										if (currentComponent.getComponentId().equals(componentID)) {
											JSONArray skillsJson = (JSONArray) ((JSONObject) componentObject)
													.get(OntologyEntitiesNames.SKILLS);

											if (skillsJson != null) {
												System.out.println(skillsJson.toJSONString());
												// * for each Device associated with the SoS
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

				// to be removed
				// soSs.add(SoS.fromJSON((JSONObject) jo));

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
	@Path("/biecointerface")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response biecointerface(String jsonMessage, @Context HttpHeaders headers) {
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
		} catch (ParseException e) {
			// TODO Auto-generated catch block
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
			
			componentID  = (String) ontologyRequest.get(OntologyEntitiesNames.COMPONENT_ID);
			System.out.println(componentID);
 
			
			result = getSkills(sosID, deviceID, componentID).toString();
			
			
			output = Response.status(200).entity(result).build();
			break;
		default:
			output = Response.status(404).entity("Invalid data request: " + ontologyRequest).build();
		}
		return output;
	}

	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Student> searchByLastName(@DefaultValue("") @QueryParam("lname") String lastName) {
		List<Student> students = this.loadDatabase();
		ArrayList<Student> result = new ArrayList<Student>();
		for (Student s : students) {
			if (s.getLname().equals(lastName)) {
				result.add(s);
			}
		}
		return result;
	}

	@POST
	@Path("/setGrade")
	@Produces(MediaType.APPLICATION_JSON)
	public MyResponse setGrade(@QueryParam("id") int id, @QueryParam("grade") double grade) {
		List<Student> students = this.loadDatabase();
		for (Student s : students) {
			if (s.getId() == id) {
				s.setGrade(grade);
				this.dumpDatabase(students);
				return new MyResponse("student found");
			}
		}
		return new MyResponse("student not found");
	}

	private List<Student> loadDatabase() {
		List<Student> students = new ArrayList<Student>();
		JSONParser parser = new JSONParser();
		System.out.println("########### Start ###########");
		try (FileReader registro = new FileReader("registro.json");) {
			JSONArray array = (JSONArray) parser.parse(registro);
			for (Object jo : array) {
				students.add(Student.fromJSON((JSONObject) jo));
				System.out.println(students.get(students.size() - 1));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return students;
	}

}