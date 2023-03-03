# OntologyManger base image
FROM amazoncorretto:19.0.1-al2
LABEL MAINTAINER="said.daoudagh@isti.cnr.it"
# copy files required for Ontology Manager to run
COPY /* /
# the port numbers the container should expose
#EXPOSE 62626 
EXPOSE 8282
# Entrypoint to run the Ontology Manager
ENTRYPOINT ["java","-cp", "OntologyManager-jar-with-dependencies.jar", "it.cnr.isti.sedc.bieco.ontologyManager.rest.Main"]