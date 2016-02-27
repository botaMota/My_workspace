package ma.mota.jms.receivers;

import com.sun.messaging.ConnectionFactory;

import javax.jms.*;

/**
 * Created by Elmoutaoukil on 27/02/2016.
 */
public class  JMS2ReceiverAsync implements MessageListener{

    public JMS2ReceiverAsync(){
        ConnectionFactory cf = new ConnectionFactory();
        JMSContext ctx = cf.createContext();
        Queue queue = ctx.createQueue("EM_JMS2_TRADE.Q");

        JMSConsumer jmsConsumer = ctx.createConsumer(queue);
        jmsConsumer.setMessageListener(this);

        System.out.println("Waiting for messages");
    }

    public static void main(String args[]) {

        new Thread(){

            @Override
            public void run() {
                new JMS2ReceiverAsync();
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
