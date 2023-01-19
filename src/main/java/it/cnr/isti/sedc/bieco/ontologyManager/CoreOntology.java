package it.cnr.isti.sedc.bieco.ontologyManager;

import it.cnr.isti.sedc.bieco.ontologyManager.utils.Sub;

public class CoreOntology extends Thread {

	private static CoreOntology INSTANCE;

	public static Thread getInstance() throws InterruptedException {
		if (INSTANCE == null) {
			INSTANCE = new CoreOntology();
			INSTANCE.run();
		}
		return INSTANCE;
	}

	public static boolean isRunning() {
		if (INSTANCE == null)
			return false;
		return true;
	}

	public static void killInstance() {
		INSTANCE.interrupt();
		INSTANCE = null;
	}

	public static void DemoStart() {

		System.out.println("------------------------------------------------------");
		System.out.println("-------Starting Ontology Manager session-------");
		System.out.println("------------------------------------------------------");
	}

	public static void main(String[] args) {

	}

	public static String getLoggerData() {
		
		System.out.println(System.getProperty("user.dir"));
		
		return Sub.readFile(System.getProperty("user.dir") + "/logs/ontologyManager-debug.log");
	}
}
