package exercise33_01;


// Exercise31_01Server.java: The server can communicate with
// multiple clients concurrently using the multiple threads
import java.util.*;
import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/*
 * Author: Jason Snow
 * Date: 04/20/2021
 * 
 * This program was edited to act as a server, receiving information from a client, making a loan object with that information
 * then using the object ot compute monthly and total payments it would then return to the client.
 */

public class Exercise33_01Server extends Application {
  // Text area for displaying contents
  private TextArea ta = new TextArea();

  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    ta.setWrapText(true);
   
    // Create a scene and place it in the stage
    Scene scene = new Scene(new ScrollPane(ta), 400, 200);
    primaryStage.setTitle("Exercise31_01Server"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
    
    new Thread(() -> {
    	try {
    		//create a server socket
    		ServerSocket serverSocket = new ServerSocket(8000);
    		Platform.runLater(() -> ta.appendText("Server started at " + new Date() + '\n'));
    		
    		//listen for a connection request
    		Socket socket = serverSocket.accept();
    		Platform.runLater(() -> ta.appendText("Connected to a client at " + new Date() + '\n'));
    		
    		
    		//create data input and output streams
    		DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
    		DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
    		
    		while (true) {
    			//receive annual interest rate from the client
    			double annualInterestRate = inputFromClient.readDouble();
    			
    			//receive number of years
    			int numberOfYears = inputFromClient.readInt();
    			
    			//receive loan amount
    			double loanAmount = inputFromClient.readDouble();
    			
    			//create loan object
    			Loan loan = new Loan(annualInterestRate, numberOfYears, loanAmount);
				
    			//Send monthly payment and total payment
    			outputToClient.writeDouble(loan.getMonthlyPayment());
    			outputToClient.writeDouble(loan.getTotalPayment());
    			
    			Platform.runLater(() -> {
	    			ta.appendText("Annual Interest Rate: " + annualInterestRate + '\n');
	    			ta.appendText("Number of Years: " + numberOfYears + '\n');
	    			ta.appendText("Loan Amount: " + loanAmount + '\n');
	    			ta.appendText("Monthly Payment: " + loan.getMonthlyPayment() + '\n');
	    			ta.appendText("Total Payment: " + loan.getTotalPayment() + '\n');
    			});
    		}
    	}
    	catch(IOException ex) {
    		ex.printStackTrace();
    	}
    }).start();
    
  
    
    
  }
    
  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
