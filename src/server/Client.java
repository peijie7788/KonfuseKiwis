package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import model.BreakOut;

/**
 * In jedem Spiel wird ein Client initialisiert, der sich mithilfe von IP-Adresse und Port
 * mit einem Server verbinden kann.
 * Der Client kommuniziert mit dem Server mit Hilfe von Streams.
 * Er erstellt einen extra Thread fuer das Empfangen von Nachrichten.
 * @author Niklas, Peijie
 *
 */
public class Client implements Runnable{
	
	BufferedReader reader;
	PrintWriter writer;
	BreakOut breakOut;
	
	private int port;
	private String ip;
	
	private boolean connected;
		
	public Client(BreakOut breakOut) {
		this.breakOut = breakOut;
	}

	/**
	 * Erstellt einen neuen Socket client mit einer Ip-Adresse und einem Port und
	 * versucht sich auf den Server, der jewiligen Adresse und Port zu verbinden.
	 * Initialisiert InPut- und OutputStream, sowie Reader und Writer.
	 * Startet einen neuen Thread ThreadMessageFromServer.
	 */
	@Override
	public void run() {
		
		try {
			Socket client = new Socket(ip, port);
			System.out.println("Client started!");
			
			// Streams
			OutputStream out = client.getOutputStream();
			writer = new PrintWriter(out);
			
			InputStream in = client.getInputStream();
			reader = new BufferedReader(new InputStreamReader(in));
			
			new Thread(new ThreadMessageFromServer()).start();
			

		} catch (UnknownHostException e) {
			System.out.println("Server unreachable");
		} catch (IOException e) {
			System.out.println("Server unreachable");
		}
		
	}
	
	/**
	 * Ist eine Thread Klasse, für das Empfangen von Nachrichten des Servers.
	 * @author Niklas
	 *
	 */
	class ThreadMessageFromServer implements Runnable{

		/**
		 * Wartet auf Nachrichten vom Server und schreibt
		 * diese in das Chatfenster des Clients.
		 */
		@Override
		public void run() {
			
			String messageFromServer;
			
			try {
				while((messageFromServer = reader.readLine()) != null) {
				
				System.out.println(messageFromServer);
				breakOut.getView().getTextArea().appendText(messageFromServer + "\n");
				
				}
			} catch (IOException e) {
				System.out.println("Stream already closed!");
			}
		}
		
	}

	public BufferedReader getReader() {
		return reader;
	}

	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}

	public PrintWriter getWriter() {
		return writer;
	}

	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}
}
