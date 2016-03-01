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

public class JMSVmReceiver implements MessageListener {

	private Session session ;
	
	private int id ;
	
	public JMSVmReceiver(int id) {
		
		Connection cx;
		try {
			this.id = id ;
			cx = new ActiveMQConnectionFactory("vm://embedded1").createConnection();
			cx.start();
			session = cx.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("EM_EMBED_INTERNEL.Q");
			MessageConsumer receiver = session.createConsumer(queue);
			receiver.setMessageListener(this);
			System.out.println("Receiver ("+ id+"): waiting for messages ...");
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onMessage(Message msg) {
		TextMessage message = (TextMessage) msg ;
		try {
			Thread.sleep(1000);
			System.out.println("Trade palced ("+ id+"): "+message.getText());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
