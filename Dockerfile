FROM amazoncorretto:19.0.1-al2
LABEL MAINTAINER="said.daoudagh@isti.cnr.it"
COPY /* /
EXPOSE 62626 8282
ENTRYPOINT ["java","-cp", "OntologyManager-jar-with-dependencies.jar", "it.cnr.isti.sedc.bieco.ontologyManager.rest.Main"]