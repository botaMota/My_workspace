package ma.mota.jms.file.based.technique;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.jms.Connection;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSSender {

	private static String sourceFile = "sourceImageDir/promo.jpg";
	private static String tempDir = "tempImageDir/";

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
		
		String fileName = copyFileToTemp();
		TextMessage message = session.createTextMessage(fileName);
		sender.send(message);		
		System.out.println("Message Sent");
		connection.close();
		System.exit(0);
	}

//	public byte[] readFileFromSource() throws Exception {
//		File file = new File(sourceFile);
//		try (InputStream is = new FileInputStream(file);) {
//			byte[] bytes = new byte[(int) file.length()];
//			is.read(bytes);
//			return bytes;
//		}
//	}

	public String copyFileToTemp() throws Exception {
		File inFile = new File(sourceFile);
		String outFile = tempDir + UUID.randomUUID();
		try (InputStream is = new FileInputStream(inFile);) {
			try (OutputStream os = new FileOutputStream(outFile);) {
				byte[] bytes = new byte[(int) inFile.length()];
				is.read(bytes);
				os.write(bytes);
				return outFile;
			}
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

