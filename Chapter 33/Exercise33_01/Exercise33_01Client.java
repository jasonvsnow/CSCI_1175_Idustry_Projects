package exercise33_01;

// Exercise31_01Client.java: The client sends the input to the server and receives
// result back from the server
import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/*
 * Author: Jason Snow
 * Date: 04/20/2021
 * 
 * This program was edited to send information to a server which would process the information and
 * return monthly and total payment amounts which would be displayed
 */

public class Exercise33_01Client extends Application {
  // Text field for receiving radius
  private TextField tfAnnualInterestRate = new TextField();
  private TextField tfNumOfYears = new TextField();
  private TextField tfLoanAmount = new TextField();
  private Button btSubmit= new Button("Submit");
  private DataOutputStream toServer = null;
  private DataInputStream fromServer = null;

  // Text area to display contents
  private TextArea ta = new TextArea();
  
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    ta.setWrapText(true);
   
    GridPane gridPane = new GridPane();
    gridPane.add(new Label("Annual Interest Rate"), 0, 0);
    gridPane.add(new Label("Number Of Years"), 0, 1);
    gridPane.add(new Label("Loan Amount"), 0, 2);
    gridPane.add(tfAnnualInterestRate, 1, 0);
    gridPane.add(tfNumOfYears, 1, 1);
    gridPane.add(tfLoanAmount, 1, 2);
    gridPane.add(btSubmit, 2, 1);
    
    tfAnnualInterestRate.setAlignment(Pos.BASELINE_RIGHT);
    tfNumOfYears.setAlignment(Pos.BASELINE_RIGHT);
    tfLoanAmount.setAlignment(Pos.BASELINE_RIGHT);
    
    tfLoanAmount.setPrefColumnCount(5);
    tfNumOfYears.setPrefColumnCount(5);
    tfLoanAmount.setPrefColumnCount(5);
            
    BorderPane pane = new BorderPane();
    pane.setCenter(new ScrollPane(ta));
    pane.setTop(gridPane);
    
    // Create a scene and place it in the stage
    Scene scene = new Scene(pane, 400, 250);
    primaryStage.setTitle("Exercise31_01Client"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
    
    
    btSubmit.setOnAction(e -> {
    	try {
	    	//get the annual interest rate from the text field
	    	double annualInterestRate = Double.parseDouble(tfAnnualInterestRate.getText());
	    	//get number of years from the text field
	    	int numberOfYears = Integer.parseInt(tfNumOfYears.getText());
	    	//get loan amount from the text field
	    	double loanAmount = Double.parseDouble(tfLoanAmount.getText());
	    	
	    	//send info to server
	    	toServer.writeDouble(annualInterestRate);
	    	toServer.writeInt(numberOfYears);
	    	toServer.writeDouble(loanAmount);
	    	toServer.flush();
	    	
	    	//get information from server
	    	double monthlyPayment = fromServer.readDouble();
	    	double totalPayment = fromServer.readDouble();
	    	
	    	//display to the text area
	    	ta.appendText("Annual Interest Rate: " + annualInterestRate + '\n');
			ta.appendText("Number of Years: " + numberOfYears + '\n');
			ta.appendText("Loan Amount: " + loanAmount + '\n');
			ta.appendText("Monthly Payment: " + monthlyPayment + '\n');
			ta.appendText("Total Payment: " + totalPayment + '\n');
	    	
    	}
    	catch (IOException ex) {
    		System.err.println(ex);
    	}
    	
    });
    
    try {
    	//create a socket to connect to the server
    	Socket socket = new Socket("localhost", 8000);
    	
    	//create an input strema to receive data from the server
    	fromServer = new DataInputStream(socket.getInputStream());
    	
    	//create an output stream to send data to the server
    	toServer = new DataOutputStream(socket.getOutputStream());
    }
    catch (IOException ex) {
    	ta.appendText(ex.toString() + '\n');
    }
    
  }
  
  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
