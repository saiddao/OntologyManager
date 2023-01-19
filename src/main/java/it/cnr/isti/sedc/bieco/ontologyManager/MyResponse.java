package it.cnr.isti.sedc.bieco.ontologyManager;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class MyResponse {
	String message;

	public MyResponse() {
	}

	public MyResponse(String m) {
		this.message = m;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}