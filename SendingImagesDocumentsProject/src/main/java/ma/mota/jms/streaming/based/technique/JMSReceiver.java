package ma.mota.jms.streaming.based.technique;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSReceiver implements MessageListener {

//	private static String targetFile = "targetImageDir/Hibernate.pdf";
	private static String targetFile = "targetImageDir/ebooks.zip";

	Connection connection;
	Session session;
	Queue queue;
	MessageConsumer receiver;
	private OutputStream os;

	public JMSReceiver() throws Exception {
		connection = new ActiveMQConnectionFactory("tcp://localhost:61616")
				.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		queue = session.createQueue("EM_TRADE.Q");
		receiver = session.createConsumer(queue);
		System.out.println("Waiting for messages ...");
		receiver.setMessageListener(this);
		os = new FileOutputStream(targetFile);
	}


	public static void main(String[] args) {
		try {
			new JMSReceiver();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error");
		}

	}
	
	@Override
	public void onMessage(Message message) {

		try {
			if (message.propertyExists("sequenceMarker")) {
				String marker = message.getStringProperty("sequenceMarker");
				System.out.println("Received Marker: " + marker);

				if (marker.equals("START")) {
					os = new FileOutputStream(targetFile);
				}

				if (marker.equals("END")) {
					os.close();
				}
			} else {
				BytesMessage msg = (BytesMessage) message;
				System.out.println("Received Message");
				byte[] bytes = new byte[new Long(msg.getBodyLength()).intValue()];
				msg.readBytes(bytes);
				os.write(bytes);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
