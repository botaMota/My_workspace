package ma.mota.jms.browser;

import java.util.Enumeration;

import javax.jms.DeliveryMode;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;

import com.sun.messaging.ConnectionFactory;

public class JMS2Browser {
	public static void main(String[] args) throws JMSException {
		
		ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();
		try (JMSContext jmsContext = cf.createContext();) {
			Queue queue = jmsContext.createQueue("EM_JMS2_TRADE.Q");
			QueueBrowser browser = jmsContext.createBrowser(queue);
			Enumeration<?> enumeration = browser.getEnumeration();
			int msgCount = 0;
			while (enumeration.hasMoreElements()) {
				msgCount++;
				System.out.println(((Message)enumeration.nextElement()).getBody(String.class));
			}
			
			if(msgCount==0){
				System.out.println("There is no message in the queue");
			}
		}		
	}

}
