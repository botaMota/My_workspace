package ma.mota.jms.receivers;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSReceiver {

	public static void main(String[] args) {
		
		 try {
			Connection cx = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
			cx.start();
			Session session = cx.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("EM_TRADE.Q");
			MessageConsumer receiver = session.createConsumer(queue);
			TextMessage message = (TextMessage)receiver.receive();
			System.out.println(message.getText());
			cx.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error");
		}
		
	}

}
