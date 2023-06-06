package it.cnr.isti.sedc.bieco.ontologyManager.rest.UC1;

public class MoniitoringInstantiatedRules {

	
	
	
	
	
	
	
	public static String getInstantieatedRules() {
		// TODO Auto-generated method stub
		String rule = getRule();
		System.out.println(rule);
		return rule;
		
		
		
	}

	private static String getRule() {
		// TODO Auto-generated method stub
		
		StringBuilder builder = new StringBuilder();
		
		
		
		builder.append("//created Jan 24, 2023 - 17:41:32");
		builder.append("\n");
		builder.append("package it.cnr.isti.labsedc.concern.event;");
		builder.append("\n");
		builder.append("import it.cnr.isti.labsedc.concern.event.ConcernAbstractEvent;");
		builder.append("\n");
		builder.append("import it.cnr.isti.labsedc.concern.event.ConcernProbeEvent;");
		builder.append("\n");
		builder.append("import it.cnr.isti.labsedc.concern.event.ConcernICTGatewayEvent;");
		builder.append("\n");
		builder.append("import it.cnr.isti.labsedc.concern.utils.KieLauncher;");
		builder.append("\n");
		builder.append("import it.cnr.isti.labsedc.concern.utils.Calculator;");
		builder.append("\n");
		builder.append("import it.cnr.isti.labsedc.concern.notification.NotificationManager;");
		builder.append("\n");

		builder.append("//Check a sequence of events strictly");
		builder.append("\n");

		builder.append("dialect \"java\"");
		builder.append("\n");

		builder.append("declare ConcernICTGatewayEvent");
		builder.append("\n");
		builder.append("    @role( event )");
		builder.append("\n");
		builder.append("    @timestamp( timestamp )");
		builder.append("\n");
		builder.append("    @expires(2m)");
		builder.append("\n");
		builder.append("end");
		builder.append("\n");


		builder.append("declare FirstAndSecondEvent");
		builder.append("\n");
		builder.append("    @role( event )");
		builder.append("\n");
		builder.append("    @expires(2m)");
		builder.append("\n");
		builder.append("    @timestamp ( timestamp )");
		builder.append("\n");
		builder.append("    destinationID : String");
		builder.append("\n");
		builder.append("	senderID : String");
		builder.append("\n");
		builder.append("	first : ConcernICTGatewayEvent");
		builder.append("\n");
		builder.append("	second : ConcernICTGatewayEvent");
		builder.append("\n");
		builder.append("	name : String");
		builder.append("\n");
		builder.append("	timestamp : long");
		builder.append("\n");
		builder.append("end");
		builder.append("\n");

		builder.append("declare FirstAndSecondAndThirdEvent");
		builder.append("\n");
		builder.append("    @role( event )");
		builder.append("\n");
		builder.append("    @expires(2m)");
		builder.append("\n");
		builder.append("    @timestamp ( timestamp )");
		builder.append("\n");
		builder.append("	destinationID : String");
		builder.append("\n");
		builder.append("	senderID : String	");
		builder.append("\n");
		builder.append("	first : ConcernICTGatewayEvent");
		builder.append("\n");
		builder.append("	second: ConcernICTGatewayEvent");
		builder.append("\n");
		builder.append("	third : ConcernICTGatewayEvent");
		builder.append("\n");
		builder.append("	name: String");
		builder.append("\n");
		builder.append("	timestamp : long");
		builder.append("\n");
		builder.append("end");
		builder.append("\n");

		builder.append("rule \"checkFirst\"");
		builder.append("\n");
		builder.append("no-loop");
		builder.append("\n");
		builder.append("salience 1000");
		builder.append("\n");
		builder.append("dialect \"java\"");
		builder.append("\n");
		builder.append("when");
		builder.append("\n");
		builder.append("	$aEvent: ConcernICTGatewayEvent(");
		builder.append("\n");
		builder.append("		this.senderID == \"ICTGW_Probe\",");
		builder.append("\n");
		builder.append("		this.destinationID == \"Monitoring\",");
		builder.append("\n");
		builder.append("		this.name == \"AUTHENTICATION_REQUEST\",");
		builder.append("\n");
		builder.append("		this.consumed == false);");
		builder.append("\n");

		builder.append("then");
		builder.append("\n");
		builder.append("		$aEvent.setConsumed(true);");
		builder.append("\n");
		builder.append("		update($aEvent);");
		builder.append("\n");
		builder.append("		KieLauncher.printInfo(\"Session started - Rule fired:\" + drools.getRule().getName());	");
		builder.append("\n");	
		builder.append("end");
		builder.append("\n");

		builder.append("rule \"checkStarter\"");
		builder.append("\n");
		builder.append("no-loop");
		builder.append("\n");
		builder.append("salience 999");
		builder.append("\n");
		builder.append("dialect \"java\"");
		builder.append("\n");
		builder.append("when");
		builder.append("\n");
		builder.append("	$aEvent: ConcernICTGatewayEvent(");
		builder.append("\n");
		builder.append("		this.consumed == false);");
		builder.append("\n");
		builder.append("	not(ConcernICTGatewayEvent(");
		builder.append("\n");
		builder.append("		this.senderID == \"ICTGW_Probe\",");
		builder.append("\n");
		builder.append("		this.destinationID == \"Monitoring\",");
		builder.append("\n");
		builder.append("		this.name == \"AUTHENTICATION_REQUEST\",");
		builder.append("\n");
		builder.append("		this.consumed == true, ");
		builder.append("\n");
		builder.append("		this before $aEvent) || FirstAndSecondEvent() || FirstAndSecondAndThirdEvent()");
		builder.append("\n");
		builder.append("		);");
		builder.append("\n");
		builder.append("then");
		builder.append("\n");
		builder.append("		retract($aEvent);");
		builder.append("\n");
		builder.append("		NotificationManager.NotifyViolation($aEvent.getSenderID(), \"Unattended event - Rule fired:\" + drools.getRule().getName(), drools.getRule().getName(), drools.getRule().getMetaData());	");
		builder.append("\n");
		builder.append("end");
		builder.append("\n");



		builder.append("rule \"doubleStart\"");
		builder.append("\n");
		builder.append("no-loop");
		builder.append("\n");
		builder.append("salience 998");
		builder.append("\n");
		builder.append("dialect \"java\"");
		builder.append("\n");
		builder.append("when");
		builder.append("\n");
		builder.append("	$aEvent: ConcernICTGatewayEvent(");
		builder.append("\n");
		builder.append("		this.senderID == \"ICTGW_Probe\",");
		builder.append("\n");
		builder.append("		this.destinationID == \"Monitoring\",");
		builder.append("\n");
		builder.append("		this.name == \"AUTHENTICATION_REQUEST\",");
		builder.append("\n");
		builder.append("		this.consumed == true);");
		builder.append("\n");
				
		builder.append("	$bEvent: ConcernICTGatewayEvent(");
		builder.append("\n");
		builder.append("		this.senderID == \"ICTGW_Probe\",");
		builder.append("\n");
		builder.append("		this.destinationID == \"Monitoring\",");
		builder.append("\n");
		builder.append("		this.name == \"AUTHENTICATION_REQUEST\",");
		builder.append("\n");
		builder.append("		this.consumed == false,");
		builder.append("\n");
		builder.append("		this after $aEvent);");
		builder.append("\n");
		builder.append("then");
		builder.append("\n");
		builder.append("		retract($aEvent);");
		builder.append("\n");
		builder.append("		retract($bEvent);");
		builder.append("\n");
		builder.append("		NotificationManager.NotifyViolation($aEvent.getSenderID(), \"Unattended event - Rule fired:\" + drools.getRule().getName(), drools.getRule().getName(), drools.getRule().getMetaData());");
		builder.append("\n");	
		builder.append("end");
		builder.append("\n");

		builder.append("rule \"catchFirstAndCheckSecond\"");
		builder.append("\n");
		builder.append("no-loop");
		builder.append("\n");
		builder.append("salience 997");
		builder.append("\n");
		builder.append("dialect \"java\"");
		builder.append("\n");
		builder.append("when");
		builder.append("\n");
		builder.append("	$aEvent: ConcernICTGatewayEvent(");
		builder.append("\n");
		builder.append("		this.senderID == \"ICTGW_Probe\",");
		builder.append("\n");
		builder.append("		this.destinationID == \"Monitoring\",");
		builder.append("\n");
		builder.append("		this.name == \"AUTHENTICATION_REQUEST\",");
		builder.append("\n");
		builder.append("		this.consumed == true);");
		builder.append("\n");
				
		builder.append("	$bEvent: ConcernICTGatewayEvent(");
		builder.append("\n");
		builder.append("		this.senderID == $aEvent.getSenderID(),");
		builder.append("\n");
		builder.append("		this.destinationID == $aEvent.getDestinationID(),");
		builder.append("\n");
		builder.append("		this.name != \"REGISTRATION_RESPONSE\" || this.ICTMessageType != \"REGISTRATION_RESPONSE\" ||  this.ICTMessageCategory != \"REGISTRATION\",");
		builder.append("\n");
		builder.append("		this after $aEvent);");
		builder.append("\n");
		builder.append("then");
		builder.append("\n");
		builder.append("		retract($aEvent);");
		builder.append("\n");
		builder.append("		retract($bEvent);");
		builder.append("\n");
		builder.append("		NotificationManager.NotifyViolation($aEvent.getSenderID(), \"Unattended event - Rule fired:\" + drools.getRule().getName(), drools.getRule().getName(), drools.getRule().getMetaData());");
		builder.append("\n");	
		builder.append("end");
		builder.append("\n");


		builder.append("rule \"catchFirstAndSecond\"");
		builder.append("\n");
		builder.append("no-loop");
		builder.append("\n");
		builder.append("salience 996");
		builder.append("\n");
		builder.append("dialect \"java\"");
		builder.append("\n");
		builder.append("when");
		builder.append("\n");
		builder.append("	$aEvent: ConcernICTGatewayEvent(");
		builder.append("\n");
		builder.append("		this.senderID == \"ICTGW_Probe\",");
		builder.append("\n");
		builder.append("		this.destinationID == \"Monitoring\",");
		builder.append("\n");
		builder.append("		this.name == \"AUTHENTICATION_REQUEST\",");
		builder.append("\n");
		builder.append("		this.ICTMessageType == \"AUTHENTICATION_REQUEST\",");
		builder.append("\n");
		builder.append("		this.ICTMessageCategory == \"AUTHENTICATION\",");
		builder.append("\n");
		builder.append("		this.consumed == true");
		builder.append("\n");
		builder.append("		);");
		builder.append("\n");
				
		builder.append("	$bEvent: ConcernICTGatewayEvent(");
		builder.append("\n");
		builder.append("		this.senderID == $aEvent.getSenderID,");
		builder.append("\n");
		builder.append("		this.destinationID == $aEvent.getDestinationID,");
		builder.append("\n");
		builder.append("		this.name == \"REGISTRATION_RESPONSE\",");
		builder.append("\n");
		builder.append("		this.ICTMessageType == \"REGISTRATION_RESPONSE\",");
		builder.append("\n");
		builder.append("		this.ICTMessageCategory == \"REGISTRATION\",");
		builder.append("\n");
		builder.append("		this after $aEvent);");
		builder.append("\n");
			
		builder.append("then");
		builder.append("\n");
		builder.append("	FirstAndSecondEvent captured = new FirstAndSecondEvent();");
		builder.append("\n");
		builder.append("	captured.setTimestamp($aEvent.getTimestamp());");
		builder.append("\n");
		builder.append("	captured.setFirst($aEvent);");
		builder.append("\n");
		builder.append("	captured.setSecond($bEvent);");
		builder.append("\n");
		builder.append("	captured.setDestinationID($aEvent.getDestinationID());");
		builder.append("\n");
		builder.append("	captured.setSenderID($aEvent.getSenderID());");
		builder.append("\n");
		builder.append("	captured.setName($aEvent.getName() + \"-\" + $bEvent.getName());");
		builder.append("\n");
		builder.append("	insert(captured);");
		builder.append("\n");
		builder.append("	retract($aEvent);");
		builder.append("\n");
		builder.append("	retract($bEvent);");
		builder.append("\n");
		builder.append("	KieLauncher.printInfo(\"ComplexEvent first and second created - Rule fired:\" + drools.getRule().getName());");
		builder.append("\n");	
		builder.append("end");
		builder.append("\n");

		builder.append("rule \"checkForCorrectThird\"");
		builder.append("\n");
		builder.append("no-loop");
		builder.append("\n");
		builder.append("salience 995");
		builder.append("\n");
		builder.append("dialect \"java\"");
		builder.append("\n");
		builder.append("when");
		builder.append("\n");
		builder.append("	$aEvent: FirstAndSecondEvent();");
		builder.append("\n");
		builder.append("	$bEvent: ConcernICTGatewayEvent(");
		builder.append("\n");
		builder.append("		this.senderID == $aEvent.getSenderID(),");
		builder.append("\n");
		builder.append("		this.destinationID == $aEvent.getDestinationID(),");
		builder.append("\n");
		builder.append("		this.name != \"TOPOLOGY_ELEMENTS_RESPONSE\" || this.ICTMessageType != \"TOPOLOGY_ELEMENTS_RESPONSE\" ||  this.ICTMessageCategory != \"DATA\",");
		builder.append("\n");
		builder.append("		this after $aEvent);");
		builder.append("\n");
		builder.append("then");
		builder.append("\n");
		builder.append("		retract($aEvent);");
		builder.append("\n");
		builder.append("		retract($bEvent);");
		builder.append("\n");
		builder.append("		NotificationManager.NotifyViolation($aEvent.getSenderID(), \"Unattended event - Rule fired:\" + drools.getRule().getName(), drools.getRule().getName(), drools.getRule().getMetaData());");
		builder.append("\n");	
		builder.append("end");
		builder.append("\n");

		builder.append("rule \"catchSecondAndThird\"");
		builder.append("\n");
		builder.append("no-loop");
		builder.append("\n");
		builder.append("salience 994");
		builder.append("\n");
		builder.append("dialect \"java\"");
		builder.append("\n");
		builder.append("when");
		builder.append("\n");
		builder.append("	$aEvent: FirstAndSecondEvent();");
		builder.append("\n");
				
		builder.append("	$bEvent: ConcernICTGatewayEvent(");
		builder.append("\n");
		builder.append("		this.senderID == $aEvent.getSenderID(),");
		builder.append("\n");
		builder.append("		this.destinationID == $aEvent.getDestinationID(),");
		builder.append("\n");
		builder.append("		this.name == \"TOPOLOGY_ELEMENTS_RESPONSE\",");
		builder.append("\n");
		builder.append("		this.ICTMessageType == \"TOPOLOGY_ELEMENTS_RESPONSE\",");
		builder.append("\n");
		builder.append("		this.ICTMessageCategory == \"DATA\",");
		builder.append("\n");
		builder.append("		this after $aEvent);");
		builder.append("\n");	
		builder.append("then");
		builder.append("\n");
		builder.append("	FirstAndSecondAndThirdEvent captured = new FirstAndSecondAndThirdEvent();");
		builder.append("\n");
		builder.append("	captured.setTimestamp($aEvent.getTimestamp());");
		builder.append("\n");
		builder.append("	captured.setFirst($aEvent.getFirst());");
		builder.append("\n");
		builder.append("	captured.setSecond($aEvent.getSecond());");
		builder.append("\n");
		builder.append("	captured.setThird($bEvent);");
		builder.append("\n");
		builder.append("	captured.setDestinationID($aEvent.getDestinationID());");
		builder.append("\n");
		builder.append("	captured.setSenderID($aEvent.getSenderID());");
		builder.append("\n");
		builder.append("	captured.setName($aEvent.getName() + \"-\" + $bEvent.getName());");
		builder.append("\n");
		builder.append("	insert(captured);");
		builder.append("\n");
		builder.append("	retract($aEvent);");
		builder.append("\n");
		builder.append("	retract($bEvent);");
		builder.append("\n");
		builder.append("	KieLauncher.printInfo(\"ComplexEvent first, second and third created - Rule fired:\" + drools.getRule().getName());");
		builder.append("\n");	
		builder.append("end");
		builder.append("\n");

		builder.append("rule \"checkForCorrectFourth\"");
		builder.append("\n");
		builder.append("no-loop");
		builder.append("\n");
		builder.append("salience 993");
		builder.append("\n");
		builder.append("dialect \"java\"");
		builder.append("\n");
		builder.append("when");
		builder.append("\n");
		builder.append("	$aEvent: FirstAndSecondAndThirdEvent();");
		builder.append("\n");
		builder.append("	$bEvent: ConcernICTGatewayEvent(");
		builder.append("\n");
		builder.append("		this.senderID == $aEvent.getSenderID(),");
		builder.append("\n");
		builder.append("		this.destinationID == $aEvent.getDestinationID(),");
		builder.append("\n");
		builder.append("		this.name != \"TOPOLOGY_ELEMENT_DETAILS_RESPONSE\" || this.ICTMessageType != \"TOPOLOGY_ELEMENT_DETAILS_RESPONSE\" ||  this.ICTMessageCategory != \"DATA\",");
		builder.append("\n");
		builder.append("		this after $aEvent);");
		builder.append("\n");
		builder.append("then");
		builder.append("\n");
		builder.append("		retract($aEvent);");
		builder.append("\n");
		builder.append("		retract($bEvent);");
		builder.append("\n");
		builder.append("		NotificationManager.NotifyViolation($aEvent.getSenderID(), \"Unattended event - Rule fired:\" + drools.getRule().getName(), drools.getRule().getName(), drools.getRule().getMetaData());");
		builder.append("\n");		
		builder.append("end");
		builder.append("\n");


		builder.append("rule \"catchThirdAndFourth\"");
		builder.append("\n");
		builder.append("no-loop");
		builder.append("\n");
		builder.append("salience 991");
		builder.append("\n");
		builder.append("dialect \"java\"");
		builder.append("\n");
		builder.append("when");
		builder.append("\n");
		builder.append("	$aEvent: FirstAndSecondAndThirdEvent();");
		builder.append("\n");
				
		builder.append("	$bEvent: ConcernICTGatewayEvent(");
		builder.append("\n");
		builder.append("		this.senderID == $aEvent.getSenderID(),");
		builder.append("\n");
		builder.append("		this.destinationID == $aEvent.getDestinationID(),");
		builder.append("\n");
		builder.append("		this.name == \"TOPOLOGY_ELEMENT_DETAILS_RESPONSE\",");
		builder.append("\n");
		builder.append("		this.ICTMessageType == \"TOPOLOGY_ELEMENT_DETAILS_RESPONSE\",");
		builder.append("\n");
		builder.append("		this.ICTMessageCategory == \"DATA\",");
		builder.append("\n");
		builder.append("		this after $aEvent);");
		builder.append("\n");
			
		builder.append("then");
		builder.append("\n");
		builder.append("	retract($aEvent);");
		builder.append("\n");
		builder.append("	retract($bEvent);");
		builder.append("\n");
		builder.append("	KieLauncher.printInfo(\"Cycle completed - Rule fired:\" + drools.getRule().getName());");
		builder.append("\n");	
		builder.append("end");
		builder.append("\n");

		
		
		
		return builder.toString();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
