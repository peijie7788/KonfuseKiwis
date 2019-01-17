package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Gehoert streng zur Serverseite.
 * Zu Jedem Client ist ein Handler zugewiesen, der in unterschiedlichen Threads laeuft
 * Hier liegen auch die E/A Streams die, dem jeweiligen Client zur Verfuegung stehen.
 * @author peijie
 *
 */
public class Handler implements Runnable{

	private Socket client;
	private BufferedReader reader;
	private Server server;
	private String username = "";
	private PrintWriter writer;
	
	public Handler (Socket client, Server server) throws IOException {
		this.client = client;
		this.server = server;
		
		//Input Streams from client initialization
		InputStream in = client.getInputStream();
		reader = new BufferedReader(new InputStreamReader(in));
		
		//OutputStreams from client initialization
		OutputStream out = client.getOutputStream();
		writer = new PrintWriter(out);
	}

	/**
	 * Versucht immer wieder, die Nachrichten vom Client zu empfangen,
	 * und diese an allen Clienten weiter zu leiten.
	 * 
	 */
	@Override
	public void run() {
		try {
		
		//verarbeitet die Anmeldung
		checkin();
		
		//verarbeitet die 
		String messageFromClient;
		
		while((messageFromClient = reader.readLine()) != null) {
			
			if(messageFromClient.equals("bye")) {
				server.cutConnection(this);
				break;
			}else if(messageFromClient.startsWith("!@#$WIN%^&*")){
				server.announce(username + " won a level with " + messageFromClient.substring(11) + " points!");
			}else if(messageFromClient.startsWith("!@#$LOSE%^&*")){
				server.announce(username + " lost a level with " + messageFromClient.substring(12) + " points!");
			}else if(messageFromClient.equals("!@#$START%^&*")){
				server.announce(username + " started a new match!");
			}else {
				server.announce(username + ": " + messageFromClient);
				System.out.println("Recieved from Client: " + messageFromClient);
			}
			
		}
		
		}catch(Exception e){
			
		}
	}

	/**
	 * versucht, den Client anzumelden,
	 * fuer eine erfolgreiche Anmeldung muss es festgestellt werden,
	 * dass der Name nicht leer und nicht in der Namenliste vorhanden ist.
	 * Wenn die Bedingungen nicht erfuellt sind, ruft sich rekursiv auf.
	 * @throws IOException
	 */
	private void checkin() throws IOException {
		String nameCandidate;
		
		if((nameCandidate = reader.readLine())!=null) {
			System.out.println("Recieved name from Client: " + username);
			
			if(!server.CheckAlreadyExist(nameCandidate)) {
				username = nameCandidate;
				server.regard(nameCandidate);
			}
			else {
				writer.println("The name already exists! Reenter your name:");
				writer.flush();
				checkin();
			}
			
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public PrintWriter getWriter() {
		return writer;
	}

	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}	
	
	public Socket getClient() {
		return client;
	}

	public void setClient(Socket client) {
		this.client = client;
	}

}
