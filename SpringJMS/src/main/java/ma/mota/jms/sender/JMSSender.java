package ma.mota.jms.sender;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.MessagePostProcessor;

public class JMSSender {

	public static void main(String[] args) {
		
		ApplicationContext appContext = new ClassPathXmlApplicationContext("app-context.xml");
		
//		 Pre-Processing
//		MessageCreator mc = new MessageCreator() {
//			
//			public Message createMessage(Session session) throws JMSException {
//
//				TextMessage message = session.createTextMessage("Salam");
//				message.setStringProperty("Trader", "Mark");
//				return message;
//			}
//		};
		
		//Post-Procession
		
		MessagePostProcessor postProcessor = new MessagePostProcessor() {
			
			public Message postProcessMessage(Message message) throws JMSException {
				message.setStringProperty("Trader", "Mary");
				return message;
			}
		};
		
		JmsTemplate jmsTemplate = (JmsTemplate) appContext.getBean("jmsTemplate");
//		Simple string message and post-prcessing
		jmsTemplate.convertAndSend((Object)"salam mota" ,postProcessor);
		
//		Pre-processing
//		jmsTemplate.send(mc);
		System.out.println("Message sent");
		
		System.exit(0);

	}

}
