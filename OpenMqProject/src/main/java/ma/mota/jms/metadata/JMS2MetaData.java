package ma.mota.jms.metadata;

import java.util.Enumeration;

import javax.jms.ConnectionMetaData;
import javax.jms.DeliveryMode;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;

import com.sun.messaging.ConnectionFactory;

public class JMS2MetaData {
	public static void main(String[] args) throws JMSException {
		
		ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();
		try (JMSContext jmsContext = cf.createContext();) {
			
			ConnectionMetaData metaData = jmsContext.getMetaData();
			System.out.println(" ");
			System.out.println("JMS Version : "+metaData.getJMSVersion());
			System.out.println("JMS Provider : "+metaData.getJMSProviderName());
			System.out.println("JMS Provider Version : "+metaData.getProviderVersion());
			System.out.println("JMSX propperties supported :");
			
			Enumeration<?> e = metaData.getJMSXPropertyNames();
			while (e.hasMoreElements()) {
				System.out.println("  "+e.nextElement());
			}
		}		
	}

}
