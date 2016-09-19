package com.demo.akka;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.demo.kafka.SimpleConsumer;

import akka.actor.ActorSelection;
import akka.actor.UntypedActor;



@Component
@Scope("prototype")
public class ConsumerCCActor extends UntypedActor {
	
	@Value("${topic}")
	private String topic;

	private static Logger logger = LoggerFactory.getLogger(ConsumerCCActor.class);
	
	@Override
	public void onReceive(Object msg) throws Exception {	
		
		if(msg == "start"){
            logger.info("GREETED");
            logger.info("Consumer starting");
            SimpleConsumer sc = new SimpleConsumer("localhost:2181", "coregroup");
            ConsumerConnector consumerConnector = sc.getConsumerConnector();
            logger.info("Running.....");
            Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
            System.out.println(new Integer(1));
            System.out.println(new Integer(1));
            topicCountMap.put(topic, new Integer(1));
            Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumerConnector
                    .createMessageStreams(topicCountMap);
            KafkaStream<byte[], byte[]> stream = consumerMap.get(topic).get(0);//remove args if error
            ConsumerIterator<byte[], byte[]> it = stream.iterator();
            while (it.hasNext()) {

                String newString = new String(it.next().message());
                logger.info("newString :: {}",newString);
                

                try {
					processMessage(newString);
				} catch (Exception e) {
					e.printStackTrace();
				}
                
                
            }
			
		}
	}
	
	
	public void processMessage(String newString){	
        	System.out.println(newString);      	
    }
}

	
