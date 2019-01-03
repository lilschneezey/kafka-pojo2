package io.schneezey.producer;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.schneezey.pojo.TestPojo;
import io.schneezey.pojo.TestSubPojo;
import io.schneezey.pojo.TEST_ENUM;

/**
 * This is meant to show how to use an Avro generated class from a schema.  
 * 
 * I started with a manual preparation of creating a schema using Avro reflection. Note, this is not perfect.  
 * It appears there are a few bugs, as it did not recognize @Nullable or @AvroDefault on the same line as my DateAsLong Encoding.
 * I ended up editing the schema before generating the class to get the proper test.
 * 
 * I was never able to get a default value for an enum. needs more research. 
 *
 */
public class Producer 
{
	public static Logger logger = LoggerFactory.getLogger(Producer.class);
    public static void main( String[] args ) {
    	
    	produceAvroGeneratedPojo();
    }

    private static TestPojo createTestPojo() {
    	TestPojo pojo = TestPojo.newBuilder()
    	
    	.setId(Random.randomInt(999999999))

	
    	.setTestPBoolean(Random.randomBoolean())
    	.setTestPDouble(Random.randomDouble())
    	.setTestPFloat(Random.randomFloat())
    	.setTestPInt(Random.randomInt())
    	.setTestPLong(Random.randomLong())
    	.setTestPBytes((Random.randomByteBuffer(Random.randomInt(128))) )

    	.setTestN1Boolean(Random.randomBoolean())
    	.setTestN1Double(Random.randomDouble())
    	.setTestN1Float(Random.randomFloat())
    	.setTestN1Int(Random.randomInt())
    	.setTestN1Long(Random.randomLong())
    	.setTestN1Bytes((Random.randomByteBuffer(Random.randomInt(128))) )
    	    	
    	.setTestDate(0)
    	.setSubPojos(new ArrayList<TestSubPojo>() )
    	.setTestenum(TEST_ENUM.TYPEA)
    	
    	.build();

		int value = Random.randomInt(3);
		switch (value) {
		case 1:
			pojo.setTestenum(TEST_ENUM.TYPEA);
			break;
		case 2:
			pojo.setTestenum(TEST_ENUM.TYPEB);
			break;
		case 3:
		default:
			pojo.setTestenum(TEST_ENUM.TYPEC);
			break;
		}

    	Calendar.getInstance().set(1910 + Random.randomInt(85), Random.randomInt(12), Random.randomInt(28));
    	pojo.setTestDate(Calendar.getInstance().getTime().getTime());
  
    	Calendar.getInstance().set(1910 + Random.randomInt(85), Random.randomInt(12), Random.randomInt(28));
    	pojo.setTestN1Date(Calendar.getInstance().getTime().getTime());
    	
    	int limit = Random.randomInt(4);
    	for (int i = 0; i < limit; i++) {
	    	TestSubPojo subPojo = TestSubPojo.newBuilder()
		    	.setSubPBoolean(Random.randomBoolean())
		    	.setSubPDouble(Random.randomDouble())
		    	.setSubPFloat(Random.randomFloat())
		    	.setSubPInt(Random.randomInt())
		    	.setSubPLong(Random.randomLong())
		    	.setSubPBytes((Random.randomByteBuffer(Random.randomInt(128))) )
	    	    .build();
	    	pojo.getSubPojos().add(subPojo);
    	}
    	
    	return pojo;
    }
    
	private static void produceAvroGeneratedPojo() {
		KafkaProducer<String,TestPojo> producer = null;
    	try {
	        Properties config = new Properties();
	        config.put("client.id", InetAddress.getLocalHost().getHostName());
	        config.put("bootstrap.servers", "localhost:9092");
	        config.put("acks", "all");
	        
	        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
	                org.apache.kafka.common.serialization.StringSerializer.class);
	        
	        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
	        config.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");

	        producer = new KafkaProducer<String, TestPojo> (config);
	        
	        
	        for (int i = 0; i < 3; i++) {
	        	TestPojo pojo = createTestPojo();
		        ProducerRecord<String,TestPojo> avroRecord = new ProducerRecord<String,TestPojo> ("test.pojo.avrogen.confluent.avro.v3", pojo.getId().toString(), pojo);
	        	producer.send( avroRecord );
		        logger.debug("Avro Message produced" + pojo.toString() );
			}
	        producer.close();
    	} catch (Exception ee) {
    		ee.printStackTrace();
    		logger.error("Exception Caught \n" + ee.getLocalizedMessage());
    	}
    	finally {
    		if (producer != null) producer.close();
    	}
	}

}
