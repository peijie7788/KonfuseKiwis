package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Server, der alle Handlers verwaltet
 * enthaelt auch die Methode fuer Clientanmeldung,
 * Benachrichtigung, Begruessung und Abmeldung
 * @author peijie
 *
 */
public class Server {

	private List<String> listOfClients; 
	private List<Handler> handlers;
	private ServerSocket serverSocket;
	
	public static void main (String[] args) {
		
		Server server = new Server();
		server.initializeServer();
		server.acceptClients();
	}
	
	
	/**
	 * kontrolliert ob der Name schon vorhanden ist
	 * @param Der Name, mit dem der Client versucht zu verbinden
	 * @return true if der Name schon in der Liste vorhanden ist, ansonsten false.
	 */
	public boolean CheckAlreadyExist(String name) {
		
		for(String username : listOfClients) {
			
			if(name.equals(username)) {
				return true;
			}
		}
		
		this.listOfClients.add(name);
		return false;
	}
	
	/**
	 * Startet einen neuen Server, initialisiert ArrayLists von Clientnamen und Handlers.
	 */
	private void initializeServer(){
		try {
		serverSocket = new ServerSocket(5555);
		System.out.println("Server started!");
		listOfClients = new ArrayList<String>();
		handlers = new ArrayList<Handler>();
		}catch (Exception e) {
			System.out.print("Server already exists on this port");
		}
	}
	
	/**
	 * Der Server versucht, solange der laeuft, einen Client zu empfangen
	 * Wenn die Verbindung hergestellt worden ist, weist der jedem Client einen Handler zu,
	 * welcher dem client mitteilt, dass der sich anmelden soll
	 */
	private void acceptClients() {
		while(true) {
			
			try {
			
				Socket client = serverSocket.accept();
				
				Handler handler = new Handler(client,this);
				handlers.add(handler);
				
				Thread t1 = new Thread(handler);
				t1.start();
				
				handler.getWriter().println("Please register, input your name: ");
				handler.getWriter().flush();
				
			}catch(IOException e) {
				e.printStackTrace();
			}catch(Exception e) {
				
			}
		}
		
	}
	
	/**
	 * verschickt die Nachricht an alle Clients, die mit dem Server verbunden sind
	 * @param messageToClients
	 * 
	 */
	public void announce(String messageToClients) {
		
		
		for(Handler handler : handlers) {
			if(!handler.getUsername().equals("")) {
			handler.getWriter().println(messageToClients);
			handler.getWriter().flush();
			}
		}
	}
	
	
	/**
	 * Schick an den Clienten Begrüssung oder Ankündigung des Beitritts, je nach dem, welcher Client gerade beigetreten ist
	 * @param nameSourceClient
	 * der Name des gerade angemeldeten clients
	 */
	public void regard(String nameSourceClient) {
		

		for(Handler handler : handlers) {

			//Null-check notwendig, sonst wird for-schleife verlassen, wenn zwei Benutzer zeitlich nebeneinander einloggt 
			if(handler.getUsername()!=null &&  !handler.getUsername().equals("")) {
				if(handler.getUsername().equals(nameSourceClient)) {
				
				handler.getWriter().println("Welcome to Chatroom, " + nameSourceClient);
				handler.getWriter().flush();
				}
				else {
				handler.getWriter().println(nameSourceClient + " joined the room.");
				handler.getWriter().flush();
				}
			}
		}
	}
	
	/**
	 * Wenn ein Client bye eingibt, werden erstmal die anderen Clienten darüber informiert
	 * entfernt den Namen von der Namenliste
	 * entfernt den entsprechendem Handler von der Handlerliste
	 * @param handler
	 * 
	 */
	public void cutConnection(Handler handler) {
		
		listOfClients.remove(handler.getUsername());
		announce(handler.getUsername() + " left the room.");
		
		try {
			handler.getClient().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		handlers.remove(handler);
	}
}
