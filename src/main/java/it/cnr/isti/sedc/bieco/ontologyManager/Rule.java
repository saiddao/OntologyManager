package it.cnr.isti.sedc.bieco.ontologyManager;

import javax.xml.bind.annotation.XmlRootElement;

import org.json.simple.JSONObject;

@XmlRootElement(name = "rule")
public class Rule {
	private String ruleName, ruleId;
	private JSONObject ruleJsonObject;
	
	public Rule() {
		// TODO Auto-generated constructor stub
	}
	
	public Rule(JSONObject rule) {
		// TODO Auto-generated constructor stub
		ruleJsonObject = rule;
		
		this.ruleName = new String();
		this.ruleId = new String();
		
	}

	public Rule(String rName, String rID) {
		// TODO Auto-generated constructor stub
		this.ruleName = rName;
		this.ruleId = rID;

	}


	public JSONObject toJson() {
		
		JSONObject result = new JSONObject();
		
		if (!(this.ruleName.equals("")&& this.ruleId.equals(""))) {
			result.put(OntologyEntitiesNames.RULE_NAME, this.ruleName);
			result.put(OntologyEntitiesNames.Rule_ID, this.ruleId);
		} else {
			result = this.ruleJsonObject;
		}
		
		return result;
	}
	
	
	public static Rule fromJSON(JSONObject object) {
		
		String ruleAsJSONResult = object.toJSONString();
		System.out.println(ruleAsJSONResult);
		
//		return new Rule((String) object.get(OntologyEntitiesNames.RULE_NAME), (String) object.get(OntologyEntitiesNames.Rule_ID));			
		return new Rule(object);
	}

	
	
	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public JSONObject getRuleJsonObject() {
		return ruleJsonObject;
	}

	public void setRuleJsonObject(JSONObject ruleJsonObject) {
		this.ruleJsonObject = ruleJsonObject;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return toJson().toJSONString();
	}
	
}
