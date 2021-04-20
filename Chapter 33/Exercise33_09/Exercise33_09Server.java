package exercise33_09;



import java.io.*;
import java.net.*;
import java.util.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.application.Platform;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/*
 * Author: Jason Snow
 * Date: 04/20/2021
 * 
 * This program was edited to act as a server between two programs that act enables two users 
 * to chat.
 */

public class Exercise33_09Server extends Application {
  private TextArea taServer = new TextArea();
  private TextArea taClient = new TextArea();
  private DataInputStream fromClient = null;
  private DataOutputStream toClient = null;
 
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    taServer.setWrapText(true);
    taClient.setWrapText(true);
    taServer.setEditable(false);

    BorderPane pane1 = new BorderPane();
    pane1.setTop(new Label("History"));
    pane1.setCenter(new ScrollPane(taServer));
    BorderPane pane2 = new BorderPane();
    pane2.setTop(new Label("New Message"));
    pane2.setCenter(new ScrollPane(taClient));
    
    VBox vBox = new VBox(5);
    vBox.getChildren().addAll(pane1, pane2);

    // Create a scene and place it in the stage
    Scene scene = new Scene(vBox, 200, 200);
    primaryStage.setTitle("Exercise31_09Server"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage

    // To complete later
    new Thread(() -> {
    	try {
    		ServerSocket serverSocket = new ServerSocket(8000);
    		 
    		Socket socket = serverSocket.accept();
    		 
        	fromClient = new DataInputStream(socket.getInputStream());
        	
        	toClient = new DataOutputStream(socket.getOutputStream());
        	
	    	while (true) {
				//receive messages from client
				String message = fromClient.readUTF();
				
				Platform.runLater(() -> {
					taServer.appendText("C: " + message + '\n');
				});
	    	}
    	}
    	catch (IOException ex) {
    		taClient.appendText(ex.toString() + '\n');
    	}
    }).start();
    
    taClient.setOnKeyPressed(new EventHandler<KeyEvent>(){
    	public void handle(KeyEvent e){
    		if (e.getCode() == KeyCode.ENTER) {
    			try {
     				String message = taClient.getText().trim();
    				toClient.writeUTF(message);
    				toClient.flush();
    				taClient.setText("");	
    				taServer.appendText("S: " + message + "\n");
    			}
    			catch (IOException ex){
    				
    			}
    		}
    	}
    });
    	
   


  }
  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
