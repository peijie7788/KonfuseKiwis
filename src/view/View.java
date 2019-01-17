package view;

import java.util.Observable;
import java.util.Observer;

import controller.ButtonMessageSendListener;
import controller.ChatController;
import controller.ConnectController;
import controller.Controller;
import controller.EnterMessageSendListener;
import controller.MouseController;
import controller.TextController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.BreakOut;
import model.CustomObservable;
import model.bonus.Bonus;

/**
 * @author Niklas und Peijie
 * 
 * Die Klasse View ist für das Anzeigen aller Bestandteile zuständig. 
 */
public class View extends Observable implements Observer{
	
	private Pane paneAll;
	private Pane panePlay;
	private HBox hbox; 
	private HBox stringHBox;
	private Text losetext;
	private Text wintext;
	private Label points;
	private Label lives;
	private Button sendButton;
	private Scene scene;
	private BreakOut breakOut;
	private Pane chat;
	private FlowPane chatinput;
	private TextField inputTextfield;
	private CustomObservable co;
	private TextArea textArea;
	private Pane connectionPane;
	private FlowPane ipFlowPane;
	private FlowPane portFlowPane;
	private Label ipLabel;
	private Label portLabel;
	private TextField ipTextfield;
	private TextField portTextfield;
	private Button connectButton;
	
	
	/**
	 * Der Konstruktor: er ruft alle Initialisierungsmethoden auf und fügt allen Vaterknoten all ihre Kinder hinzu,
	 *  mit dem root, der primaryStage.
	 * @param primaryStage ist der oberste Vaterknoten, ihr werden alle anderen sichtbaren Elemente als Kind hinzugefügt.
	 * @param breakOut Klasse mit wichtigen Parametern und Objekten auf die die View zugreifen können muss.
	 * @throws Exception wenn etwas schiefgeht wird eine Exception geworfen.
	 */
	public View(Stage primaryStage, BreakOut breakOut) throws Exception{
		   
		 this.breakOut = breakOut;
		 
		 //Pane in der alle anderen Elemente enthalten sind
		 paneAll = new Pane();
		
		 //Hbox ist die Box am oberen Rand mit Punkten und Leben
		 initHbox(breakOut);
		 
		 //PanePlay ist der Spielbereich 
		 initPanePlay(breakOut);
		 		 
		 //HBox fuer Strings wie GAME OVER oder YOU WIN
		 initstringHBox();
		 
		 //Chatfenster an der recheten seite des Spiels
		 initChat(breakOut);
		 
		 //Pane für das verbinden zu einem Server mit IP-Adresse und Port
		 initConnectionPane();
		 initIpFlowPane();
		 initPortFlowPane();
		 
		 initIpLabel();
		 initPortLabel();
		 initIpTextField(breakOut);
		 initPortTextfield(breakOut);
		 initConnectButton(breakOut);
		 
		 initTextArea(breakOut);
		 initChatinput(breakOut);
		 
         initInputTextfield();
		 initSendButton();
		 
		 //Textfeld fuer GAME OVER
		 initLosetext();
		 //Textfeld fuer YOU WIN
		 initWintext();
		 
		 initPoints();
		 initLives();
		 
		 initScene(breakOut);
		 
		 getChildrenAll(breakOut, breakOut.getValueFieldPoints(), breakOut.getValueFieldLives());

	     primaryStage.setResizable(false);
	     primaryStage.setScene(scene);
	     primaryStage.setTitle("Breakout");
	     primaryStage.show();
	    }


	/**
	 * Die update Methode wird aufgerufen wenn der Observable den Observer, also in diesem Fall die View notifiziert
	 * ,dass es eine Anderung in der Observabele Klasse gab. Somit wird auf die Veränderungen reagiert und die entsprechenden
	 * Anpasssungen werden je nach Aenderung vorgenommen.
	 */
	@Override
	public void update(Observable o, Object arg) {
		
		if(o instanceof Bonus){
			Bonus.ChangeType changeType = (Bonus.ChangeType) arg;
			
			switch(changeType){
				case CREATE:{
					Bonus objectBonus = (Bonus)o;
					ShapeBonus shapeBonus = new ShapeBonus(objectBonus);
					shapeBonus.setTranslateX(objectBonus.getxCoord());
					shapeBonus.setTranslateY(objectBonus.getyCoord());
					objectBonus.getTimerBonus().setShapeBonus(shapeBonus);
					
					this.panePlay.getChildren().add(shapeBonus);
					break;
				}
				
				case DELETE:{
					Bonus objectBonus = (Bonus)o;
					this.panePlay.getChildren().remove(objectBonus.getTimerBonus().getShapeBonus());
					break;
				}
				default:
					break;
			
			}
			
		}
		else{
			co = (CustomObservable) o;
			if(co.getBrickValue() == 0) {
				this.panePlay.getChildren().remove(co.getBrick());
			}
			if(co.getBrickType() == 'G' ) {
				System.out.println("Zombie");
				this.panePlay.getChildren().remove(co.getBrick());
			}
		}
	}

	/**
	 * In dieser Methode werden die sichtbaren Elemente dem entsprechenden Vaterknoten als Kind(Children) 
	 * an die Liste angehängt bzw. mitgegeben.
	 * @param breakOut Klasse die mitgegeben wird, um auf spezielle Objekte oder Parameter mit Gettern und Settern zuzugreifen.
	 * @param valueFieldPoints ist ein Label der Punkte, dass von der View observiert wird. 
	 * Um dieses als Kindknoten anzuhängen muss es mit übergeben werden
	 * @param valueFieldLives ist ein Label der Leben, dass von der View observiert wird.
	 * Um dieses als Kindknoten anzuhängen muss es mit übergeben werden
	 */
	private void getChildrenAll(BreakOut breakOut, CountViewPoints valueFieldPoints, CountViewLives valueFieldLives) {
		for(int i=0;i<breakOut.getBrickCount();i++) {
			panePlay.getChildren().add(breakOut.getBricks().get(i));
		}
		chat.getChildren().addAll(connectionPane, chatinput, textArea);
		connectionPane.getChildren().addAll(ipFlowPane, portFlowPane);
		ipFlowPane.getChildren().addAll(ipLabel, ipTextfield);
		portFlowPane.getChildren().addAll(portLabel, portTextfield, connectButton);
		chatinput.getChildren().addAll(inputTextfield, sendButton);
		hbox.getChildren().addAll(points, valueFieldPoints, lives, valueFieldLives);
		panePlay.getChildren().addAll(breakOut.getBat(), breakOut.getBall(), stringHBox);
	    paneAll.getChildren().addAll(hbox, panePlay, chat);
	}

	/**
	 * Initialisiert das Textfield, dass für die Porteingaben zum Verbinden mit einem Server benötigt wird. 
	 * @param breakOut Klasse die mitgegeben wird, um auf spezielle Objekte oder Parameter mit Gettern und Settern zuzugreifen.
	 */
	private void initPortTextfield(BreakOut breakOut) {
		 portTextfield = new TextField("Port eingeben");
		 portTextfield.setEditable(false);
		 portTextfield.setDisable(true);
		 portTextfield.setOnMouseClicked(new TextController<MouseEvent>(breakOut, portTextfield));
		 portTextfield.setMaxWidth(100);
		 portTextfield.setMinWidth(100);
	}

	/**
	 * Initialisiert das Textfield, dass für die IP-Adresseneingaben zum Verbinden mit einem Server benötigt wird.
	 * @param breakOut Klasse die mitgegeben wird, um auf spezielle Objekte oder Parameter mit Gettern und Settern zuzugreifen.
	 */
	private void initIpTextField(BreakOut breakOut) {
		 ipTextfield = new TextField("ip Adresse eingeben");
		 ipTextfield.setEditable(false);
		 ipTextfield.setDisable(true);
		 ipTextfield.setOnMouseClicked(new TextController<MouseEvent>(breakOut, ipTextfield));
	}

	/**
	 * Initialisiert das Label für den Port
	 */
	private void initPortLabel() {
		 portLabel = new Label("PORT:");
		 portLabel.setPadding(new Insets(0,0,0,10));
	}

	/**
	 * Initialisiert das Label für die IP-Adresse
	 */
	private void initIpLabel() {
		 ipLabel = new Label("IP-Adress:");
		 ipLabel.setPadding(new Insets(0,6,0,10));
	}

	/**
	 * Initialisiert eine FlowPane, welche alle Elemente der Zeile für die Porteingabe enthält.
	 */
	private void initPortFlowPane() {
		 portFlowPane = new FlowPane(20.0, 1.0);
		 portFlowPane.setTranslateX(1);
		 portFlowPane.setTranslateY(35);
		 portFlowPane.setMaxWidth(298);
		 portFlowPane.setMaxHeight(35);
		 portFlowPane.setMinWidth(298);
		 portFlowPane.setMinHeight(35);
		 portFlowPane.setPadding(new Insets(2,0,0,0));
	}

	/**
	 * Initialisiert eine FlowPane, die alle Elemente der Zeile, für die IP-Adresseneingabe enthält.
	 */
	private void initIpFlowPane() {
		 ipFlowPane = new FlowPane(20.0, 1.0);
		 ipFlowPane.setTranslateX(1);
		 ipFlowPane.setTranslateY(0);
		 ipFlowPane.setMaxWidth(298);
		 ipFlowPane.setMaxHeight(35);
		 ipFlowPane.setMinWidth(298);
		 ipFlowPane.setMinHeight(35);
		 ipFlowPane.setPadding(new Insets(3,0,0,0));
	}

	/**
	 * Initialisiert eine Pane, die jewils die FlowPane für die Ip-Adresse und den Port enthält. 
	 */
	private void initConnectionPane() {
		 connectionPane = new Pane();
		 connectionPane.setTranslateX(1);
		 connectionPane.setTranslateY(0);
		 connectionPane.setMaxWidth(298);
		 connectionPane.setMaxHeight(70);
		 connectionPane.setMinWidth(298);
		 connectionPane.setMinHeight(70);
	}

	/**
	 * Initialisiert einen Button, der den Nutzer bei richtiger Port und IP-Adresse mit einem Server verbindet.
	 * @param breakOut Klasse die mitgegeben wird, um auf spezielle Objekte oder Parameter mit Gettern und Settern zuzugreifen.
	 */
	private void initConnectButton(BreakOut breakOut) {
		 connectButton = new Button("Connect");
		 connectButton.setDisable(true);
		 connectButton.setMaxWidth(100);
		 connectButton.setMinWidth(100);
		 connectButton.setMaxHeight(30);
		 connectButton.setMinHeight(30);
		 connectButton.setOnMouseClicked(new ConnectController<MouseEvent>(breakOut));
	}

	/**
	 * Initialisiert die TextArea, welche den Chatinhalt anzeigt.
	 * @param breakOut Klasse die mitgegeben wird, um auf spezielle Objekte oder Parameter mit Gettern und Settern zuzugreifen.
	 */
	private void initTextArea(BreakOut breakOut) {
		 textArea = new TextArea();
		 textArea.setEditable(false);
		 textArea.setDisable(true);
		 textArea.setTranslateX(1);
		 textArea.setTranslateY(70);
		 textArea.setMaxWidth(298);
		 textArea.setMaxHeight((breakOut.getRowCounter()+breakOut.getLowerSpace()+1)*breakOut.getBrickHeight() - 30);
		 textArea.setMinWidth(298);
		 textArea.setMinHeight((breakOut.getRowCounter()+breakOut.getLowerSpace()+1)*breakOut.getBrickHeight() - 30);
	}

	/**
	 * Initialisiert einen Button, der zum verschicken von Nachrichten an den Chat benutzt wird.
	 */
	private void initSendButton() {
		 sendButton = new Button("Send");
		 sendButton.setDisable(true);
		 sendButton.setMaxWidth(100);
		 sendButton.setMinWidth(100);
		 sendButton.setMaxHeight(30);
		 sendButton.setMinHeight(30);
		 sendButton.setOnMouseClicked(new ButtonMessageSendListener<MouseEvent>(breakOut));
	}

	/**
	 * Initialisiert ein Textfield, dass zum Eingeben für Nachrichten an den Chat benutzt wird.
	 */
	private void initInputTextfield() {
		 inputTextfield = new TextField("Eingabe");
		 inputTextfield.setPadding(new Insets(0,0,5,5));
		 inputTextfield.setStyle("-fx-background-color: #FAFAFA;");
		 inputTextfield.setStyle("-fx-border-width: 5.0");
		 inputTextfield.setStyle("-fx-border-color: #151515");
		 inputTextfield.setEditable(false);
		 inputTextfield.setDisable(true);
		 inputTextfield.setOnMouseClicked(new TextController<MouseEvent>(breakOut, inputTextfield));
		 inputTextfield.setMaxWidth(200);
		 inputTextfield.setMinWidth(200);
		 inputTextfield.setMaxHeight(30);
		 inputTextfield.setMinHeight(30);
	}

	/**
	 * Initialisiert eine FlowPane, die das Textfield und den Button zum schrieben und verschicken von Nachrichten enthält. 
	 * @param breakOut Klasse die mitgegeben wird, um auf spezielle Objekte oder Parameter mit Gettern und Settern zuzugreifen.
	 */
	private void initChatinput(BreakOut breakOut) {
		 chatinput = new FlowPane();
		 chatinput.setTranslateX(0.0);
		 chatinput.setTranslateY((breakOut.getRowCounter()+breakOut.getLowerSpace()+1)*breakOut.getBrickHeight() + 40);
		 chatinput.setMaxWidth(300);
		 chatinput.setMaxHeight(30);
		 chatinput.setMinWidth(300);
		 chatinput.setMinHeight(30);
		 chatinput.setOnKeyPressed(new EnterMessageSendListener<KeyEvent>(breakOut));
	}

	/**
	 * Initialisiert eine Pane, die alle Elemente für den Chat enthält.
	 * @param breakOut Klasse die mitgegeben wird, um auf spezielle Objekte oder Parameter mit Gettern und Settern zuzugreifen.
	 */
	private void initChat(BreakOut breakOut) {
		 chat = new Pane();
		 chat.setStyle("-fx-background-color: #FAFAFA;");
		 chat.setStyle("-fx-border-width: 5.0");
		 chat.setStyle("-fx-border-color: #151515");
		 chat.setOnMouseEntered(new ChatController<MouseEvent>(breakOut));
		 chat.setOnMouseExited(new ChatController<MouseEvent>(breakOut));
		 chat.setTranslateX(breakOut.getColumnCounter()*breakOut.getBrickWidth());
		 chat.setTranslateY(0.0);
		 chat.setMaxWidth(300);
		 chat.setMaxHeight((breakOut.getRowCounter()+breakOut.getLowerSpace()+1)*breakOut.getBrickHeight() + 80);
		 chat.setMinWidth(300);
		 chat.setMinHeight((breakOut.getRowCounter()+breakOut.getLowerSpace()+1)*breakOut.getBrickHeight() + 80);
	}
	
    /**
     * Initialisiert das Label, welches die derzeitigen Leben anzeigt.
     */
	private void initLives() {
		lives = new Label("Leben: ");
		lives.setFont(Font.font(40));
		lives.setTextFill(Color.DARKBLUE);
	}

	/**
	 * Initialisiert das Label, welches die derzeitigen Punkte anzeigt.
	 */
	private void initPoints() {
	    points = new Label("Punkte: ");
	    points.setFont(Font.font(40));
		points.setTextFill(Color.DARKBLUE);
		points.setPadding(new Insets(0,0,10,0));
	}

	/**
	 * Initialisiert einen Text "YOU WIN", der beim gewinnen des Spiels angezeigt wird.
	 */
	private void initWintext() {
		wintext = new Text("  YOU WIN");
		wintext.setFont(Font.font(100.0));
	}

	/**
	 * Initialisiert Text "GAME OVER", der beim verlieren des Spiels angezeigt wird.
	 */
	private void initLosetext() {
		losetext = new Text("GAME OVER");
		losetext.setFont(Font.font(100.0));
	}

	/**
	 * Initialisiert eine Hbox, die den Win- und Losetext enthält.
	 */
	private void initstringHBox() {
		stringHBox = new HBox();
		stringHBox.setTranslateX(breakOut.getColumnCounter()*breakOut.getBrickWidth() - (0.5* breakOut.getColumnCounter()*breakOut.getBrickWidth()) - 290);
		stringHBox.setTranslateY((breakOut.getRowCounter()+breakOut.getLowerSpace())*breakOut.getBrickHeight() - 150);
	}

	/**
	 * Initialisiert eine HBox, die die Elemente wie Punkte, Leben und deren Labels enthält.
	 * @param breakOut Klasse die mitgegeben wird, um auf spezielle Objekte oder Parameter mit Gettern und Settern zuzugreifen.
	 */
	private void initHbox(BreakOut breakOut) {
		hbox = new HBox(40);
		hbox.setStyle("-fx-background-color: #FAFAFA;");
		hbox.setStyle("-fx-border-width: 5.0");
		hbox.setStyle("-fx-border-color: #151515");
		hbox.setTranslateX(0);
		hbox.setTranslateY(0);
		hbox.setMaxWidth(breakOut.getColumnCounter()*breakOut.getBrickWidth());
		hbox.setMaxHeight(70);
		hbox.setMinWidth(breakOut.getColumnCounter()*breakOut.getBrickWidth());
		hbox.setMinHeight(70);
		hbox.setPadding(new Insets(10,10,10,10));
	}

	/**
	 * Initialisiert eine Pane, welche den kompletten Spielinhalt wie Bricks, Schläger und Ball enthält.
	 * @param breakOut Klasse die mitgegeben wird, um auf spezielle Objekte oder Parameter mit Gettern und Settern zuzugreifen.
	 */
	private void initPanePlay(BreakOut breakOut) {
		panePlay = new Pane();
		panePlay.setStyle("-fx-background-color: #336699;");
		panePlay.setPadding(new javafx.geometry.Insets(0, 10, 0, 0));
		panePlay.setTranslateX(0);
		panePlay.setTranslateY(70);
		panePlay.setMaxWidth(breakOut.getColumnCounter()*breakOut.getBrickWidth());
		panePlay.setMaxHeight((breakOut.getRowCounter()+breakOut.getLowerSpace()+1)*breakOut.getBrickHeight() + 10);
		panePlay.setMinWidth(breakOut.getColumnCounter()*breakOut.getBrickWidth());
		panePlay.setMinHeight((breakOut.getRowCounter()+breakOut.getLowerSpace()+1)*breakOut.getBrickHeight() + 10);
	}

	/**
	 * Initialisiert die Scene. Außerdem werden hier die EventHandle für bestimmte Mouse oder Keyevents mit der Scene erstellt.
	 * @param breakOut Klasse die mitgegeben wird, um auf spezielle Objekte oder Parameter mit Gettern und Settern zuzugreifen.
	 */
	private void initScene(BreakOut breakOut) {
		scene = new Scene(paneAll, breakOut.getColumnCounter()*breakOut.getBrickWidth() + 295, (breakOut.getRowCounter()+breakOut.getLowerSpace()+1)*breakOut.getBrickHeight() + 70 );
	    scene.setOnKeyPressed(new Controller<KeyEvent>(breakOut));
	    scene.setOnKeyReleased(new Controller<KeyEvent>(breakOut));
	 	scene.setOnMouseMoved(new MouseController<MouseEvent>(breakOut));
	 	scene.setOnMouseExited(new MouseController<MouseEvent>(breakOut));
	 	scene.setOnMouseClicked(new MouseController<MouseEvent>(breakOut));
	 	
	}

	public Pane getPanePlay() {
		return panePlay;
	}

	public void setPanePlay(Pane panePlay) {
		this.panePlay = panePlay;
	}
	
	public void setStringHbox(Text text) {
		stringHBox.getChildren().add(text);
	}
	
	public Text getLosetext() {
		return losetext;
	}
	
	public Text getWintext() {
		return wintext;
	}

	public TextField getInputTextfield() {
		return inputTextfield;
	}

	public void setInputTextfield(TextField inputTextfield) {
		this.inputTextfield = inputTextfield;
	}

	public Pane getChat() {
		return chat;
	}

	public void setChat(Pane chat) {
		this.chat = chat;
	}

	public TextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(TextArea textArea) {
		this.textArea = textArea;
	}

	public TextField getIpTextfield() {
		return ipTextfield;
	}

	public void setIpTextfield(TextField ipTextfield) {
		this.ipTextfield = ipTextfield;
	}

	public TextField getPortTextfield() {
		return portTextfield;
	}

	public void setPortTextfield(TextField portTextfield) {
		this.portTextfield = portTextfield;
	}

	public Button getSendButton() {
		return sendButton;
	}

	public void setSendButton(Button sendButton) {
		this.sendButton = sendButton;
	}

	public Button getConnectButton() {
		return connectButton;
	}

	public void setConnectButton(Button connectButton) {
		this.connectButton = connectButton;
	}
	
}