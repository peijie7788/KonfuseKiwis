package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import model.BreakOut;

/**
 * Listener von Connect Button
 * @author peijie
 *
 * @param <T>
 */
public class ConnectController<T extends Event> implements EventHandler<T> {
	private BreakOut breakOut;
	
	
	public ConnectController(BreakOut breakOut) {
		this.breakOut = breakOut;
	}

	
	/**
	 * wird aufgerufen, wenn auf Connect gedrueckt wird 
	 */
	@Override
	public void handle(T event) {
		try {
			String ip = breakOut.getView().getIpTextfield().getText();
			int port = Integer.parseInt(breakOut.getView().getPortTextfield().getText());
			breakOut.getClient().setIp(ip);
			breakOut.getClient().setPort(port);
			new Thread(breakOut.getClient()).start();
			breakOut.getClient().setConnected(true);
		}catch(Exception e) {
			System.out.println("No reachable Server. Wrong IP-Adress or Port!");
			e.printStackTrace();
		}
	}

}
