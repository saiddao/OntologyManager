package it.cnr.isti.sedc.bieco.ontologyManager;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@XmlRootElement(name = "skill")
public class Skill {
	private String skillName, skillId;
	private JSONObject skillJsonObject;
	
	public Skill() {
		// TODO Auto-generated constructor stub
	}
	
	public Skill(JSONObject skill) {
		// TODO Auto-generated constructor stub
		skillJsonObject = skill;
		
		this.skillName = new String();
		this.skillId = new String();
		
	}

	public Skill(String sName, String sID) {
		// TODO Auto-generated constructor stub
		this.skillName = sName;
		this.skillId = sID;

	}


	public JSONObject toJson() {
		JSONObject result = new JSONObject();
		
		result.put(OntologyEntitiesNames.SKILL_NAME, this.skillName);
		result.put(OntologyEntitiesNames.SKILL_ID, this.skillId);
		return result;
	}
	
	
	public static Skill fromJSON(JSONObject object) {
		return new Skill((String) object.get(OntologyEntitiesNames.SKILL_NAME), (String) object.get(OntologyEntitiesNames.SKILL_ID));			
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public String getSkillId() {
		return skillId;
	}

	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}

	public JSONObject getSkillJsonObject() {
		return skillJsonObject;
	}

	public void setSkillJsonObject(JSONObject skillJsonObject) {
		this.skillJsonObject = skillJsonObject;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return toJson().toJSONString();
	}

}
