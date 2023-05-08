package it.cnr.isti.sedc.bieco.ontologyManager.rest.UC1;

import org.w3c.dom.Node;

public class EStructuralFeatures extends ECoreUC1 {
	
	
	private String eType;
	private String defaultValueLiteral;
	
	public EStructuralFeatures() {
		// TODO Auto-generated constructor stub
	}

	public EStructuralFeatures(String type, String eName) {
		super(type, eName);
		// TODO Auto-generated constructor stub
	}

	public EStructuralFeatures(Node epackageChild) {
		// TODO Auto-generated constructor stub
		super(epackageChild);
		
		
		if(geteNode().getAttributes().getNamedItem(UtilsUC1.eType) != null) {
			this.eType = geteNode().getAttributes().getNamedItem(UtilsUC1.eType).getTextContent();
		}
		if(geteNode().getAttributes().getNamedItem(UtilsUC1.defaultValueLiteral) != null) {
			this.defaultValueLiteral = geteNode().getAttributes().getNamedItem(UtilsUC1.defaultValueLiteral).getTextContent();
		}
		
	}

	public String geteType() {
		return eType;
	}

	public void seteType(String eType) {
		this.eType = eType;
	}
	
	public String getCleanedType() {
		if(eType ==null){
			return null;
		}
		return eType.substring(3);
	}
	
	public String getDefaultValueLiteral() {
		return defaultValueLiteral;
	}

	public void setDefaultValueLiteral(String defaultValueLiteral) {
		this.defaultValueLiteral = defaultValueLiteral;
	}

	@Override
	public void parseNode() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getOMJson() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return "EStructuralFeatures [eType=" + eType + ", defaultValueLiteral=" + defaultValueLiteral
				+ ", getXsi_type()=" + getXsi_type() + ", getName()=" + getName() + "]";
	}
	
}
