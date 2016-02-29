package ma.mota.jms.producers;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSSenderReqReply {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616");
		try {
			Connection cx = cf.createConnection();
			cx.start();
			Session session = cx.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queueReq = session.createQueue("EM_TRADE_REQ.Q");
			Queue queueRes = session.createQueue("EM_TRADE_RES.Q");
			
			//Message creation
			TextMessage textMessageReq = session.createTextMessage("Salam Motawakil");
			textMessageReq.setJMSReplyTo(queueRes);
			//Producer creation
			MessageProducer producer = session.createProducer(queueReq);
			producer.send(textMessageReq);
			System.out.println("Message sent");
			
			
			//Receiver creation
			String filter = "JMSCorrelationID = '" + textMessageReq.getJMSMessageID() + "'";
			MessageConsumer receiver = session.createConsumer(queueRes, filter);
			TextMessage textMessageRes = (TextMessage) receiver.receive();
			
			System.out.println("Confirmation = "+textMessageRes.getText());
			cx.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error");
		}

	}

}
