package twoWayCommunicationWithDatabase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

public class SocketClientHandler implements Runnable {

	private Socket client;
	private DBV dbv;

	public SocketClientHandler(Socket client) {
		this.client = client;
		dbv = new DBV("localhost", "5432", "postgres", "test", "postgres",
				"1234");
	}

	@Override
	public void run() {
		try {
			System.out.println("Thread started with name:"
					+ Thread.currentThread().getName());
			readResponse();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void readResponse() throws Exception {
		String userInput;
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(
				client.getInputStream()));
		while ((userInput = stdIn.readLine()) != null) {
			if (userInput.equals("TIME?")) {
				System.out
						.println("REQUEST TO SEND TIME RECEIVED. SENDING CURRENT TIME");
				sendTime();
				break;
			} else if (userInput.equals("EXIT!")) {
				System.out.println("REQUEST TO SHUTDOWN SERVER. SHUTTING DOWN");
				shutDown();
			}
			/*
			 * else if (userInput.startsWith("SEARCH???")) { String[]
			 * inputRequest = userInput.split("???");
			 * dbv.doSearch(inputRequest[1]); } else if
			 * (userInput.startsWith("INSERT???")) { String[] inputRequest =
			 * userInput.split("???"); String[] request =
			 * inputRequest[1].split(";"); dbv.doInsert(request); } else if
			 * (userInput.startsWith("UPDATE???")) { String[] inputRequest =
			 * userInput.split("???"); String[] request =
			 * inputRequest[1].split(";"); dbv.doChange(request); } else if
			 * (userInput.startsWith("ADDTOJURNEY???")) { String[] inputRequest
			 * = userInput.split("???"); String[] request =
			 * inputRequest[1].split(";"); dbv.doInsertInNewJourney(request); }
			 * else if (userInput.startsWith("STOPSESSION!!!")) {
			 * Thread.currentThread().interrupt(); }
			 */
			else if (userInput.startsWith("SEARCH?")) {
				String statementInput = stdIn.readLine();
				dbv.doSearch(statementInput);

			} else if (userInput.startsWith("INSERT?")) {
				String statementInput = stdIn.readLine();
				dbv.doInsert(statementInput.split(";"));

			} else if (userInput.startsWith("UPDATE?")) {
				String statementInput = stdIn.readLine();
				dbv.doChange(statementInput.split(";"));

			} else if (userInput.startsWith("ADDTOJOURNEY?")) {
				String statementInput = stdIn.readLine();
				dbv.doInsertInNewJourney(statementInput.split(";"));

			} else if (userInput.startsWith("STOPSESSION?")) {
				break;
			}
			System.out.println(userInput);
		}
	}

	private void shutDown() throws IOException, InterruptedException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
				client.getOutputStream()));
		writer.write("SHUTTING DOWN!");
		writer.flush();
		writer.close();
		System.exit(0);
	}

	private void sendTime() throws IOException, InterruptedException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
				client.getOutputStream()));
		writer.write(new Date().toString());
		writer.flush();
		writer.close();
	}

}