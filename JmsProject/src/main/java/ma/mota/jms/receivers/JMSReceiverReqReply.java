package ma.mota.jms.receivers;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSReceiverReqReply {

	public static void main(String[] args) throws Exception {
		
		 try {
			Connection cx = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
			cx.start();
			Session session = cx.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("EM_TRADE_REQ.Q");			
			System.out.println("Wairing for a message ...");
			
			//Receiver Creation
			MessageConsumer receiver = session.createConsumer(queue);
			TextMessage message = (TextMessage)receiver.receive();
			System.out.println("Received Message : "+message.getText());
			
			//Simulated Processing
			Thread.sleep(4000);
			String confirmation = "EQ-21546";
			System.out.println("Trade Confirmation : "+confirmation);
			
			//Sender Creation
			TextMessage senderMsg = session.createTextMessage(confirmation);
			senderMsg.setJMSCorrelationID(message.getJMSMessageID());
			MessageProducer sender = session.createProducer((Queue)message.getJMSReplyTo());
			sender.send(senderMsg);
			cx.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error");
		}
		
	}

}
