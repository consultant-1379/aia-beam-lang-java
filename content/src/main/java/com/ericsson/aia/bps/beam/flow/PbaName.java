package com.ericsson.aia.bps.beam.flow;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.options.Validation;

/**
 * This is skeleton of Main class. Please check the reference application in AIA application catelog for reference.
 *
 * This class need to be refactored to meet the application/flow requirement.
 */
public class pbaNameInCamelCase{

    private pbaNameInCamelCase(){
    }

    /**
     * create the Options for this flow.
     */
    public interface pbaNameInCamelCaseOptions extends PipelineOptions{

    }

    public static void main(final String[] args){

        //Retrieve the options from parameters
        pbaNameInCamelCaseOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().as(pbaNameInCamelCaseOptions.class);

        //create pipeline with options
        Pipeline pipeline = Pipeline.create(options);

        /**
         * Step 1: Retrieve the input. e.g,
         *
         * PCollection<KV<String, String>> input = pipeline
         *             //get the kafka input
         *             .apply("Reading from kafka",
         *                 KafkaIO.<String, String>read().withBootstrapServers(options.getDataSourceKafkaServer()).withTopic(options.getDataSourceKafkaTopic())
         *                     .withKeyDeserializer(StringDeserializer.class).withValueDeserializer(StringDeserializer.class))
         *
         */

        /**
         * Step 2: Add transform step.
         *
         */

        /**
         * Step 3: Write out. e.g,
         *
         * input.apply("Writing out to kafka",
         *             KafkaIO.<String, String>write().withBootstrapServers(options.getDataSinkKafkaServer()).withTopic(options.getDataSinkKafkaTopic())
         *                 .withKeySerializer(StringSerializer.class)
         *                 .withValueSerializer(AccessLogRecordSerializer.class)
         */

        pipeline.run().waitUntilFinish();

    }

}