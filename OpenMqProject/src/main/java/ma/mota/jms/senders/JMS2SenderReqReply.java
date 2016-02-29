package ma.mota.jms.senders;

import java.util.UUID;

import javax.jms.JMSContext;
import javax.jms.Queue;

import com.sun.messaging.ConnectionFactory;

public class JMS2SenderReqReply {
	public static void main(String[] args) {
		
		ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();
		try (JMSContext jmsContext = cf.createContext();) {
			Queue queueReq = jmsContext.createQueue("EM_JMS2_TRADE_REQ.Q");
			Queue queueRes = jmsContext.createQueue("EM_JMS2_TRADE_RES.Q");
			
			String uuid = UUID.randomUUID().toString();
			
			jmsContext
			.createProducer()
			.setJMSReplyTo(queueRes)
			.setProperty("Uuid", uuid)
			.send(queueReq, "BUY AAPL 5000 SHARES");
			System.out.println("Message sent");
			
			String filter = "MessageLink = '" + uuid + "'";
			String confirmation = jmsContext.createConsumer(queueRes , filter).receiveBody(String.class);
			System.out.println("Confirmation : "+confirmation);
			
			System.out.println("End of messaging");
		}		
	}

}


