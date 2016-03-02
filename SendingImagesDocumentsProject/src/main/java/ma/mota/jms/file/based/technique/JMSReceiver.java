package ma.mota.jms.file.based.technique;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSReceiver implements MessageListener {

	private static String targetFile = "targetImageDir/promo.jpg";

	Connection connection;
	Session session;
	Queue queue;
	MessageConsumer receiver;

	public JMSReceiver() throws Exception {
		connection = new ActiveMQConnectionFactory("tcp://localhost:61616")
				.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		queue = session.createQueue("EM_TRADE.Q");
		receiver = session.createConsumer(queue);
		System.out.println("Waiting for messages ...");
		receiver.setMessageListener(this);
	}

	public byte[] readFileFromTemp(String tempFilename) throws Exception {
		File file = new File(tempFilename);
		try (InputStream is = new FileInputStream(file);) {
			byte[] bytes = new byte[(int) file.length()];
			is.read(bytes);
//			 file.delete(); //commented to view tempdir
			return bytes;
		}
	}

	public void writeFileToTarget(byte[] bytes) throws Exception {
		try (OutputStream os = new FileOutputStream(targetFile);) {
			os.write(bytes);
		}
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
			System.out.println("File received");
			String filename = ((TextMessage) message).getText();
			writeFileToTarget(readFileFromTemp(filename));
			System.out.println("File traited");
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
