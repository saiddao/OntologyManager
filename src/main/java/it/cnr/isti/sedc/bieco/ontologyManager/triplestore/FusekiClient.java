package it.cnr.isti.sedc.bieco.ontologyManager.triplestore;

import org.apache.jena.fuseki.main.FusekiServer;
import org.apache.jena.fuseki.server.DataService;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.sparql.core.DatasetGraph;
import org.apache.jena.sparql.core.DatasetGraphWrapper;
import org.apache.jena.system.Txn;
import org.apache.jena.tdb.TDB;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.update.*;



import static java.lang.String.format ;
import static org.apache.jena.riot.WebContent.contentTypeSPARQLQuery ;
import static org.apache.jena.riot.WebContent.contentTypeSPARQLUpdate ;

import java.io.IOException ;
import java.util.List ;

import javax.servlet.ServletException ;
import javax.servlet.http.HttpServletRequest ;
import javax.servlet.http.HttpServletResponse ;

import org.apache.jena.atlas.web.MediaType ;
import org.apache.jena.fuseki.DEF ;
import org.apache.jena.fuseki.Fuseki ;
import org.apache.jena.fuseki.FusekiException ;
import org.apache.jena.fuseki.server.* ;
import org.apache.jena.riot.web.HttpNames ;

public class FusekiClient {
    public static void main(String[] args) {
        String endpoint = "http://localhost:3030/ds/query";
        String updateEndpoint = "http://localhost:3030/ds/update";
        
        System.out.println(ARQ.VERSION);
        
        //SPARQL SELECT example
        String queryString = "SELECT ?s ?p ?o WHERE {?s ?p ?o} LIMIT 10";
        Query query = QueryFactory.create(queryString);
        try (QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint, query)) {
            ResultSet results = qexec.execSelect();
            ResultSetFormatter.out(System.out, results, query);
        }

        //SPARQL UPDATE example
        String updateString = "INSERT DATA { <http://example/s1> <http://example/p1> <http://example/o1> }";
        UpdateRequest update = UpdateFactory.create(updateString);
        try (UpdateExecutionFactory uef = UpdateExecutionFactory.newRemoteForm(update, updateEndpoint)) {
            UpdateProcessor proc = uef.create();
            proc.execute();
        }
    }
    
    
    private void createDataBase() {
		// TODO Auto-generated method stub
    	
    	
    	// Make a TDB-backed dataset
    	  String directory = "MyDatabases/Dataset1" ;
    	  Dataset dataset = TDBFactory.createDataset(directory) ;
    	  
    	  
    	  DatasetGraph dsg = TDBFactory.createDatasetGraph();
    	  DataService dataService = new DataService(dsg) ;
    	  dataService.addEndpoint(OperationName.GSP_RW, "");
    	  dataService.addEndpoint(OperationName.Query, "");
    	  dataService.addEndpoint(OperationName.Update, "");
    	  
    	  
    	  FusekiServer server = FusekiServer.create()
    			  .add("/rdf", dataset)
    			  .build() ;
    			server.start() ;
    			
    			
    			
    			// Add some data while live.
    			// Write transaction.
    			Txn.execWrite(dsg, ()->RDFDataMgr.read(dsg, "D.trig")) ;
    			
    			
    			
    			
    			
    			
    			
    			
    			
    			
    			
    			server.stop() ;
    			
    	  // for synchronization only in UPDATE MODE
    	  TDB.sync(dataset ) ;
    	  dataset.begin(ReadWrite.READ) ;
    	  // Get model inside the transaction
    	  Model model = dataset.getDefaultModel() ;
    	  dataset.end() ;
    	  dataset.begin(ReadWrite.WRITE) ;
    	  model = dataset.getDefaultModel() ;
    	  dataset.end() ;
    	  
    	
	}
}
