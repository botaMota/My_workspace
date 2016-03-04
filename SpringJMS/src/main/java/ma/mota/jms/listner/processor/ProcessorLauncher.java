package ma.mota.jms.listner.processor;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProcessorLauncher {

	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("app-context.xml");
		System.out.println("Waiting on messages ...");
	}

}
