package ma.mota.jms.receivers;

import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Queue;

import com.sun.messaging.ConnectionFactory;

public class JMS2ReceiverReqReply {

	public static void main(String[] args) throws Exception {

		ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();
		try (JMSContext jmsContext = cf.createContext();) {
			Queue queue = jmsContext.createQueue("EM_JMS2_TRADE_REQ.Q");
			System.out.println("Waiting for a message ...");
			Message msg = jmsContext.createConsumer(queue).receive();
			String body = msg.getBody(String.class);
			System.out.println("Received message :"+body);
			Thread.sleep(4000);
			String confirmation = "EQ-89565";
			System.out.println("Trade confirmation : "+confirmation);
			System.out.println("MessageLink : "+msg.getStringProperty("Uuid"));
			
			jmsContext
			.createProducer()
			.setProperty("MessageLink", msg.getStringProperty("Uuid"))
			.send(msg.getJMSReplyTo(), confirmation);
			
			System.out.println("End receiving messages");
		}
	}
}
		