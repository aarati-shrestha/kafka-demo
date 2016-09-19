package com.demo.kafka;

import java.util.Properties;
import java.util.Random;

import javax.annotation.PostConstruct;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class KafkaProducer {
	
	private static Producer producer;
	public final Properties props = new Properties();

	private Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
	 @Value("${kafka}")
	 private static String kafka;
	 private  String brokerListPort = ":9092";
	 //	 private String prop;

	    @Autowired
	    public void MyBean(@Value("${kafka}") String k) {
	        this.kafka = k;
	    }
	    
	    

	public KafkaProducer() {
			super();
		}



	@PostConstruct
	public void init() {
		props.put("metadata.broker.list", kafka+brokerListPort);
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		props.put("partitioner.class", "jiko.kafka.SimplePartitioner");
		props.put("request.required.acks", "1");

		ProducerConfig config = new ProducerConfig(props);
		setProducer(new Producer(config));
		logger.info("initialized!!!!");
	}

	public Producer getProducer() {
		return producer;
	}

	public void setProducer(Producer producer) {
		KafkaProducer.producer = producer;
	}
	
	public void produce(String message, String topic) {

		Integer i = new Random().nextInt();
		
			KeyedMessage<String, String> data1 = new KeyedMessage<String, String>(
					topic, i.toString(), message);
			getProducer().send(data1);
	}

}

