package ma.mota.jms.metadata;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionMetaData;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSMetaData {

	public static void main(String[] args) {
		
		try {
			Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
			ConnectionMetaData metaData = connection.getMetaData();
			System.out.println(" ");
			System.out.println("JMS Version : "+metaData.getJMSVersion());
			System.out.println("JMS Provider : "+metaData.getJMSProviderName());
			System.out.println("JMS Provider Version : "+metaData.getProviderVersion());
			System.out.println("JMSX propperties supported :");
			
			Enumeration<?> e = metaData.getJMSXPropertyNames();
			while (e.hasMoreElements()) {
				System.out.println("  "+e.nextElement());
			}
			connection.close();
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error");
		}

	}

}
