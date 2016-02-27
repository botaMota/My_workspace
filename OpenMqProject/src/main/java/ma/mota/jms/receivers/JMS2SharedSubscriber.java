package ma.mota.jms.receivers;

import com.sun.messaging.ConnectionFactory;

import javax.jms.*;

/**
 * Created by Elmoutaoukil on 27/02/2016.
 */
public class  JMS2SharedSubscriber implements MessageListener{

    public JMS2SharedSubscriber(){
        ConnectionFactory cf = new ConnectionFactory();
        JMSContext ctx = cf.createContext();
        Topic topic = ctx.createTopic("My_First_Topic");

        JMSConsumer jmsConsumer = ctx.createSharedConsumer(topic , "sub:3e");
        jmsConsumer.setMessageListener(this);

        System.out.println("Waiting for messages");
    }

    public static void main(String args[]) {

        new Thread(){

            @Override
            public void run() {
                new JMS2SharedSubscriber();
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
