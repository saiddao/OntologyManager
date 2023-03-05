package it.cnr.isti.sedc.bieco.ontologyManager.utils;

import java.util.ArrayList;

import org.json.simple.JSONArray;

public class OntlologyManagerUtils {
	
	
	public OntlologyManagerUtils() {
		// TODO Auto-generated constructor stub
	}
	
	public static ArrayList<String> fromJSONArray2ArrayList (JSONArray skillIDs) {
		ArrayList<String> arrayListSkillsIDs = new ArrayList<String>();
		
		for (Object skillObject : skillIDs) {
			System.out.println("skillIDs -> "+skillObject);
			if (skillObject instanceof String) {
		        String skillID = (String) skillObject;
		        arrayListSkillsIDs.add(skillID);
		    }
			System.out.println("arrayListSkillsIDs -> "+arrayListSkillsIDs);
		}
		return arrayListSkillsIDs;
	}
}
