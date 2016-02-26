package ma.mota.jms.senders;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;


public class HelloProducer {

	public static void sendMessage() {

		try {

			// Start connection
			ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();
			Connection connection = cf.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createTopic("HelloWorld");
			
			MessageProducer producer = session.createProducer(destination);
			connection.start();

			// create message to send
			TextMessage message = session.createTextMessage();
			message.setText("Hello World (" + System.currentTimeMillis() + ") from HelloProducer.java");

			System.out.println("Send from HelloProducer.java");
			producer.send(message);

			// close everything
			producer.close();
			session.close();
			connection.close();

		} catch (JMSException ex) {
			System.out.println("Error = " + ex.getMessage());
		}
	}

	public static void main(String args[]) {
		HelloProducer.sendMessage();
	}
}
