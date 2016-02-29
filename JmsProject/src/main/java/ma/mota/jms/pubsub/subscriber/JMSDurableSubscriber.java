package ma.mota.jms.pubsub.subscriber;


import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSDurableSubscriber implements MessageListener {

	public JMSDurableSubscriber() throws Exception{
		TopicConnection connection = new ActiveMQConnectionFactory("tcp://localhost:61616?jms.clientID=client:123").createTopicConnection();
		connection.start();
		TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("EM_Trade.T");
		TopicSubscriber subscriber = session.createDurableSubscriber(topic , "sub:456");
		subscriber.setMessageListener(this);
		System.out.println("Waiting for messages ...");
	}
	public static void main(String[] args) {
		
		new Thread(){
			public void run() {
				try {
					new JMSDurableSubscriber();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
	}

	public void onMessage(Message msg) {
		TextMessage message = (TextMessage) msg ;
		try {
			System.out.println("Recieved message : "+ message.getText());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
