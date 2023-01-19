FROM amazoncorretto:19.0.1-al2
LABEL MAINTAINER="said.daoudagh@isti.cnr.it"
COPY /* /
EXPOSE 62626 8282
ENTRYPOINT ["java","-cp", "OntologyManagerRest-1.0.jar", "it.cnr.isti.labsedc.concern.rest.Main"]