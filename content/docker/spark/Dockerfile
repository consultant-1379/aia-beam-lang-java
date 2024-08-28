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
#Pull Data Processor Base image from Docker Repository
FROM armdocker.rnd.ericsson.se/aia_releases/spark:v2.1.0

ENV FLOW_HOME="/pbaName"
ENV FLOW_CONFIGURATION="/conf"
ENV FLOW_JAR="pbaName-bundled.jar"
ENV FLOW_MAIN_CLASS="com.ericsson.aia.bps.beam.flow.pbaNameInCamelCase"
ENV DEPLOY_MODE="client"
ENV MASTER_URL="local[*]"

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
