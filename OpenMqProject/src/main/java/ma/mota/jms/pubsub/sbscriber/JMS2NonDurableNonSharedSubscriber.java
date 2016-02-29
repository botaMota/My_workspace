package ma.mota.jms.pubsub.sbscriber;

import com.sun.messaging.ConnectionFactory;

import javax.jms.*;

/**
 * Created by Elmoutaoukil on 27/02/2016.
 */
public class  JMS2NonDurableNonSharedSubscriber implements MessageListener{

    public JMS2NonDurableNonSharedSubscriber(){
        ConnectionFactory cf = new ConnectionFactory();
        JMSContext ctx = cf.createContext();
        Topic topic = ctx.createTopic("EM_JMS2_TRADE.T");

        JMSConsumer jmsConsumer = ctx.createSharedConsumer(topic , "sub:abc123");
        jmsConsumer.setMessageListener(this);

        System.out.println("Waiting for messages ...");
    }

    public static void main(String args[]) {

        new Thread(){

            @Override
            public void run() {
                new JMS2NonDurableNonSharedSubscriber();
            }
        }.start();
    }

    @Override
    public void onMessage(Message message) {

        try {
            System.out.println("Received Message : "+ message.getBody(String.class));
        } catch (JMSException e) {
            e.printStackTrace();
            System.out.println("ERROR Message");
        }
    }
}
