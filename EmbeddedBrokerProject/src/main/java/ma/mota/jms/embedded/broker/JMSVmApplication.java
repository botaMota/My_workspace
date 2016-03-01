package ma.mota.jms.embedded.broker;

import java.util.Arrays;
import java.util.List;

import ma.mota.jms.producers.JMSVmSender;
import ma.mota.jms.receivers.JMSVmReceiver;

import org.apache.activemq.broker.BrokerService;

public class JMSVmApplication {

	public static void main(String[] args) {
		new Thread(){
			public void run() {
				JMSVmApplication app = new JMSVmApplication();
				app.startBroker();
				app.startTradeProcessor();
				app.processTrades();
			};
		}.start();
	}
	
	private void startBroker() {

		BrokerService brokerService = new BrokerService();
		try {
			brokerService.addConnector("tcp://localhost:61888");
			brokerService.setBrokerName("embedded1");
			brokerService.setPersistent(false);
			brokerService.start();
			System.out.println("Internal Embedded broker stated");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void startTradeProcessor() {
		new JMSVmReceiver(1);
		new JMSVmReceiver(2);
		new JMSVmReceiver(3);
		new JMSVmReceiver(4);
		new JMSVmReceiver(5);
		new JMSVmReceiver(6);
		new JMSVmReceiver(7);
	}
	
	private void processTrades() {
		JMSVmSender sender = new JMSVmSender();
		for (String trade : trades) {
			sender.sendMessage(trade);
		}
	}
	
	private List<String> trades = 
			Arrays.asList("BUY AAPL 2000","BUY IBM 4400","BUY ATT 2400","SELL AARL 1000","SELL IBM 2200");

}
