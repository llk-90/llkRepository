package com.fh.dao;

import javax.annotation.Resource;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Repository;

@Repository("KafkaMessageSend")
public class KafkaMessageSend {
	private String topic="change";
	
	@Resource(name="producer")
	private KafkaProducer<String,String> producer;
	
	public void sendMessage(String key,String value) {
		producer.send(new ProducerRecord<>(this.topic,0, key, value));
		producer.flush();
	}
}
