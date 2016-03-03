package ma.mota.jms.streaming.based.technique;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSSender {

//	private static String sourceFile = "sourceImageDir/Hibernate.pdf";
	private static String sourceFile = "sourceImageDir/ebooks.zip";

	Connection connection;
	Session session;
	Queue queue;
	MessageProducer sender;
	String uuid ;

	public JMSSender() throws Exception {
		connection = new ActiveMQConnectionFactory("tcp://localhost:61616")
				.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		queue = session.createQueue("EM_TRADE.Q");
		sender = session.createProducer(queue);
	}

	public void sendMessage() throws Exception {
		
		
		uuid = UUID.randomUUID().toString();

		sendStream(null, "START");

		InputStream is = new FileInputStream(sourceFile);
		byte[] bytes = new byte[10000000];
		while ((is.read(bytes)) >= 0) {
			sendStream(bytes, null);
		}
		sendStream(null, "END");
		is.close();

		System.out.println("Message Sent");
		connection.close();
		System.exit(0);
	}

	public void sendStream(byte[] bytes, String marker) throws Exception {
		BytesMessage msg = session.createBytesMessage();
		 msg.setStringProperty("JMSXGroupID", uuid);
		if (bytes == null) {
			msg.setStringProperty("sequenceMarker", marker);
			System.out.println("Sending " + marker);
		} else {
			msg.writeBytes(bytes);
			System.out.println("Streaming...");
		}
		sender.send(msg);
	}

	public static void main(String[] args) {
		try {
			new JMSSender().sendMessage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error");
		}

	}

}
