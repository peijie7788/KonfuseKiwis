package model.bonus;

import java.util.Observable;

import controller.BonusDecayTimer;
import controller.BonusTimer;
import javafx.scene.shape.Rectangle;
import model.BreakOut;
import model.brick.Brick;

/**
 * Oberklasse aller Boni, wird von der View observiert
 *
 * @author peijie
 */
public abstract class Bonus extends Observable {
    private double xCoord;
    private double yCoord;
    private int width;
    private int height;
    protected BreakOut breakOut;
    private BonusTimer timerBonus;
    private int speed;
    private BonusDecayTimer bonusDecayTimer;

    /**
     * Der Typ, der von Notifikation mitgegeben wird
     *
     * @author peijie
     */
    public enum ChangeType {
        CREATE, MOVE, DELETE
    }

    /**
     * Konstruktor vom Bonus, haengt einen Observer an dem Bonus an
     * erstellt einen Timer fuer die Bewegung, und notifiziert die View,
     * dass ein Bonus erstellt worden ist
     *
     * @param sourceBrick Brick, aus dem dieser Bonus erzeugt wird
     * @param breakOut    Verweis auf das Spiel
     */
    public Bonus(Brick sourceBrick, BreakOut breakOut) {
        this.speed = 2;
        this.width = (int) (sourceBrick.getWidth() * 0.8);
        this.height = (int) (sourceBrick.getHeight() * 0.667);

        this.breakOut = breakOut;
        addObserver(breakOut.getView());
        this.xCoord = sourceBrick.getX() + sourceBrick.getWidth() * 0.1;
        this.yCoord = sourceBrick.getY() + sourceBrick.getHeight();

        timerBonus = new BonusTimer(this, breakOut);
        timerBonus.start();
        bonusDecayTimer = new BonusDecayTimer(this);

        this.setChanged();
        this.notifyObservers(ChangeType.CREATE);

    }

    /**
     * bewegt den Bonus in logischer Ebene nach unten
     * setzt, wenn dieser vom Spieler bzw. Schlaeger beruehrt wird
     */
    public void translate() {

        if ((this.xCoord + this.width >= breakOut.getBat().getxCoord()
                && this.xCoord <= breakOut.getBat().getxCoord() + breakOut.getBat().getWidth())
                && this.yCoord + this.height >= breakOut.getBat().getTranslateY()
                && this.yCoord <= breakOut.getBat().getTranslateY()
                ) {

            applyBonus();
            bonusDecayTimer.start();
            breakOut.deletebonus(this);
        }
        setyCoord(getyCoord() + speed);

    }

    /**
     * entfernt den Bonus in der View
     */
    public void deleteBonusInVision() {
        this.setChanged();
        this.notifyObservers(ChangeType.DELETE);
    }

    /**
     * setzt alle Eigenschaften zurueck
     */
    public void resetAll() {
        this.breakOut.getBall().setRadius(this.breakOut.getBall().getDEFAULT_SIZE());
        this.breakOut.getBall().setPenetrating(false);
        this.breakOut.getBall().setSpeed(this.breakOut.getBall().getDefaultSpeed());
        breakOut.getBat().setWidth(breakOut.getBat().getDEFAULT_WIDTH());
        breakOut.getBat().setWidthBat(breakOut.getBat().getDEFAULT_WIDTH());
    }

    /**
     * wird von Kinderklasse implementiert, setzt den Wert zurueck, der ursprungliche von diesem Bonus geaendert wurde
     */
    public abstract void reset();

    /**
     * wird von Kinderklasse implementiert, setzt den Bonus ein
     */
    public abstract void applyBonus();

    public BonusTimer getTimerBonus() {
        return timerBonus;
    }

    public void setTimerBonus(BonusTimer timerBonus) {
        this.timerBonus = timerBonus;
    }

    public double getxCoord() {
        return xCoord;
    }

    public void setxCoord(double xCoord) {
        this.xCoord = xCoord;
    }

    public double getyCoord() {
        return yCoord;
    }

    public void setyCoord(double yCoord) {
        this.yCoord = yCoord;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


}
