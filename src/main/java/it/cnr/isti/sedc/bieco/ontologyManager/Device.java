package it.cnr.isti.sedc.bieco.ontologyManager;

import javax.xml.bind.annotation.XmlRootElement;

import org.json.simple.JSONObject;

@XmlRootElement(name = "device")
public class Device {
	private String deviceName, deviceId;
	private JSONObject deviceJsonObject;
	
	public Device() {
		// TODO Auto-generated constructor stub
	}
	
	public Device(JSONObject device) {
		// TODO Auto-generated constructor stub
		deviceJsonObject = device;
		
		this.deviceName = new String();
		this.deviceId = new String();
		
	}

	public Device(String dName, String dID) {
		// TODO Auto-generated constructor stub
		this.deviceName = dName;
		this.deviceId = dID;

	}

	public JSONObject getDeviceJsonObject() {
		return deviceJsonObject;
	}

	public void setDeviceJsonObject(JSONObject deviceJsonObject) {
		this.deviceJsonObject = deviceJsonObject;
	}

	public void parse() {
		// TODO Auto-generated method stub
		
		this.deviceName = (String) this.deviceJsonObject.get(OntologyEntitiesNames.DEVICE_NAME); 
		this.deviceId = (String) this.deviceJsonObject.get(OntologyEntitiesNames.DEVICE_ID);
				
	}
	
	
	
	public JSONObject toJson() {
		JSONObject result = new JSONObject();
		
		result.put(OntologyEntitiesNames.DEVICE_NAME, this.deviceName);
		result.put(OntologyEntitiesNames.DEVICE_ID, this.deviceId);
		return result;
	}
	
	
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	
	
	
	
	public static Device fromJSON(JSONObject object) {
		return new Device((String) object.get(OntologyEntitiesNames.DEVICE_NAME), (String) object.get(OntologyEntitiesNames.DEVICE_ID));			
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return toJson().toJSONString();
	}
}
