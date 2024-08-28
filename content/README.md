# Introduction

pbaName is Apache beam java language based flow generated from the AIA Portal(http://analytics.ericsson.se/#/) using beam-lang-java-template template.

# Pre- Prerequites

Apache beam is required for this flow. You can use the dependencies of version 2.3/2.4 from nexus. Please
modify the beam.version property in pom.xml if you want to use a different version.

# Prerequites

Some of the services are required depending on the chosen Input/Output and Runner. For example,

Kafka service is required if the I/O is kafka.
Spark cluster is required if the runner is SparkRunner.

You can use the AIA sandbox or install these required services in your machine.

# Development

Please follow the http://analytics.ericsson.se/#/Documentation/Environment_Setup to set up your environment. The following tools are required,
```
   Java
   Maven
   Docker
```

# Terminology

We are using the following terminologies when building or running pbaName flow. And the table below contains the mapping of these terminologies.

* maven runner profile: This is the maven profile as defined in the pom.xml.
* Beam Runner: Apache beam Runner(https://beam.apache.org/documentation/runners/capability-matrix/).
* Uber Jar: One single JAR file containing the packages and required dependencies.
* docker image: dockerization is provided as one way of packaging, and maven plugin is used to generated docker image.

|maven runner profile|Beam Runner|Uber JAR|docker image|
|:------------- |:-------------|:-------------|:-------------|
|spark-runner |SparkRunner|target/pbaName-bundled.jar|armdocker.rnd.ericsson.se/aia/bps/beam/flow/pbaName-spark|
|flink-runner|FlinkRunner|target/pbaName-bundled.jar|armdocker.rnd.ericsson.se/aia/bps/beam/flow/pbaName-flink|
|direct-runner|DirectRuner|target/pbaName-bundled.jar|armdocker.rnd.ericsson.se/aia/bps/beam/flow/pbaName-direct|
|apex-runner|ApexRunner|target/pbaName-bundled.jar|armdocker.rnd.ericsson.se/aia/bps/beam/flow/pbaName-apex|
|gearpump-runner|GearpumpRunner|target/pbaName-bundled.jar|armdocker.rnd.ericsson.se/aia/bps/beam/flow/pbaName-gearpump|
|dataflow-runner|CloudFlowRunner|target/pbaName-bundled.jar|armdocker.rnd.ericsson.se/aia/bps/beam/flow/pbaName-dataflow|

`NOTE`

- NOT all the docker images are tested, at the moment, direct runner, spark runner and flink runne are under testing. Please contact AIA team if you need support for other runners.

# Build

Maven is the chosen package tool for this flow. Please run the following command to compile and package in uber jar together with docker image.
```
 mvn compile package -P<maven runner profile>

 <maven runner profile>: as defined in Terminology section.
```

e.g,

```
 mvn compile package -Pspark-runner
```

# Run

pbaName can be executed in maven exec plugin, embedded mode, cluster mode or docker mode with the generated uber jar file or docker image.

## Maven exec plugin

```
mvn compile exec:java -Dexec.mainClass=com.ericsson.aia.bps.beam.flow.pbaNameInCamelCase
    -Dexec.args="--runner=<Beam Runner> [<Runner options>] [<flow options>]" -P<runner profile>

<Beam Runner>: as defined in Terminology section.
<Runner Options>: options required for the chosen runner. Please check the PipelineOptions in beam doc: https://beam.apache.org/documentation/runners/capability-matrix/.
<flow options>: As programmed in the pbaNameInCamelCaseOptions interface in pbaNameInCamelCase.java. The option can be passed in the format of --<option name>=<option value>
<maven runner profile>: as defined in Terminology section.
```

For example, you can run the following command to execute in Flink cluster,
```
mvn compile exec:java -Dexec.mainClass=com.ericsson.aia.bps.beam.flow.pbaNameInCamelCase
   -Dexec.args="--runner=FlinkRunner --dataSourceKafkaServer=localhost:9092  --dataSourceKafkaTopic=input_topic --dataSinkKafkaServer=localhost:9092 --dataSinkKafkaTopic=output_topic_beam --jdbcUrl=jdbc:postgresql://localhost:5432/aia --jdbcUser=aia --jdbcPassword=aia
           --flinkMaster=172.17.0.1:28081 --filesToStage=target/pbaName-bundled.jar" -Pflink-runner
```

## embedded mode

Most of the Runner provides embedded mode, i.e, run the flow in single JVM.
```
java -jar target/pbaName-bundled.jar com.ericsson.aia.bps.beam.flow.pbaNameInCamelCase --runner=<Beam Runner>
       [<Runner options>] [<flow options>]
<Beam Runner>: as defined in Terminology section.
<Runner Options>: options required for the chosen runner. Please check the PipelineOptions in beam doc: https://beam.apache.org/documentation/runners/capability-matrix/.
<flow options>: As programmed in the pbaNameInCamelCaseOptions interface in pbaNameInCamelCase.java. The option can be passed in the format of --<option name>=<option value>
```

For example, you can run the following command to execute in embedded Flink mode,

```
java -jar target/pbaName-bundled.jar com.ericsson.aia.bps.beam.flow.pbaNameInCamelCase --runner=FlinkRunner
     --dataSourceKafkaServer=localhost:9092  --dataSourceKafkaTopic=input_topic --dataSinkKafkaServer=localhost:9092 --dataSinkKafkaTopic=output_topic_beam --jdbcUrl=jdbc:postgresql://localhost:5432/aia --jdbcUser=aia --jdbcPassword=aia
```

## Cluster mode

You can submit your application to Runner cluster for resource friendly execution. The command is different for each runner. Please check the application deployment documentation for details.
The commands listed below are for reference purpose only.
Flink and Spark cluster are included here for reference, the other Runners will be added soon.

### Flink cluster

```
<FLINK_HOME>/bin/flink run -d `pwd`/target/pbaName-bundled.jar --runner=FlinkRunner
     [<Runner options>] [<flow options>]

<FLINK_HOME>: flink home folder.
<Runner Options>: options required for the chosen runner. Please check the PipelineOptions in beam doc: https://beam.apache.org/documentation/runners/capability-matrix/.
<flow options>: As programmed in the pbaNameInCamelCaseOptions interface in pbaNameInCamelCase.java. The option can be passed in the format of --<option name>=<option value>
```

for example,

```
/opt/flink/bin/flink run -d `pwd`/target/pbaName-bundled.jar --runner=FlinkRunner
    --dataSourceKafkaServer=localhost:9092  --dataSourceKafkaTopic=input_topic --dataSinkKafkaServer=localhost:9092 --dataSinkKafkaTopic=output_topic_beam --jdbcUrl=jdbc:postgresql://localhost:5432/aia --jdbcUser=aia --jdbcPassword=aia
```

### Spark cluster

```
<SPARK_HOME>/bin/spark-submit --class com.ericsson.aia.bps.beam.flow.pbaNameInCamelCase
    [<Spark deployment options>] `pwd`/target/pbaName-bundled.jar --runner=SparkRunner [<flow options>]

<SPARK_HOME>: spark home folder.
<Spark deployment options>: options required for the spark application deployment.
<flow options>: As programmed in the pbaNameInCamelCaseOptions interface in pbaNameInCamelCase.java. The option can be passed in the format of --<option name>=<option value>
```

for example,
```
/opt/spark-2.2.1/bin/spark-submit --class com.ericsson.aia.bps.beam.flow.pbaNameInCamelCase
   --master=spark://127.0.0.1:7077 target/access-log-bundled-1.0-SNAPSHOT.jar --runner=SparkRunner
   --dataSourceKafkaServer=localhost:9092  --dataSourceKafkaTopic=input_topic --dataSinkKafkaServer=localhost:9092 --dataSinkKafkaTopic=output_topic_beam --jdbcUrl=jdbc:postgresql://localhost:5432/aia --jdbcUser=aia --jdbcPassword=aia
```

## Docker mode

With the built docker image, pbaName can run in docker mode, and it can be deployed into docker orchestration platform.

```
docker run <docker image>

<docker image>: as defined in the Terminology.
```

e.g,

```
docker run -v `pwd`:/flow armdocker.rnd.ericsson.se/aia/bps/beam/java/pbaName-spark
```

## Troubleshooting
1. When running in docker mode, there is no output.

In some case this is caused by the service unavailable to the flow inside docker, you can try the following solutions,

* Run the docker with HOST network, e.g,
 ```
 docker run -d --network=host -v `pwd`:/flow armdocker.rnd.ericsson.se/aia/bps/beam/flow/pbaName-spark
 ```
* Change the input/output configuration to public address of the services.
* Use the service link or service discovery from container services, like docker-compose, kubernetes.