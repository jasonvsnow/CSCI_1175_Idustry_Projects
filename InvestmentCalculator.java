package exercise31_17;



import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


/*
 * Author: Jason Snow
 * Date: 04/20/2021
 * 
 * This program has the user entire an investment amount, the number of years to let the investment grow, and the 
 * annual interest rate the investment will grow by and give them the future value for that investment after the 
 * number of specified years. It also has a menu by which they may exit the program.
 */
public class InvestmentCalculator extends Application {

private TextField tfInvestmentAmount = new TextField();
private TextField tfNumOfYears = new TextField();
private TextField tfAnnualInterestRate = new TextField();
private TextField tfFutureValue = new TextField();
private Button btCalculate = new Button("Calculate");


@Override // Override the start method in the Application class
public void start(Stage primaryStage) {
	tfFutureValue.setEditable(false);
	
	VBox pane = new VBox();
	GridPane gridPane = new GridPane();
	
	MenuBar menuBar = new MenuBar();
	Menu menuOperation = new Menu("Operation");
	MenuItem exit = new MenuItem("Exit");
	menuOperation.getItems().addAll(new MenuItem("Calcuate"), exit);
	menuBar.getMenus().add(menuOperation);
	
	exit.setOnAction(e -> {
		System.exit(0);
	});
	
	pane.getChildren().addAll(menuBar, gridPane);
	
	Label lblInvestmentAmount = new Label("Investment Amount:   ");
	Label lblNumOfYears = new Label("Number of Years:   ");
	Label lblAnnualInterestRate = new Label("Annual Interest Rate:   ");
	Label lblFutureValue = new Label("Future Value:   ");
	
	gridPane.add(lblInvestmentAmount, 0, 1);
	gridPane.add(lblNumOfYears, 0, 2);
	gridPane.add(lblAnnualInterestRate, 0, 3);
	gridPane.add(lblFutureValue, 0, 4);
	
	gridPane.add(tfInvestmentAmount, 2, 1);
	gridPane.add(tfNumOfYears, 2, 2);
	gridPane.add(tfAnnualInterestRate, 2, 3);
	gridPane.add(tfFutureValue, 2, 4);
	
	gridPane.add(btCalculate, 2, 5);
	GridPane.setHalignment(btCalculate, HPos.RIGHT);
	
	tfInvestmentAmount.setAlignment(Pos.BASELINE_RIGHT);
	tfNumOfYears.setAlignment(Pos.BASELINE_RIGHT);
	tfAnnualInterestRate.setAlignment(Pos.BASELINE_RIGHT);
	tfFutureValue.setAlignment(Pos.BASELINE_RIGHT);
	

	

	btCalculate.setOnAction(e -> {
		double investmentAmount = Double.parseDouble(tfInvestmentAmount.getText());
		int numberOfYears = Integer.parseInt(tfNumOfYears.getText());
	  	double annualInterestRate = Double.parseDouble(tfAnnualInterestRate.getText());
    	
	  	double futureValue = investmentAmount * Math.pow((1 + (annualInterestRate/12)/100), (numberOfYears*12));
	  	double roundOff = Math.round(futureValue * 100.00) / 100.00;
	  	String s = String.format("$%.2f", roundOff);
	  	tfFutureValue.setText(s);
	});

 
 // Create a scene and place it in the stage
 Scene scene = new Scene(pane, 308, 180);
 primaryStage.setTitle("Exercise31_17"); // Set the stage title
 primaryStage.setScene(scene); // Place the scene in the stage
 primaryStage.show(); // Display the stage
 
 
 

 
}

/**
* The main method is only needed for the IDE with limited
* JavaFX support. Not needed for running from the command line.
*/
public static void main(String[] args) {
 launch(args);
}
}
