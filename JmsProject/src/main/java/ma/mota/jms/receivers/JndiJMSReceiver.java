package ma.mota.jms.receivers;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JndiJMSReceiver {

	public static void main(String[] args) {

		 try {
				InitialContext context = new InitialContext();
				Connection connection = ((ConnectionFactory)context.lookup("ConnectionFactory")).createConnection();
				connection.start();
				Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
				Queue queue= (Queue) context.lookup("EM_TRADE.Q");
				MessageConsumer receiver = session.createConsumer(queue);
				TextMessage message = (TextMessage)receiver.receive();
				System.out.println(message.getText());
				connection.close();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("error");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

}
