package model;

import java.util.List;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.brick.Brick;
import util.CustomMath;

public class Ball extends Circle {

	private boolean sticked;
	private boolean penetrating;

	private double relativePosToBat;

	// X-Koordinate des Balls
	private double xCoord;
	// Y-Koordinate des Balls
	private double yCoord;

	// Die Geschwindigkeit vom Ball
	private double speed;

	// Die aktuelle Richtung vom Ball, in randians
	private double angle;

	private CustomVector ballVector;

	//Die vorgegebene Geschwindigkeit
	private final double DEFAULT_SPEED = 4.0;

	// Die von Regression zu ereichende Geschwindigkeit
	private double targetSpeed;

	//Die vorgegebene Groesse
	private final int DEFAULT_SIZE = 15;

	CustomVector vBalltoCenterRoundedEdges;

	CustomVector vBalltoCorner;

	//Durch diesen Vektor wird der BallVector angepasst
	CustomVector batSpeedVector;

	// Ein Referenz auf das Spiel
	private BreakOut breakOut;

	private CustomVector resultVector;

	/**
	 * alle moegliche Abprallungstype
	 * @author peijie
	 *
	 */
	enum Wall {
		UPPER, LEFT, RIGHT, LOWER, NONE, OUT, BAT, CORNER, BATEDGE, THROUGH
	}

	/**
	 * Konstruktor des Balls setzt die start x,y-Koordinaten: xCoord, yCoord fest
	 * sowie die Farbe: Color.BLACK und den Startwinkel: angle mit dem der Ball
	 * fischfilet.
	 */
	public Ball(BreakOut breakOut) {

		//Hier wird der Radius des Balls bestimmt
		super(0, Color.BLACK);
		this.setRadius(DEFAULT_SIZE);
		speed = DEFAULT_SPEED;
		targetSpeed = DEFAULT_SPEED;
		this.breakOut = breakOut;

		this.relativePosToBat = breakOut.getBat().getWidth() / 2;
		
		this.followBat();

		setTranslateY(breakOut.getBat().getyCoord() - this.getRadius());

		yCoord = this.getTranslateY();
		angle = 3 * Math.PI / 4;

	}

	/**
	 * setzt den Brick in logische ArrayList als removed, aber nicht loeschen,
	 * notifizieren Observer(View), der den Brick dem entsprechend aus Sicht loescht
	 * versucht auch ein Bonus zu erzeugen
	 * 
	 * @param brick
	 */
	private void removeBrick(Brick brick) {
		if (brick.isUnDestroyable() == false && brick.isZombieBrick() == false) {
			brick.setRemovedCounter(-1);
			breakOut.getBrickView().setColor(brick, brick.getRemovedCounter());
			breakOut.getcObservable().execute(brick, brick.getRemovedCounter());
			breakOut.getCounterPoints().setPointsCounter(1);
			if (brick.getRemovedCounter() == 0) {
				breakOut.createBonus(brick);
			}
			breakOut.checkWin(breakOut.getBricks());
		} else if (brick.isZombieBrick()) {
			brick.setRemovedCounter(-8);
			breakOut.getcObservable().execute(brick, brick.getRemovedCounter());
			breakOut.zombieBrickDelay(brick);
		}
	}

	/**
	 * Der Ball soll immer dem Schlaeger folgen, solange isSticked() true ist
	 */
	public void followBat() {
		this.setTranslateX(breakOut.getBat().getxCoord() + this.relativePosToBat);
		this.xCoord = breakOut.getBat().getxCoord() + this.relativePosToBat;
	}

	/**
	 * aendert den Bewegungswinkel je nach Kollision, danach die X und Y Koordinaten
	 * des Balls dem entsprechend neu einsetzen getLivesCounter() ist die Getter
	 * Methode fuer livesCounter
	 * 
	 * @return livesCounter: die verbleibenden Baelle
	 */
	public void moveBall1() {
		switch (collisionChecked()) {
		case UPPER: {
			if (angle < Math.PI / 2) {
				angle = 2 * Math.PI - angle;
			} else {
				angle = 2 * Math.PI - angle;
			}
			break;
		}
		case LOWER: {
			if (angle < 1.5 * Math.PI) {
				angle = 2 * Math.PI - angle;
			} else {
				angle = 2 * Math.PI - angle;
			}
			break;

		}
		case LEFT: {
			if (angle < Math.PI) {
				angle = Math.PI - angle;
			} else {
				angle = 3 * Math.PI - angle;
			}
			break;

		}
		case RIGHT: {

			if (angle < Math.PI / 2)
				angle = Math.PI - angle;
			else
				angle = 3 * Math.PI - angle;
			break;
		}
		case BAT: {
			double difference = this.xCoord - (breakOut.getBat().getxCoord());
			double ratio = (difference / breakOut.getBat().getwidthBat());
			angle = Math.PI - Math.PI * ratio;
			if (breakOut.getBat().isSticky()) {
				this.setSticked(true);
			}
			break;
		}

		case CORNER: {
			angle = Math.atan2(resultVector.getyCoord(), resultVector.getxCoord());

			if (angle < 0) {
				angle = 2 * Math.PI + angle;
			}

			break;
		}

		case OUT: {
			breakOut.getCounterLives().removeLive();
			breakOut.getTimerBall().stop();
			breakOut.getView().getPanePlay().getChildren().removeAll(breakOut.getBall());

			breakOut.checkGameOver();
			break;
		}

		case BATEDGE: {

			if (resultVector.getyCoord() < 0
					&& this.yCoord < breakOut.getBat().getyCoord() + breakOut.getBat().getRadiusRoundedEdges()) {
				resultVector.setyCoord(-resultVector.getyCoord());
				System.out.println("Hallo");
			}

			if (resultVector.getyCoord() > 0
					&& this.yCoord > breakOut.getBat().getyCoord() + breakOut.getBat().getRadiusRoundedEdges()) {
				resultVector.setyCoord(-resultVector.getyCoord());
				System.out.println("Hallo");
			}

			if (breakOut.getBat().getSpeedVector().getxCoord() < 0) {

				if (resultVector.getxCoord() > breakOut.getBat().getSpeedVector().getxCoord()) {

					// die Geschwindigkeit des balls muss groesser als Bat sein
					resultVector.setVector(breakOut.getBat().getSpeedVector().getxCoord() /*-1.2*/,
							resultVector.getyCoord());

				}

			} else if (breakOut.getBat().getSpeedVector().getxCoord() > 0) {

				if (resultVector.getxCoord() < breakOut.getBat().getSpeedVector().getxCoord()) {

					resultVector.setVector(breakOut.getBat().getSpeedVector().getxCoord() /* +1.2 */,
							resultVector.getyCoord());
				}
			}

			breakOut.getBallRegression().start();

			System.out.println("resultVctor: " + resultVector);

			speed = resultVector.getScala();

			angle = Math.atan2(resultVector.getyCoord(), resultVector.getxCoord());

			if (angle < 0) {
				angle = 2 * Math.PI + angle;
			}

			System.out.println("Result angle is " + angle / Math.PI + "*PI");
			break;
		}

		case NONE: {
			break;
		}
		}
		
		//Die wichtigste Bewegungsaufrufe
		CustomVector ballVector = getBallVector();
		xCoord = xCoord + ballVector.getxCoord();
		setTranslateX(xCoord);
		yCoord = yCoord - ballVector.getyCoord();
		setTranslateY(yCoord);

	}

	/**
	 * Erster Schritt von Kollisionkontrolle. Kollision mit Waenden und Schlaeger
	 * wird hier kontrolliert wenn die nicht der Fall ist, wird die darauffolgend
	 * mit Bricks kontrolliert
	 * 
	 * @return
	 */
	protected Wall collisionChecked() {
		if (xCoord + this.getRadius() >= breakOut.getColumnCounter() * breakOut.getBrickWidth() - this.getBallVector().getxCoord()) {
			return Wall.RIGHT;
		}
		if (xCoord <= this.getRadius() - this.getBallVector().getxCoord()) {
			return Wall.LEFT;
		}
		if (yCoord <= this.getRadius() + this.getBallVector().getyCoord()) {
			return Wall.UPPER;
		}
		if (yCoord >= (breakOut.getRowCounter() + breakOut.getLowerSpace()) * breakOut.getBrickHeight() - this.getRadius()) {
			if (xCoord >= breakOut.getBat().getxCoord()
					&& xCoord <= breakOut.getBat().getxCoord() + breakOut.getBat().getwidthBat()) {
				if (xCoord >= breakOut.getBat().getxCoord() + breakOut.getBat().getRadiusRoundedEdges()
						&& xCoord <= breakOut.getBat().getxCoord() + breakOut.getBat().getwidthBat()
								- breakOut.getBat().getRadiusRoundedEdges()) {
					return Wall.BAT;

				}
			}
		}

		//Hier wird abprallung mit den rundfoermigen Ecken des Schlaegers verarbeitet
		for (int i = 0; i <= 1; i++) {
			double xCoordCenterBatEdges;
			xCoordCenterBatEdges = breakOut.getBat().getxCoord() + breakOut.getBat().getRadiusRoundedEdges()
					+ i * (breakOut.getBat().getWidth() - (breakOut.getBat().getRadiusRoundedEdges() * 2));

			if (breakOut.getBat().getMovementDirection() <= 0) {
				batSpeedVector = new CustomVector(-breakOut.getBat().getspeedvektor(), 0);
			} else if (breakOut.getBat().getMovementDirection() > 0) {
				batSpeedVector = new CustomVector(breakOut.getBat().getspeedvektor(), 0);
			}

			double xDifference = xCoordCenterBatEdges - this.xCoord;

			double yCoordCenterBatEdges = breakOut.getBat().getyCoord() + breakOut.getBat().getRadiusRoundedEdges();

			double yDifference = yCoordCenterBatEdges - this.yCoord;

			if (CustomMath.getScala(xDifference, yDifference)
					- breakOut.getBat().getRadiusRoundedEdges() <= this.getRadius() + this.speed) {
				System.out.println("check");

				vBalltoCorner = new CustomVector(xDifference, -yDifference);

				CustomVector vBall = getBallVector();

				double scala = (vBall.getxCoord() * vBalltoCorner.getxCoord()
						+ vBall.getyCoord() * vBalltoCorner.getyCoord()) / (Math.pow(vBalltoCorner.getScala(), 2));
				CustomVector uVector = vBalltoCorner.scaleWith(-scala);
				CustomVector vVector = vBall.addVector(uVector);
				resultVector = uVector.addVector(vVector);

				return Wall.BATEDGE;
			}
		}

		if (yCoord >= (breakOut.getRowCounter() + breakOut.getLowerSpace()) * breakOut.getBrickHeight() + breakOut.getBat().getHeigthBat()
				+ this.getRadius()) {
			return Wall.OUT;
		}

		return checkBrickCollision(breakOut.getBricks());
	}

	/**
	 * Hier wird Kollision mit Bricks kontrolliert wird aufgerufen nur dann, wenn es
	 * keine Kollision mit Waenden oder schlaeger gibt,
	 * 
	 * @param bricks
	 *            Die ArrayList von allen noch zu zerstoerenden Bricks
	 * @return Kollisionstyp, als Kriterium der Winkelaenderung
	 */
	protected Wall checkBrickCollision(List<Brick> bricks) {
		for (Brick brick : bricks) {
			
			if (brick.getRemovedCounter() > 0) {
				if (xCoord <= (brick.getX() + brick.getWidth()) && (xCoord >= brick.getX())) {
					if (((yCoord - this.getRadius()) <= brick.getY() + brick.getHeight()
							+ this.getBallVector().getyCoord())
							&& ((yCoord - this.getRadius()) >= brick.getY() + brick.getHeight())) {
						removeBrick(brick);
						return checkPenetration(brick.getRemovedCounter(), Wall.UPPER);

					}

					if (((yCoord + this.getRadius()) <= brick.getY() )
							&& ((yCoord + this.getRadius()) >= brick.getY() + this.getBallVector().getyCoord())) {
						removeBrick(brick);
						return checkPenetration(brick.getRemovedCounter(), Wall.LOWER);
					}
				}

				if (yCoord <= (brick.getY() + brick.getHeight()) && (yCoord >= brick.getY())) {
					if (((xCoord + this.getRadius()) <= brick.getX() )
							&& ((xCoord + this.getRadius()) >= brick.getX() - this.getBallVector().getxCoord())) {
						removeBrick(brick);
						return checkPenetration(brick.getRemovedCounter(), Wall.RIGHT);
					}

					if (((xCoord - this.getRadius()) <= brick.getX() + brick.getWidth()
							- this.getBallVector().getxCoord())
							&& ((xCoord - this.getRadius()) >= brick.getX() + brick.getWidth())) {
						removeBrick(brick);
						return checkPenetration(brick.getRemovedCounter(), Wall.LEFT);
					}

				}

			}

			/**
			 * In der folgenden ForSchleife wird Collision mit den Ecken von Bricks Kontrolliert
			 */
			for (int i = 0; i < 4; i++) {
				double xDifference = brick.getCoordPairs()[i].getxCoord() - this.xCoord;
				double yDifference = brick.getCoordPairs()[i].getyCoord() - this.yCoord;

				if (CustomMath.getScala(xDifference, yDifference) <= this.getRadius()
						 && brick.getRemovedCounter() > 0) {

					vBalltoCorner = new CustomVector(xDifference, -yDifference);

					CustomVector vBall = getBallVector();

					double scala = (vBall.getxCoord() * vBalltoCorner.getxCoord()
							+ vBall.getyCoord() * vBalltoCorner.getyCoord()) / (Math.pow(vBalltoCorner.getScala(), 2));
					CustomVector uVector = vBalltoCorner.scaleWith(-scala);
					CustomVector vVector = vBall.addVector(uVector);
					resultVector = uVector.addVector(vVector);

					removeBrick(brick);
					return checkPenetration(brick.getRemovedCounter(), Wall.CORNER);

				}

			}

		}

		return Wall.NONE;
	}

	/**
	 * kontrolliert, ob der Ball gerade mit dem Bonus Penetration versehen,
	 * wenn dies der Fall ist, wird keine Abprallung stattfinden
	 * 
	 * @param candidate
	 *            Urspruenglicher Kollisionstyp, wird zurueckgegeben wenn der Ball
	 *            nicht durchstechend ist
	 * @return Entgueltiger Kollisionstyp
	 */
	public Wall checkPenetration(int counter, Wall candidate) {
		if (this.penetrating && counter == 0) {
			return Wall.THROUGH;
		} else {
			return candidate;
		}
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public boolean isSticked() {
		return sticked;
	}

	public void setSticked(boolean sticked) {
		this.sticked = sticked;
	}

	public double getRelativePosToBat() {
		return relativePosToBat;
	}

	public void setRelativePosToBat(double relativePosToBat) {
		this.relativePosToBat = relativePosToBat;
	}

	public CustomVector getBallVector() {
		CustomVector vBall = new CustomVector(this.speed * Math.cos(angle), this.speed * Math.sin(angle));
		return vBall;
	}

	public boolean isPenetrating() {
		return penetrating;
	}

	public void setPenetrating(boolean penetrating) {
		this.penetrating = penetrating;
	}

	public CustomVector getBallVectora() {
		return this.ballVector;
	}

	public void setBallVectora(double newX, double newY) {
		this.ballVector.setxCoord(newX);
		this.ballVector.setyCoord(newY);
	}

	public double getDefaultSpeed() {
		return this.DEFAULT_SPEED;
	}

	public double getTargetSpeed() {
		return targetSpeed;
	}

	public void setTargetSpeed(double targetSpeed) {
		this.targetSpeed = targetSpeed;
	}

	public int getDEFAULT_SIZE() {
		return DEFAULT_SIZE;
	}

}
