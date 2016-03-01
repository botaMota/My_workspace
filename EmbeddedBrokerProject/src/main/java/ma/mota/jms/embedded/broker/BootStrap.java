package ma.mota.jms.embedded.broker;

import org.apache.activemq.broker.BrokerService;

public class BootStrap {

	public static void main(String[] args) {

		new Thread(){
			public void run() {
				new BootStrap().startBroker();
			};
		}.start();

	}

	public void startBroker() {

		BrokerService brokerService = new BrokerService();
		try {
			brokerService.addConnector("tcp://localhost:61888");
			brokerService.setBrokerName("embedded1");
			brokerService.setPersistent(false);
			brokerService.start();
			System.out.println("Embedded broker stated");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
