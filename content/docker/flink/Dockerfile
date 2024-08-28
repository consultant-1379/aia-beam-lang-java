# **********************************************************************
# Ericsson Radio Systems AB                                     
# **********************************************************************
#
# (c) Ericsson 2018 - All rights reserved.
#
# The copyright to the computer program(s) herein is the property of
# Ericsson Radio Systems AB, Sweden. The programs may be used and/or
# copied only with the written permission from Ericsson Radio Systems AB
# or in accordance with the terms and conditions stipulated in the
# agreement/contract under which the program(s) have been supplied.
#
# **********************************************************************
#Pull flink Base image from Docker Repository
FROM armdocker.rnd.ericsson.se/aia_releases/flink-base/flink-hadoop27-scala211:1.4.0

ENV FLOW_HOME="/pbaName"
ENV FLOW_CONFIGURATION="/conf"
ENV FLOW_JAR="pbaName-bundled.jar"
ENV FLOW_MAIN_CLASS="com.ericsson.aia.bps.beam.flow.pbaNameInCamelCase"
ENV MASTER_URL="localhost:6123"

#temporary solution for the protobuf problem
ADD https://arm101-eiffel004.lmera.ericsson.se:8443/nexus/service/local/repositories/central/content/com/google/protobuf/protobuf-java/3.2.0/protobuf-java-3.2.0.jar /opt/flink/lib
CMD chown flink:flink /opt/flink/lib/protobuf-java-3.2.0.jar && chmod 664 /opt/flink/lib/protobuf-java-3.2.0.jar

#create the flow folder for the jars, dependencies, and configuration
CMD mkdir -p ${FLOW_HOME}
CMD mkdir -p ${FLOW_CONFIGURATION}

#Copy uber jar
ADD ${FLOW_JAR} ${FLOW_HOME}/

#Add the configuration file
ADD config.properties ${FLOW_CONFIGURATION}/

#Add init script
ADD init_script ${FLOW_HOME}/

#change init_script permssion
RUN chmod +x ${FLOW_HOME}/init_script

#Run this script when docker images gets initialized
ENTRYPOINT ["sh", "-c", "${FLOW_HOME}/init_script"]
