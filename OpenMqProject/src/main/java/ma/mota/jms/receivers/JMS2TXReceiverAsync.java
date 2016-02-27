package ma.mota.jms.receivers;

import com.sun.messaging.ConnectionFactory;

import javax.jms.*;

/**
 * Created by Elmoutaoukil on 27/02/2016.
 */
public class  JMS2TXReceiverAsync implements MessageListener{

	JMSContext ctx ;
    public JMS2TXReceiverAsync(){
        ConnectionFactory cf = new ConnectionFactory();
         ctx = cf.createContext(Session.SESSION_TRANSACTED);
        Queue queue = ctx.createQueue("EM_JMS2_TRADE.Q");

        JMSConsumer jmsConsumer = ctx.createConsumer(queue);
        jmsConsumer.setMessageListener(this);

        System.out.println("Waiting for messages ...");
    }

    public static void main(String args[]) {

        new Thread(){

            @Override
            public void run() {
                new JMS2TXReceiverAsync();
            }
        }.start();
    }

    @Override
    public void onMessage(Message message) {

    	try {
			System.out.println("Received Message : "+ message.getBody(String.class));
			
			if(message.getIntProperty("JMSXDeliveryCount")>2){
				System.out.println("Cannot process message - sending to DLQ");
				ctx.commit();
				return ;
			}
			
			Thread.sleep(5000);
			//simulated error
			ctx.rollback();
			System.out.println("Error processing message retries = "+message.getIntProperty("JMSXDeliveryCount"));
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
}
