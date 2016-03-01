package ma.mota.jms.producers;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSVmSender {
	
	private Session session ;
	
	private MessageProducer sender ; 

	public JMSVmSender() {
		Connection cx;
		try {
			cx = new ActiveMQConnectionFactory("vm://embedded1").createConnection();
			cx.start();
			session = cx.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("EM_EMBED_INTERNEL.Q");
			sender = session.createProducer(queue);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void sendMessage(String trade) {
		try {
			TextMessage message = session.createTextMessage(trade);
			sender.send(message);
			System.out.println("Trade sent");
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
