package ma.mota.jms.browsers;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSBrowser {

	public static void main(String[] args) {
		
		try {
			Connection cx = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
			cx.start();
			Session session = cx.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("EM_TRADE.Q");
			
			QueueBrowser browser = session.createBrowser(queue);
			Enumeration<?> enumeration = browser.getEnumeration();
			int msgCount =0 ;
			while (enumeration.hasMoreElements()) {
				enumeration.nextElement();
				msgCount++ ;
			}
			
			System.out.println("There are "+msgCount+ " messages in queue");
			browser.close();
			cx.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error");
		}

	}

}
