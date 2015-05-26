package twoWayCommunicationWithDatabase;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * A Simple Socket client that connects to our socket server
 * 
 */
public class SocketClientSQL {

	private static JFrame window = new JFrame("SQL Client");
	private static String[] list = { "SEARCH", "INSERT", "UPDATE",
			"ADDTOJOURNEY", "STOPSESSION" };
	private static JComboBox<String> command = new JComboBox<String>(list);
	private static JTextField statement = new JTextField();
	private static JButton commit = new JButton("SQL Befehl ausführen");

	private String hostname;
	private int port;
	private static Socket socketClient;

	public SocketClientSQL(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
	}

	public void connect() throws UnknownHostException, IOException {
		System.out.println("Attempting to connect to " + hostname + ":" + port);
		socketClient = new Socket(hostname, port);
		System.out.println("Connection Established");
	}

	public void readResponse() throws IOException {
		String userInput;
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(
				socketClient.getInputStream()));

		System.out.print("RESPONSE FROM SERVER: ");
		while ((userInput = stdIn.readLine()) != null) {
			System.out.println(userInput);
		}
	}
	
	/**
	 * TODO create default arrays for statements with multiple parameters 
	 * and fill them instead of creating new ones
	 * 
	 * @param arg
	 */

	public static void main(String arg[]) {
		// Creating a SocketClientSQL object
		final SocketClientSQL client = new SocketClientSQL("localhost", 9991);
		BorderLayout layout = new BorderLayout();
		statement = new JTextField(100);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLayout(layout);
		window.add(command, BorderLayout.LINE_START);
		window.add(statement, BorderLayout.CENTER);
		window.add(commit, BorderLayout.LINE_END);
		window.pack();
		window.setVisible(true);

		try {
			// trying to establish connection to the server
			client.connect();

			commit.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					int selectedCommand = command.getSelectedIndex();
					String userCommand = list[selectedCommand];
					userCommand = userCommand + "?";
					System.out.println(userCommand);
					try {
						BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
								socketClient.getOutputStream()));
						writer.write(userCommand);
						writer.newLine();
						writer.flush();

						if (userCommand.equals("STOPSESSION?")) {
							window.dispose();
							System.exit(0);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
								socketClient.getOutputStream()));
						writer.write(statement.getText());
						writer.newLine();
						writer.flush();
						System.out.println(statement.getText());
					} catch (IOException e) {
						// TODO: handle exception
					}
				}
			});

		} catch (UnknownHostException e) {
			System.err.println("Host unknown. Cannot establish connection");
		} catch (IOException e) {
			System.err
					.println("Cannot establish connection. Server may not be up. "
							+ e.getMessage());
		}
	}
}