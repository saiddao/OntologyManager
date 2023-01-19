package it.cnr.isti.sedc.bieco.ontologyManager;

import javax.xml.bind.annotation.XmlRootElement;

import org.json.simple.JSONObject;

@XmlRootElement(name = "component")
public class Component {
	private String componentName, componentId;
	private JSONObject componentJsonObject;
	
	public Component() {
		// TODO Auto-generated constructor stub
	}
	
	public Component(JSONObject device) {
		// TODO Auto-generated constructor stub
		componentJsonObject = device;
		
		this.componentName = new String();
		this.componentId = new String();
		
	}

	public Component(String cName, String cID) {
		// TODO Auto-generated constructor stub
		this.componentName = cName;
		this.componentId = cID;

	}


	public JSONObject toJson() {
		JSONObject result = new JSONObject();
		
		result.put(OntologyEntitiesNames.COMPONENT_NAME, this.componentName);
		result.put(OntologyEntitiesNames.COMPONENT_ID, this.componentId);
		return result;
	}
	
	
	public static Component fromJSON(JSONObject object) {
		return new Component((String) object.get(OntologyEntitiesNames.COMPONENT_NAME), (String) object.get(OntologyEntitiesNames.COMPONENT_ID));			
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public String getComponentId() {
		return componentId;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

	public JSONObject getComponentJsonObject() {
		return componentJsonObject;
	}

	public void setComponentJsonObject(JSONObject componentJsonObject) {
		this.componentJsonObject = componentJsonObject;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return toJson().toJSONString();
	}
}
