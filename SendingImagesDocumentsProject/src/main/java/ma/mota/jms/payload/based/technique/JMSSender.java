package ma.mota.jms.payload.based.technique;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSSender {

	private static String sourceFile = "sourceImageDir/promo.jpg";
	
	Connection connection ;
	Session session ;
	Queue queue ;
	MessageProducer sender ;
	
	
	public JMSSender() throws Exception {
		connection = new ActiveMQConnectionFactory("tcp://localhost:61616")
				.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		queue = session.createQueue("EM_TRADE.Q");
		sender = session.createProducer(queue);
	}
	
	public void sendMessage() throws Exception{
		
		byte[] bs = readFileFromSource();
		BytesMessage bytesMessage = session.createBytesMessage();
		bytesMessage.writeBytes(bs);
		sender.send(bytesMessage);		
		System.out.println("Message Sent");
		connection.close();
		System.exit(0);
	}

	public byte[] readFileFromSource() throws Exception {
		File file = new File(sourceFile);
		try (InputStream is = new FileInputStream(file);) {
			byte[] bytes = new byte[(int) file.length()];
			is.read(bytes);
			return bytes;
		}
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

