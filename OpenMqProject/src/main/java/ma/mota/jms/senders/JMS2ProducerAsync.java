package ma.mota.jms.senders;

import java.util.concurrent.CountDownLatch;

import javax.jms.CompletionListener;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Queue;

import com.sun.messaging.ConnectionFactory;

public class JMS2ProducerAsync {

	public static void main(String[] args) throws Exception {


		ConnectionFactory connectionFactory = new ConnectionFactory();
		
		try(JMSContext jmsContext = connectionFactory.createContext();){
			Queue queue = jmsContext.createQueue("EM_JMS2_TRADE.Q");
			
			CountDownLatch latch =new CountDownLatch(1);
			MyCompletionListner myCompletionListner = new MyCompletionListner(latch);
			
			jmsContext.createProducer().setAsync(myCompletionListner).send(queue, "Salam mota");
			System.out.println("Message sent");
			
			for (int i = 0; i < 10; i++) {
				System.out.println("Processing ...");
				Thread.sleep(1000);
			}
			System.out.println("End processing.");
		}
	}

}


class MyCompletionListner implements CompletionListener{
	
	public CountDownLatch latch = null ;
	
	public  MyCompletionListner(CountDownLatch latch) {
		this.latch = latch ;
	}

	@Override
	public void onCompletion(Message arg0) {
		latch.countDown();
		System.out.println("message acknowledged by server");
	}

	@Override
	public void onException(Message arg0, Exception e) {
		latch.countDown();
		System.out.println("Anable to send message : "+ e);
	}
	
}