package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import controller.BallRegression;
import controller.BallTimer;
import controller.BatTimer;
import controller.BatTimerMouse;
import controller.BonusDecayTimer;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.bonus.Bonus;
import model.bonus.LargerBall;
import model.bonus.NarrowerPad;
import model.bonus.PenetrationBall;
import model.bonus.SlowerBall;
import model.bonus.SmallerBall;
import model.bonus.SwifterBall;
import model.bonus.WiderPad;
import model.brick.Brick;
import model.brick.BrickA;
import model.brick.BrickB;
import model.brick.BrickC;
import model.brick.BrickD;
import model.brick.BrickE;
import model.brick.BrickF;
import model.brick.BrickG;
import model.brick.BrickZ;
import server.Client;
import view.BrickView;
import view.CountViewLives;
import view.CountViewPoints;

import view.View;

public class BreakOut extends Application implements Observable {


	/**
	 * Liste von Bricks
	 */
	private List<Brick> bricks;
	private Bat bat;
	private Ball ball;
	private BallRegression ballRegression;
	private BallTimer timerBall;
	private BatTimer timerBat;
	private BatTimerMouse timerBatMouse;
	private Timeline timeline;

	private BonusDecayTimer bonusDecayTimer;
	private int lowerSpace;
	private int columnCounter;
	private int rowCounter;
	private int brickCount;
	private View view;
	private BrickView brickView;

	private CustomObservable cObservable;
	private CounterPoints counterPoints;
	private CounterLives counterLives;
	private CountViewLives valueFieldLives;
	private CountViewPoints valueFieldPoints;
	private Client client;

	private boolean started;
	
	private double brickWidth = 50;
	private double brickHeight = 30;

	@Override
	public void start(Stage primaryStage) throws Exception {

		cObservable = new CustomObservable();

		counterPoints = new CounterPoints();
		counterLives = new CounterLives();
		valueFieldPoints = new CountViewPoints();
		valueFieldLives = new CountViewLives();
		counterPoints.addObserver(valueFieldPoints);
		counterLives.addObserver(valueFieldLives);

		bricks = new ArrayList<>();
		readFile(primaryStage);
		
		


		bat = new Bat(this);
		ball = new Ball(this);
		ball.setSticked(true);

		timerBall();

		timerBat = new BatTimer(this);
		timerBatMouse = new BatTimerMouse(this);
		ballRegression = new BallRegression(this);

		setTimeline(new Timeline());
		brickView = new BrickView();

		view = new View(primaryStage, this);
		cObservable.addObserver(view);

		client = new Client(this);

	}

	/**
	 * kontrolliert, ob das Spiel verloren ist. Wenn die der Fall ist, zeige Text
	 * "GAME OVER" im Spielfenster an. Ausserdem wird eine Nachricht an den Server gesendet, 
	 * wenn dieser Client mit dem Server verbunden ist.
	 * Die Methode wird aufgerufen, wenn ein Ball aus dem Spielfenster runterfaellt
	 */
	public void checkGameOver() {
		if (getCounterLives().getLivesCounter() <= 0) {
			getView().setStringHbox(getView().getLosetext());

			if (client.isConnected()) {
				client.getWriter().println("!@#$LOSE%^&*" + this.getCounterPoints().getPointsCounter());
				client.getWriter().flush();
			}

		} else {
			ball = new Ball(this);
			ball.setSticked(true);
			view.getPanePlay().getChildren().addAll(ball);
			timerBall();

		}
	}

	/**
	 * kontrolliert, ob das Spiel gewonnen ist. Wenn die der Fall ist, zeigt Text
	 * "YOU WIN" im Spielfenster an. Ausserdem wird eine Nachricht an den Server gesendet, 
	 * wenn dieser Client mit dem Server verbunden ist.
	 * Die Methode wird aufgerufen, wenn ein Brick zerstoert wird
	 * 
	 * @param bricks
	 */
	public void checkWin(List<Brick> bricks) {
		int counter = 0;
		for (Brick brick : bricks) {
			counter += brick.getRemovedCounter();
			if (brick.isUnDestroyable()) {
				counter -= 8;
			}
			if (brick.isZombieBrick()) {
				counter -= 7;
			}
		}
		if (counter == 0) {
			getView().getPanePlay().getChildren().remove(ball);
			getView().setStringHbox(getView().getWintext());
			getTimerBall().stop();

			if (client.isConnected()) {
				client.getWriter().println("!@#$WIN%^&*" + this.getCounterPoints().getPointsCounter());
				client.getWriter().flush();
			}

		}
	}

	/**
	 * Startet eine PauseTransition die nach 3 Sekunden den entfernten Zombiebrick 
	 * der View erneut hinzufuegt.
	 * @param brick ist der mitgegebene Zombiebrick.
	 */
	public void zombieBrickDelay(Brick brick) {
		PauseTransition delay = new PauseTransition(Duration.seconds(3));
		delay.setOnFinished(event -> {
			getView().getPanePlay().getChildren().add(brick);
			brick.setRemovedCounter(8);
		});
		delay.play();
	}

	/**
	 * versucht mit 10% Wahrscheinlichkeit einen Bonus zu erzeugen, 
	 * jedem Bonus wird dann mit gewissen Wahrscheinlichkeit ein Bonustyp zugewiesen
	 * @param sourceBrick
	 */
	public void createBonus(Brick sourceBrick) {

		double randomNumber = Math.random();
		if (randomNumber < 0.1) {

			// Bonus bonus = new WiderPad(sourceBrick,this);

			double randomNumber2 = Math.random();
			if (randomNumber2 < 0.15) {
				Bonus bonus = new SmallerBall(sourceBrick, this);
			} else if (randomNumber2 >= 0.15 && randomNumber < 0.3) {
				Bonus bonus = new LargerBall(sourceBrick, this);
			} else if (randomNumber2 >= 0.3 && randomNumber < 0.45) {
				Bonus bonus = new SwifterBall(sourceBrick, this);
			} else if (randomNumber2 >= 0.45 && randomNumber < 0.6) {
				Bonus bonus = new SlowerBall(sourceBrick, this);
			} else if (randomNumber2 >= 0.6 && randomNumber < 75) {
				Bonus bonus = new WiderPad(sourceBrick, this);
			} else if (randomNumber2 >= 0.75 && randomNumber < 0.9) {
				Bonus bonus = new NarrowerPad(sourceBrick, this);
			} else if (randomNumber2 >= 0.9) {
				Bonus bonus = new PenetrationBall(sourceBrick, this);
			}

		}

	}

	/**
	 * loescht den Bonus
	 * @param bonus
	 */
	public void deletebonus(Bonus bonus) {
		bonus.deleteBonusInVision();
		bonus.getTimerBonus().stop();
		bonus = null;

	}

	/**
	 * liest die .txt Datei ein, initialisiert die Bricks fuegt, die in die Liste ein
	 */
	public void readFile(Stage stage) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Level File");
		File selectedFile = fileChooser.showOpenDialog(stage);
		
		String line;

		try {

			FileReader filereader;
			
			if(selectedFile==null) {
				filereader = new FileReader("u02_level5.txt");
			}
			else {
				filereader = new FileReader(selectedFile);
			}

			BufferedReader bufferedReader = new BufferedReader(filereader);

			line = bufferedReader.readLine();
			lowerSpace = Integer.parseInt(line);

			while ((line = bufferedReader.readLine()) != null) {
				line = line.replaceAll(" ", "");
				char[] charArray = line.toCharArray();

				if (charArray.length > columnCounter) {
					columnCounter = charArray.length;
				}

				/**
				 * Hier werden Bricks angeordnent
				 */
				for (int i = 0; i < charArray.length; i++) {
					switch (charArray[i]) {
					case 'A': {

						bricks.add(new BrickA(i * this.brickWidth, rowCounter * this.brickHeight, this));
						setBrickCount(getBrickCount() + 1);
						break;
					}

					case 'B': {

						bricks.add(new BrickB(i * this.brickWidth, rowCounter * this.brickHeight, this));
						setBrickCount(getBrickCount() + 1);
						break;
					}

					case 'C': {
						bricks.add(new BrickC(i * this.brickWidth, rowCounter * this.brickHeight, this));
						setBrickCount(getBrickCount() + 1);
						break;
					}

					case 'D': {
						bricks.add(new BrickD(i * this.brickWidth, rowCounter * this.brickHeight, this));
						setBrickCount(getBrickCount() + 1);
						break;
					}

					case 'E': {
						bricks.add(new BrickE(i * this.brickWidth, rowCounter * this.brickHeight, this));
						setBrickCount(getBrickCount() + 1);
						break;
					}

					case 'F': {
						bricks.add(new BrickF(i * this.brickWidth, rowCounter * this.brickHeight, this));
						setBrickCount(getBrickCount() + 1);
						break;
					}

					case 'Z': {
						bricks.add(new BrickZ(i * this.brickWidth, rowCounter * this.brickHeight, this));
						setBrickCount(getBrickCount() + 1);
						break;
					}

					case 'G': {
						bricks.add(new BrickG(i * this.brickWidth, rowCounter * this.brickHeight, this));
						setBrickCount(getBrickCount() + 1);
						break;
					}

					case '_': {
						break;
					}

					}

				}

				rowCounter++;
			}

			bufferedReader.close();

		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file!");
		} catch (Exception ex) {
			System.out.println("Error reading file!");
		}
	}

	public void timerBall() {
		timerBall = new BallTimer(this);
		timerBall.start();
	}

	public int getBrickCount() {
		return brickCount;
	}

	public void setBrickCount(int brickCount) {
		this.brickCount = brickCount;
	}

	public List<Brick> getBricks() {
		return bricks;
	}

	public void setBricks(List<Brick> bricks) {
		this.bricks = bricks;
	}

	public Bat getBat() {
		return bat;
	}

	public void setBat(Bat bat) {
		this.bat = bat;
	}

	public Ball getBall() {
		return ball;
	}

	public void setBall(Ball ball) {
		this.ball = ball;
	}

	public int getLowerSpace() {
		return lowerSpace;
	}

	public void setLowerSpace(int lowerSpace) {
		this.lowerSpace = lowerSpace;
	}

	public int getColumnCounter() {
		return columnCounter;
	}

	public void setColumnCounter(int columnCounter) {
		this.columnCounter = columnCounter;
	}

	public int getRowCounter() {
		return rowCounter;
	}

	public void setRowCounter(int rowCounter) {
		this.rowCounter = rowCounter;
	}

	public BallTimer getTimerBall() {
		return timerBall;
	}

	public void setTimerBall(BallTimer timerBall) {
		this.timerBall = timerBall;
	}

	public BatTimer getTimerBat() {
		return timerBat;
	}

	public void setTimerBat(BatTimer timerBat) {
		this.timerBat = timerBat;
	}

	public BatTimerMouse getTimerBatMouse() {
		return timerBatMouse;
	}

	public void setTimerBatMouse(BatTimerMouse timerBatMouse) {
		this.timerBatMouse = timerBatMouse;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	@Override
	public void addListener(InvalidationListener listener) {
		
	}

	@Override
	public void removeListener(InvalidationListener listener) {
		// TODO Auto-generated method stub

	}

	public CustomObservable getcObservable() {
		return cObservable;
	}

	public void setcObservable(CustomObservable cObservable) {
		this.cObservable = cObservable;
	}

	public CountViewLives getValueFieldLives() {
		return valueFieldLives;
	}

	public void setValueFieldLives(CountViewLives valueFieldLives) {
		this.valueFieldLives = valueFieldLives;
	}

	public CountViewPoints getValueFieldPoints() {
		return valueFieldPoints;
	}

	public void setValueFieldPoints(CountViewPoints valueFieldPoints) {
		this.valueFieldPoints = valueFieldPoints;
	}

	public CounterPoints getCounterPoints() {
		return counterPoints;
	}

	public void setCounterPoints(CounterPoints counterPoints) {
		this.counterPoints = counterPoints;
	}

	public CounterLives getCounterLives() {
		return counterLives;
	}

	public void setCounterLives(CounterLives counterLives) {
		this.counterLives = counterLives;
	}

	public BrickView getBrickView() {
		return brickView;
	}

	public void setBrickView(BrickView brickView) {
		this.brickView = brickView;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public BallRegression getBallRegression() {
		return ballRegression;
	}

	public void setBallRegression(BallRegression ballRegression) {
		this.ballRegression = ballRegression;
	}

	public BonusDecayTimer getBonusDecayTimer() {
		return bonusDecayTimer;
	}

	public void setBonusDecayTimer(BonusDecayTimer bonusDecayTimer) {
		this.bonusDecayTimer = bonusDecayTimer;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public Timeline getTimeline() {
		return timeline;
	}

	public void setTimeline(Timeline timeline) {
		this.timeline = timeline;
	}

	public double getBrickWidth() {
		return brickWidth;
	}

	public void setBrickWidth(double brickWidth) {
		this.brickWidth = brickWidth;
	}

	public double getBrickHeight() {
		return brickHeight;
	}

	public void setBrickHeight(double brickHeight) {
		this.brickHeight = brickHeight;
	}

	
}
