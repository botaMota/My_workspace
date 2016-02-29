package ma.mota.jms.receivers;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSReceiverVirtualQueue {

	public static void main(String[] args) throws InterruptedException {
		
		 try {
			Connection cx = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
			cx.start();
			Session session = cx.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("EM_TRADE.Q");
			System.out.println("Waiting for a message ...");
			MessageConsumer receiver = session.createConsumer(queue);
			TextMessage message = (TextMessage)receiver.receive();
			System.out.println(message.getText());
			Thread.sleep(4000);
			String confirmation = "EQ-561247";
			TextMessage messageRes = session.createTextMessage(confirmation);
			MessageProducer producer = session.createProducer((Queue)message.getJMSReplyTo());
			producer.send(messageRes);
			cx.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error");
		}
		
	}

}
