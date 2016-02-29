package ma.mota.jms.producers;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueRequestor;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSProducerVirtualQueue {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616");
		try {
			QueueConnection queueConnection = cf.createQueueConnection();
			queueConnection.start();
			QueueSession queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = queueSession.createQueue("EM_TRADE.Q");
			TextMessage message = queueSession.createTextMessage("Salam Mota");
			QueueRequestor requestor = new QueueRequestor(queueSession, queue);
			TextMessage msgRes = (TextMessage)requestor.request(message);
			System.out.println("Message respense : "+msgRes.getText());
			queueConnection.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error");
		}

	}

}
