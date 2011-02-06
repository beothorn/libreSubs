package libreSubs.libreSubsSite;

import java.net.URL;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;

public class StartUsingJetty {

	private static final int PORT = 8081;
	private final Server server;

	public StartUsingJetty() {
		server = new Server();
	}
	
	public void start(){
		final SocketConnector connector = new SocketConnector();
		
		// Set some timeout options to make debugging easier.
		connector.setMaxIdleTime(1000 * 60 * 60);
		connector.setSoLingerTime(-1);
		connector.setPort(PORT);
		server.setConnectors(new Connector[] { connector });

		final WebAppContext bb = new WebAppContext();
		bb.setServer(server);
		bb.setContextPath("/");
		bb.setWar("src/main/webapp");
		final URL resource = StartUsingJetty.class.getResource("/jettyFiles/jettyWebInf.xml");
		bb.setDescriptor(resource.getFile());
		
		server.addHandler(bb);

		try {
			server.start();
		} catch (final Exception e) {
			e.printStackTrace();
			System.exit(100);
		}
	}
	
	public void stop(){
		try { 
			server.stop();
			server.join();
		} catch (final Exception e) {
			e.printStackTrace();
			System.exit(100);
		}
	}
	
	public static void main(final String[] args) throws Exception {
		final StartUsingJetty startUsingJetty = new StartUsingJetty();
		System.out.println(">>> STARTING EMBEDDED JETTY SERVER, PRESS ANY KEY TO STOP");
		startUsingJetty.start();
		System.in.read();
		System.out.println(">>> STOPPING EMBEDDED JETTY SERVER"); 
		startUsingJetty.stop();
	}
}
