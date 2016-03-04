package ma.mota.jms.listner.processor;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class TradeProcessor implements MessageListener{

	public void onMessage(Message msg) {
		
		TextMessage message = (TextMessage) msg;
		try {
			System.out.println("Message body in processor method : "+message.getText());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
