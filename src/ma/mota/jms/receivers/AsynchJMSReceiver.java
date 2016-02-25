package ma.mota.jms.receivers;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class AsynchJMSReceiver implements MessageListener {

	public AsynchJMSReceiver() {

		try {
			Connection cx = new ActiveMQConnectionFactory(
					"tcp://localhost:61616").createConnection();
			cx.start();
			Session session = cx.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("EM_TRADE.Q");
			MessageConsumer receiver = session.createConsumer(queue);
			receiver.setMessageListener(this);
			System.out.println("Waiting for messages");
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new Thread(){
			@Override
			public void run() {
				new AsynchJMSReceiver();
			}
		}.start();

	}

	@Override
	public void onMessage(Message message) {

		TextMessage msg = (TextMessage) message;
		try {
			System.out.println("Message receved : " + msg.getText());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
