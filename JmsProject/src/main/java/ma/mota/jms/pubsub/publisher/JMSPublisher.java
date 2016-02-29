package ma.mota.jms.pubsub.publisher;

import java.text.DecimalFormat;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSPublisher {

	public static void main(String[] args) {
		
		ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616");
		try {
			Connection cx = cf.createConnection();
			cx.start();
			Session session = cx.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic("EM_Trade.T");
			MessageProducer publisher = session.createProducer(topic);
			
			DecimalFormat df = new DecimalFormat("##.00");
			String price = df.format(95.0 +Math.random());
			TextMessage message = session.createTextMessage("Apple "+price);
			
			publisher.send(message);
			System.out.println("Message published : price = "+price);
			cx.close();
			
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error");
		}
	}

}
