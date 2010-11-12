package libreSubs.libreSubsSite;

import java.net.URL;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;

public class Start {

	private static final int PORT = 8081;

	public static void main(final String[] args) throws Exception {
		final Server server = new Server();
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
		// bb.setWar("src/test/resources");
		final URL resource = Start.class.getResource("/WEB-INF/web.xml");
		bb.setDescriptor(resource.getFile());
		
		server.addHandler(bb);

		try {
			System.out.println(">>> STARTING EMBEDDED JETTY SERVER, PRESS ANY KEY TO STOP");
			server.start();
			System.in.read();
			System.out.println(">>> STOPPING EMBEDDED JETTY SERVER"); 
			server.stop();
			server.join();
		} catch (final Exception e) {
			e.printStackTrace();
			System.exit(100);
		}
	}
}
