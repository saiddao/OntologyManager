package it.cnr.isti.sedc.bieco.ontologyManager.rest.UC1;

import org.w3c.dom.Node;

abstract public  class ECoreUC1 {
	
	private String xsi_type;
	private String name;
	private Node eNode;
	private int myID;
	public ECoreUC1() {
		// TODO Auto-generated constructor stub
		
	}
	
	
	public ECoreUC1(String type, String eName) {
		// TODO Auto-generated constructor stub
		this.xsi_type = type;
		this.name = eName;
	}
	
	public ECoreUC1(Node eNode) {
		// TODO Auto-generated constructor stub
		this.eNode = eNode;
		this.name = eNode.getAttributes().getNamedItem(UtilsUC1.name).getTextContent();
		this.xsi_type = eNode.getAttributes().getNamedItem(UtilsUC1.XSI_TYPE).getTextContent();
	}


	public String getXsi_type() {
		return xsi_type;
	}

	public void setXsi_type(String xsi_type) {
		this.xsi_type = xsi_type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		return "ECoreUC1 [xsi_type=" + xsi_type + ", name=" + name + "]";
	}


	public Node geteNode() {
		return eNode;
	}


	public void seteNode(Node eNode) {
		this.eNode = eNode;
	}
	
	abstract public void parseNode();
	
	abstract public String getOMJson();
	
	public int getMyID() {
		return myID;
	}


	public void setMyID(int myID) {
		this.myID = myID;
	}
	
	
}
