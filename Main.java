package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Ellipse;
import javafx.scene.paint.Color;



public class Main extends Application {
	//A label that displays which color's turn it is
	Label playerTurn = new Label();
	//A label that displays game-over if hasConnect() is true
	Label gameover = new Label();
	//Number of token slots in the x direction
	int numWidth;
	//Number of token slots in the y direction
	int numHeight;
	//The pixel width of each token slot
	int side;
	//Minimum number of tokens in a row to win the game
	int numToWin;
	//The Board object which is being displayed
	Board b;
	//A text field in which the player types in the selected column
	TextField column = new TextField();
	//A button that submits the given column number
	Button submit = new Button();
	//Stores the most recently inputed column number
	int enteredColumn;
	//Keeps track of whether it is Blue's or Red's turn
	//Even number: Blue's turn
	//Odd number: Red's turn
	int count = 0;
	Stage primaryStage;
	
	Scene scene;
	//Layout for the tokens and background
	Pane layout;
	
	
	@Override
	public void start(Stage primaryStage) {
		//Initial parameters
		numWidth = 7;
		numHeight = 6;
		side = 75;
		numToWin = 4;
		//Initialize Variables parameters
		this.primaryStage = primaryStage;
		b = new Board(numWidth, numHeight,numToWin);
		layout = new Pane();
		layout.setStyle("-fx-background-color: yellow");
		submit.setText("Enter Column:");
		playerTurn.setText("Blue's Turn");
		submit.setOnAction(e -> {
			String temp = column.getText();
			enteredColumn = (int) Double.parseDouble(temp);
			enteredColumn--;
			if (0 <= enteredColumn && enteredColumn < numWidth && b.hasFreeSpaceY(enteredColumn) == true ) {
				if ( count % 2 == 0 ) {
					b.insertToken(enteredColumn, java.awt.Color.BLUE);
					playerTurn.setText("Red's Turn");
				}
				else {
					b.insertToken(enteredColumn, java.awt.Color.RED);
					playerTurn.setText("Blue's Turn");
				}
				paint();
				if ( b.hasConnect( b.latestToken.coordinates) == true )  {
					submit.setDisable(true);
					playerTurn.setText("");
					column.setText("");
					gameoverWindow();
					
				}
				count++;
				
			}
		});
		HBox top = new HBox(10);
		top.setAlignment(Pos.TOP_CENTER);
		top.getChildren().addAll(gameover, submit, column, playerTurn);
		layout.getChildren().addAll(top);
		layout.setPrefSize(numWidth*side, (numHeight+1)*side);
		scene = new Scene(layout);
		paint();
		//Finalizing window
		this.primaryStage.setTitle("Connect Four!");
		this.primaryStage.setScene(scene);
		this.primaryStage.show();
		
	}
	public static void main(String[] args) {
		launch(args);
	}
	public javafx.scene.paint.Color toFxColor( java.awt.Color c ) {
		int red, green, blue, a;
		double opacity;
		red = c.getRed();
		green = c.getGreen();
		blue = c.getBlue();
		a = c.getAlpha();
		opacity = a/ 255.0;
		return javafx.scene.paint.Color.rgb(red, green, blue, opacity);
	}
	public void paint() {
		Ellipse ellipse;
		for ( int i = 0 ; i < numWidth ; i++) {
			for ( int j = 0 ; j < numHeight ; j++) {
				ellipse = new Ellipse(i*side + (int) (side*1.75/4.0) ,-j*side+(numHeight)*side + (int) (side*1.75/4.0),  (side*1.75/4.0), (side*1.75/4.0));
				ellipse.setStroke(Color.YELLOW);
				if ( b.tokenBoard.get(new Point(i,j)) == null) {
					ellipse.setFill( Color.WHITE );
				}
				else {
					ellipse.setFill(toFxColor(b.tokenBoard.get(new Point(i,j)).color ));
				}
				layout.getChildren().add(ellipse);
			}
		}
	}
	public void gameoverWindow() {
		Stage gmWindow = new Stage();
		gmWindow.initModality(Modality.APPLICATION_MODAL);
		gmWindow.setTitle("Gameover Window");
		VBox layout2 = new VBox(20);
		layout2.setAlignment(Pos.CENTER);
		String s = "";
		if ( count % 2 == 0) {
			s+= "Blue Wins";
		}
		else {
			s+= "Red Wins";
		}
		Label gm = new Label(s);
		
		Button again = new Button();
		again.setText("Click to Play Again");
		again.setOnAction(e -> {
			b = new Board(numWidth, numHeight,numToWin);
			count=-1;		
			playerTurn.setText("Blue's Turn");
			submit.setDisable(false);
			column.setText("");
			gmWindow.close();
			paint();
		});
		Button closeAll = new Button();
		closeAll.setText("Click to Exit");
		closeAll.setOnAction(e-> {gmWindow.close(); primaryStage.close();} );
		layout2.getChildren().addAll(gm,again,closeAll);
		Scene sc = new Scene(layout2, 300,200);
		gmWindow.setScene(sc);
		gmWindow.showAndWait();
	}
}

