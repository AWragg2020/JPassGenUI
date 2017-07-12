import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.datatransfer.*;
import java.awt.Toolkit;

public class PassGenUI extends Application implements EventHandler<ActionEvent> {

	private Button btNewPass, btCpy2Clipboard;
	private Label lb_title, lb_charset, lb_output, lb_entropy, lb_entropyCardinal, lb_signature, lb_length, lb_alsoInclude,lb_settings,lb_X;
	private Label lb_dcb_upper ,lb_dcb_lower ,lb_dcb_digits ,lb_dcb_punctuation ,lb_dcb_special ,lb_dcb_highASCII ,lb_dcb_reps;
	private TextField tf_length, tf_alsoIncluded, tf_output;
	private DarkCheckBox dcb_upper, dcb_lower, dcb_digits, dcb_punctuation, dcb_special, dcb_highASCII, dcb_reps;
	private Stage window;
	private double xOffset=0, yOffset=0;

	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		window = primaryStage;
		window.initStyle(StageStyle.UNDECORATED);
		window.setTitle("JPassGen v1.0.0");
		window.getIcons().add(new Image("icon.png"));

		window.setOnCloseRequest(e -> {
			e.consume();
			closeApp();
		});

		////Initialising stuff

		btNewPass = new Button("New password");
		btCpy2Clipboard = new Button("Copy to clipboard");

		lb_title = new Label("JPassGen v1.0.0");
		lb_charset = new Label("Character set");
		lb_output = new Label("Output");
		lb_entropy = new Label("Entropy: ");
		lb_entropyCardinal = new Label("");
		lb_signature = new Label("JPassGen v1.0.0 by Liam2014 - 2017");
		lb_length = new Label("Length");
		lb_alsoInclude = new Label("Also include");
		lb_settings = new Label("Settings");
		lb_X = new Label("×");

		lb_dcb_upper = new Label("Uppercase letters");
		lb_dcb_lower = new Label("Lowercase letters");
		lb_dcb_digits = new Label("Digits");
		lb_dcb_punctuation = new Label("Punctuation letters");
		lb_dcb_special = new Label("Special characters");
		lb_dcb_highASCII = new Label("High ASCII characters");
		lb_dcb_reps = new Label("Allow repetitions");

		tf_length = new TextField();
		tf_alsoIncluded = new TextField();
		tf_output = new TextField();

		dcb_upper = new DarkCheckBox();
		dcb_lower = new DarkCheckBox();
		dcb_digits = new DarkCheckBox();
		dcb_punctuation = new DarkCheckBox();
		dcb_special = new DarkCheckBox();
		dcb_highASCII = new DarkCheckBox();
		dcb_reps = new DarkCheckBox();

		////Boxes

		HBox hb_close = new HBox();
		hb_close.setAlignment(Pos.CENTER);
		hb_close.getChildren().add(lb_X);

		HBox hb_title = new HBox();
		hb_title.setAlignment(Pos.CENTER_LEFT);
		hb_title.setPadding(new Insets(0,0,0,6));
		hb_title.getChildren().add(lb_title);

		HBox hb_signature = new HBox();
		hb_signature.setAlignment(Pos.CENTER_RIGHT);
		hb_signature.setPadding(new Insets(0,6,4,0));
		hb_signature.getChildren().add(lb_signature);

		VBox vb_length = new VBox(5);
		vb_length.getChildren().addAll(lb_length, tf_length);

		VBox vb_alsoInclude = new VBox(5);
		vb_alsoInclude.getChildren().addAll(lb_alsoInclude, tf_alsoIncluded);

		HBox hb_lb_charset = new HBox();
		hb_lb_charset.getChildren().add(lb_charset);

		HBox hb_lb_settings = new HBox();
		hb_lb_settings.getChildren().add(lb_settings);

		HBox hb_entropy = new HBox();
		hb_entropy.getChildren().addAll(lb_entropy, lb_entropyCardinal);

		VBox vb_output = new VBox(5);
		vb_output.getChildren().addAll(lb_output, tf_output, hb_entropy);

		HBox hb_buttons = new HBox(10);
		hb_buttons.getChildren().addAll(btNewPass, btCpy2Clipboard);

		//hboxes for dark checkboxes

		HBox hb_dcb_upper = new HBox(7);
		hb_dcb_upper.setAlignment(Pos.CENTER_LEFT);
		hb_dcb_upper.getChildren().addAll(dcb_upper, lb_dcb_upper);

		HBox hb_dcb_lower = new HBox(7);
		hb_dcb_lower.setAlignment(Pos.CENTER_LEFT);
		hb_dcb_lower.getChildren().addAll(dcb_lower, lb_dcb_lower);

		HBox hb_dcb_digits = new HBox(7);
		hb_dcb_digits.setAlignment(Pos.CENTER_LEFT);
		hb_dcb_digits.getChildren().addAll(dcb_digits, lb_dcb_digits);

		HBox hb_dcb_punctuation = new HBox(7);
		hb_dcb_punctuation.setAlignment(Pos.CENTER_LEFT);
		hb_dcb_punctuation.getChildren().addAll(dcb_punctuation, lb_dcb_punctuation);

		HBox hb_dcb_special = new HBox(7);
		hb_dcb_special.setAlignment(Pos.CENTER_LEFT);
		hb_dcb_special.getChildren().addAll(dcb_special, lb_dcb_special);

		HBox hb_dcb_highASCII = new HBox(7);
		hb_dcb_highASCII.setAlignment(Pos.CENTER_LEFT);
		hb_dcb_highASCII.getChildren().addAll(dcb_highASCII, lb_dcb_highASCII);

		HBox hb_dcb_reps = new HBox(7);
		hb_dcb_reps.setAlignment(Pos.CENTER_LEFT);
		hb_dcb_reps.getChildren().addAll(dcb_reps, lb_dcb_reps);

		////main bar?

		BorderPane title_close = new BorderPane();
		title_close.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});
		title_close.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				primaryStage.setX(event.getScreenX() - xOffset);
				primaryStage.setY(event.getScreenY() - yOffset);
			}
		});
		title_close.setLeft(hb_title);
		title_close.setRight(hb_close);

		////grids

		BorderPane root = new BorderPane();

		VBox vb_center = new VBox(20);
		vb_center.setPadding(new Insets(20,30,5,30));

		HBox hb_charset_settings = new HBox(40);

		VBox vb_charset = new VBox(15);
		vb_charset.getChildren().addAll(hb_lb_charset, hb_dcb_upper, hb_dcb_lower, hb_dcb_digits, hb_dcb_punctuation, hb_dcb_special, hb_dcb_highASCII);

		VBox vb_settings = new VBox(15);
		vb_settings.getChildren().addAll(hb_lb_settings, hb_dcb_reps, vb_length, vb_alsoInclude);

		hb_charset_settings.getChildren().addAll(vb_charset, vb_settings);
		vb_center.getChildren().addAll(hb_charset_settings, vb_output, hb_buttons);

		root.setTop(title_close);
		root.setCenter(vb_center);
		root.setBottom(hb_signature);

		////styling

		title_close.setStyle("-fx-background-color: #040505;");        
		vb_center.setStyle("-fx-background-color: #0D1215;");
		hb_signature.setStyle("-fx-background-color: #0D1215;");

		lb_title.setStyle("-fx-text-fill: #A1AAAC;"
				+ "-fx-font: 13px \"Century Gothic\";");
		lb_signature.setStyle("-fx-text-fill: #3E4951;"
				+ "-fx-font: 13px \"Century Gothic\";");
		lb_charset.setStyle("-fx-text-fill: #C4A38A;"
				+ "-fx-font: 13px \"Century Gothic\";");
		lb_settings.setStyle("-fx-text-fill: #C4A38A;"
				+ "-fx-font: 13px \"Century Gothic\";");
		lb_length.setStyle("-fx-text-fill: #5A6879;"
				+ "-fx-font: 13px \"Century Gothic\";");
		lb_alsoInclude.setStyle("-fx-text-fill: #5A6879;"
				+ "-fx-font: 13px \"Century Gothic\";");
		lb_output.setStyle("-fx-text-fill: #5A6879;"
				+ "-fx-font: 13px \"Century Gothic\";");
		lb_entropy.setStyle("-fx-text-fill: #5A6879;"
				+ "-fx-font: 13px \"Century Gothic\";");
		lb_entropyCardinal.setStyle("-fx-text-fill: #C4A38A;"
				+ "-fx-font: 13px \"Century Gothic\";");

		lb_dcb_upper.setStyle("-fx-text-fill: #5A6879;"
				+ "-fx-font: 13px \"Century Gothic\";");
		lb_dcb_lower.setStyle("-fx-text-fill: #5A6879;"
				+ "-fx-font: 13px \"Century Gothic\";");
		lb_dcb_digits.setStyle("-fx-text-fill: #5A6879;"
				+ "-fx-font: 13px \"Century Gothic\";");
		lb_dcb_punctuation.setStyle("-fx-text-fill: #5A6879;"
				+ "-fx-font: 13px \"Century Gothic\";");
		lb_dcb_special.setStyle("-fx-text-fill: #5A6879;"
				+ "-fx-font: 13px \"Century Gothic\";");
		lb_dcb_highASCII.setStyle("-fx-text-fill: #5A6879;"
				+ "-fx-font: 13px \"Century Gothic\";");
		lb_dcb_reps.setStyle("-fx-text-fill: #5A6879;"
				+ "-fx-font: 13px \"Century Gothic\";");

		tf_length.setStyle("-fx-text-fill: #A1AAAC;"
				+ "-fx-background-color: #090B0E;"
				+ "-fx-font: 13px \"Century Gothic\";");		
		tf_alsoIncluded.setStyle("-fx-text-fill: #A1AAAC;"
				+ "-fx-background-color: #090B0E;"
				+ "-fx-font: 13px \"Century Gothic\";");		
		tf_output.setStyle("-fx-text-fill: #A1AAAC;"
				+ "-fx-background-color: #090B0E;"
				+ "-fx-font: 15px \"Century Gothic\";");

		btNewPass.setStyle("-fx-text-fill: #5A6879;"
				+ "-fx-background-color: #040505;"
				+ "-fx-font: 13px \"Century Gothic\";");
		btCpy2Clipboard.setStyle("-fx-text-fill: #5A6879;"
				+ "-fx-background-color: #040505;"
				+ "-fx-font: 13px \"Century Gothic\";");		
		hb_close.setStyle("-fx-background-color: #6C0B0F;");
		lb_X.setStyle("-fx-text-fill: #CDCFCC;"
				+ "-fx-font: 18px \"Century Gothic\";"
				+ "-fx-font-weight: bold;");		
		hb_close.setMinWidth(30);
		hb_close.setMaxWidth(30);
		hb_close.setMinHeight(20);
		hb_close.setMinHeight(20);

		////setting fucntionality for stuff

		btNewPass.setOnAction(e -> this.handleOptions(dcb_upper, dcb_lower, dcb_digits, dcb_punctuation, dcb_special, dcb_highASCII, dcb_reps));
		btNewPass.setOnMouseEntered(e -> this.handleChangeColorEntered(btNewPass));
		btNewPass.setOnMouseExited(e -> this.handleChangeColorExited(btNewPass));
		btNewPass.setOnMousePressed(e -> this.handleChangeColorPressed(btNewPass));
		btNewPass.setOnMouseReleased(e -> this.handleChangeColorReleased(btNewPass));

		btCpy2Clipboard.setOnAction(this);
		btCpy2Clipboard.setOnMouseEntered(e -> this.handleChangeColorEntered(btCpy2Clipboard));
		btCpy2Clipboard.setOnMouseExited(e -> this.handleChangeColorExited(btCpy2Clipboard));
		btCpy2Clipboard.setOnMousePressed(e -> this.handleChangeColorPressed(btCpy2Clipboard));
		btCpy2Clipboard.setOnMouseReleased(e -> this.handleChangeColorReleased(btCpy2Clipboard));

		hb_close.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				closeApp();
			}
		});
		hb_close.setOnMouseEntered(e -> handleOverClose(hb_close, lb_X));
		hb_close.setOnMouseExited(e -> handleExitClose(hb_close, lb_X));

		tf_output.setEditable(false);

		dcb_upper.setChecked(true);
		dcb_lower.setChecked(true);
		dcb_digits.setChecked(true);
		dcb_reps.setChecked(true);

		////scene stuff

		Scene mainScene = new Scene(root, 660, 438);
		window.setScene(mainScene);
		window.show();

	}

	@Override
	public void handle(ActionEvent event) {

		String password = tf_output.getText();
		StringSelection stringSelection = new StringSelection(password);
		Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
		clpbrd.setContents(stringSelection, null);
		
	}

	private void handleOptions(DarkCheckBox upper, DarkCheckBox lower, DarkCheckBox digits, DarkCheckBox punctuation, DarkCheckBox special, DarkCheckBox highASCII, DarkCheckBox reps){

		boolean charset[] = new boolean[6];
		charset[0] = upper.isChecked();
		charset[1] = lower.isChecked();
		charset[2] = digits.isChecked();
		charset[3] = punctuation.isChecked();
		charset[4] = highASCII.isChecked();
		charset[5] = special.isChecked();

		Integer length = new Integer(0), int_entropy;
		String s_length = tf_length.getText(), entropy="", pass="";

		if(isInteger(s_length)){

			try{

				length = Integer.parseInt(s_length);

				PassGenCore pgc = new PassGenCore();
				pgc.setAllowReps(reps.isChecked());
				pgc.setAlsoIncluded(tf_alsoIncluded.getText());
				pgc.setLength(length.intValue());
				pass = pgc.generatePassword(charset);
				tf_output.setText(pass);
				int_entropy = new Integer(pgc.retEntropy());
				entropy = int_entropy.toString();
				lb_entropyCardinal.setText(entropy);

				pgc = null;
				length = null;
				s_length = null;
				charset = null;
				int_entropy = null;
				entropy = null;
				System.gc();

			}catch(Exception e){}
		}

		else{
			tf_output.setText("Length was not a number");
		}

	}

	//handlers for colors :D

	private void handleChangeColorEntered(Button button){

		button.setStyle("-fx-background-color: #C4A38A;"
				+"-fx-text-fill: #040505;"
				+ "-fx-font: 13px \"Century Gothic\";");
	}

	private void handleChangeColorExited(Button button){

		button.setStyle("-fx-text-fill: #5A6879;"
				+ "-fx-background-color: #040505;"
				+ "-fx-font: 13px \"Century Gothic\";");
	}

	private void handleChangeColorPressed(Button button){

		button.setStyle("-fx-text-fill: #5A6879;"
				+ "-fx-background-color: #000000;"
				+ "-fx-font: 13px \"Century Gothic\";");
	}

	private void handleChangeColorReleased(Button button){

		button.setStyle("-fx-background-color: #C4A38A;"
				+ "-fx-text-fill: #040505;"
				+ "-fx-font: 13px \"Century Gothic\";");
	}

	private void handleOverClose(HBox hbox, Label label){
		hbox.setStyle("-fx-background-color: #040505;");
	}

	private void handleExitClose(HBox hbox, Label label){
		hbox.setStyle("-fx-background-color: #6C0B0F;");
	}

	private boolean isInteger(String s) {
		return isInteger(s,10);
	}

	private boolean isInteger(String s, int radix) {
		if(s.isEmpty()) return false;
		for(int i = 0; i < s.length(); i++) {
			if(i == 0 && s.charAt(i) == '-') {
				if(s.length() == 1) return false;
				else continue;
			}
			if(Character.digit(s.charAt(i),radix) < 0) return false;
		}
		return true;
	}

	private void closeApp(){
		window.close();
	}

	//// didn't like the default checkboxes javaFX provided, so I made one of my own	
	
	private class DarkCheckBox extends HBox{

		private boolean checked;
		private String whatever;
		private Label x;

		public DarkCheckBox(){

			this.setMaxWidth(21);
			this.setMinWidth(21);
			this.setMaxHeight(21);
			this.setMinHeight(21);

			this.setAlignment(Pos.CENTER);
			
			this.setStyle("-fx-background-color: #040505;"
					+ "-fx-border-radius: 5 5 5 5;"
					+ "-fx-background-radius: 5 5 5 5;");
			
			this.whatever = " ";
			this.x = new Label(whatever);
			
			x.setStyle("-fx-font: 13px \"Century Gothic\";"
					+ "-fx-text-fill: #A1AAAC;"
					+ "-fx-font-weight: bold;");

			this.setOnMouseClicked(e -> click());

			this.checked = false;
			this.getChildren().add(x);
		}

		private void click(){
			checked = !checked;
			this.whatever = checked ? "×" : " ";
			this.x.setText(whatever);
		}

		public void setChecked(boolean in){
			this.checked = in;
			this.whatever = checked ? "×" : " ";
			this.x.setText(whatever);
		}

		public boolean isChecked(){
			return this.checked;
		}
	}
}