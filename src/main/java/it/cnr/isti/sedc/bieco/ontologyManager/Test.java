package it.cnr.isti.sedc.bieco.ontologyManager;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		loadDatabase();
		
		loadOntDatabase();

	}



private static List<Student> loadDatabase() {
	List<Student> students = new ArrayList<Student>();
	JSONParser parser = new JSONParser();
	System.out.println("########### Start ###########");
	try (FileReader registro = new FileReader("registro.json");) {
		System.out.println(registro.toString());
		
		System.out.println(registro);		
		
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
			
			//to be removed
			//soSs.add(SoS.fromJSON((JSONObject) jo));
			
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



}