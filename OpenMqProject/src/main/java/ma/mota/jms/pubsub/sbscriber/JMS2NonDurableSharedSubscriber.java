package ma.mota.jms.pubsub.sbscriber;

import com.sun.messaging.ConnectionFactory;

import javax.jms.*;

/**
 * Created by Elmoutaoukil on 27/02/2016.
 */
public class  JMS2NonDurableSharedSubscriber implements MessageListener{

    public JMS2NonDurableSharedSubscriber(){
        ConnectionFactory cf = new ConnectionFactory();
        JMSContext ctx = cf.createContext();
        Topic topic = ctx.createTopic("EM_JMS2_TRADE.T");

        JMSConsumer jmsConsumer = ctx.createConsumer(topic);
        jmsConsumer.setMessageListener(this);

        System.out.println("Waiting for messages ...");
    }

    public static void main(String args[]) {

        new Thread(){

            @Override
            public void run() {
                new JMS2NonDurableSharedSubscriber();
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
