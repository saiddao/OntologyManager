package it.cnr.isti.sedc.bieco.daemonmanager;

import javax.xml.bind.annotation.XmlRootElement;
import org.json.simple.JSONObject;

@XmlRootElement(name = "student")
public class Student {
	private String fname, lname;
	private String streetAddress;
	private long addressNo;
	private double grade;
	private long studentId;

	public Student() {
	}

	public Student(String fname, String lname, String street, long no, long id, double grade) {
		this.fname = fname;
		this.lname = lname;
		this.streetAddress = street;
		this.addressNo = no;
		this.grade = grade;
		this.studentId = id;
	}

	public String getLname() {
		return this.lname;
	}

	public String getFname() {
		return this.fname;
	}

	public long getId() {
		return this.studentId;
	}

	public long getAddressNo() {
		return this.addressNo;
	}

	public String getStreet() {
		return this.streetAddress;
	}

	public double getGrade() {
		return this.grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public void setStreet(String street) {
		this.streetAddress = street;
	}

	public void setStreetNo(int no) {
		this.addressNo = no;
	}

	public void setId(int id) {
		this.studentId = id;
	}

	public static Student fromJSON(JSONObject object) {
		return new Student((String) object.get("fname"), (String) object.get("lname"),
				(String) ((JSONObject) object.get("address")).get("street"),
				(long) ((JSONObject) object.get("address")).get("no"), (long) object.get("id"),
				(double) object.get("grade"));
	}

	public JSONObject toJson() {
		JSONObject result = new JSONObject();
		result.put("fname", this.fname);
		result.put("lname", this.lname);
		result.put("id", this.studentId);
		result.put("grade", this.grade);
		JSONObject address = new JSONObject();
		address.put("no", this.addressNo);
		address.put("street", this.streetAddress);
		result.put("address", address);
		return result;
	}
}