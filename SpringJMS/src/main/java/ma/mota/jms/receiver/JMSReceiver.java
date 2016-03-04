package ma.mota.jms.receiver;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

public class JMSReceiver {

	public static void main(String[] args) throws JMSException {
		
		ApplicationContext appContext = new ClassPathXmlApplicationContext("app-context.xml");

		JmsTemplate jmsTemplate = (JmsTemplate) appContext.getBean("jmsTemplate");
		System.out.println("Waiting on messages ...");
		
		//Simple string message
		//String body = (String) jmsTemplate.receiveAndConvert();
		//System.out.println("Received messgae : "+body);
		
		//Pre-processing and Post-processing
		TextMessage message = (TextMessage) jmsTemplate.receiveSelected("Trader = 'Mary'");
		System.out.println("Message body : "+message.getText());
		System.out.println("Trader : "+ message.getStringProperty("Trader"));
		
		System.exit(0);
	}

}
