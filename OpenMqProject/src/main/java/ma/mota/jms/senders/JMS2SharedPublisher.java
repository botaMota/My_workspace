package ma.mota.jms.senders;

import com.sun.messaging.ConnectionFactory;


import javax.jms.JMSContext;
import javax.jms.Topic;
import java.text.DecimalFormat;

/**
 * Created by Elmoutaoukil on 27/02/2016.
 */
public class JMS2SharedPublisher {

    public static void main(String args[]){

        ConnectionFactory cf = new ConnectionFactory();
        JMSContext ctx = cf.createContext();
        Topic topic = ctx.createTopic("My_First_Topic");

        DecimalFormat df = new DecimalFormat("##.00");
        String price = df.format(98.00 + Math.random());
        ctx.createProducer().send(topic ,price);
        System.out.println("Message sent");
        
        ctx.stop();

    }
}
