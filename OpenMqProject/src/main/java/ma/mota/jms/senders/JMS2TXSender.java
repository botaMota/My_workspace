package ma.mota.jms.senders;

import javax.jms.JMSContext;
import javax.jms.Queue;

import com.sun.messaging.ConnectionFactory;

public class JMS2TXSender {
	public static void main(String[] args) {
		
		ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();
		try (JMSContext jmsContext = cf.createContext();) {
			Queue queue = jmsContext.createQueue("EM_JMS2_TRADE.Q");
			
			jmsContext
			.createProducer()
			.send(queue, "BUY AAPL 5000 SHARES");

			System.out.println("Message sent");
		}		
	}

}
