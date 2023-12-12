/**
 * The Bonus class represents a bonus object in the game, which can be collected
 * by the player to gain various benefits such as increased score or special abilities.
 * It includes methods for initializing, drawing, and checking if the bonus is taken.
 *
 */
package brickGame;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.Random;

public class Bonus implements Serializable {
    /**
     * The Rectangle object representing the visual appearance of the bonus.
     */
    public Rectangle bonus;
    /**
     * The x-coordinate of the bonus.
     */
    public double x;
    /**
     * The y-coordinate of the bonus.
     */
    public double y;
    /**
     * The time the bonus was created.
     */
    public long timeCreated;
    /**
     * A flag indicating whether the bonus has been taken.
     */
    public boolean taken = false;
    /**
     * The type of the bonus, determining its effect when taken.
     */
    public int type;
    /**
     * Constructs a new Bonus object at the specified row and column coordinates.
     *
     * @param row    The row coordinate where the bonus will appear.
     * @param column The column coordinate where the bonus will appear.
     */
    public Bonus(int row, int column) {
        x = (column * (Block.getWidth())) + Block.getPaddingH() + (Block.getWidth() / 2) - 15;
        y = (row * (Block.getHeight())) + Block.getPaddingTop() + (Block.getHeight() / 2) - 15;
        draw();
    }
    /**
     * Initializes the visual representation of the bonus, setting its size, position,
     * and image based on a random type.
     */
    private void draw() {
        bonus = new Rectangle();
        bonus.setWidth(30);
        bonus.setHeight(30);
        bonus.setX(x);
        bonus.setY(y);
        String url;
        int r = new Random().nextInt(20) % 3;
        if (r == 0) {
            url = "coins.png";
            type = 0;
        } else if (r == 1){
            url = "treasure.png";
            type = 1;
        }
        else{
            url = "teleport.png";
            type = 2;
        }
        bonus.setFill(new ImagePattern(new Image(url)));
    }
    /**
     * Checks if the bonus is taken by the player, updating score and applying
     * special effects accordingly.
     */
    public void checkIsTaken(){
        if ((this.x + bonus.getWidth()) > Main.sceneHeight || this.taken) {
            return;
        }
        if ((this.y+ bonus.getHeight()) >= Main.yBreak && this.y <= (Main.yBreak + Main.breakHeight) && (this.x+bonus.getWidth()) >= Main.xBreak && this.x <= Main.xBreak + Main.breakWidth) {
            if (type == 0){
                System.out.println("You Got it and +3 score for you");
                this.taken = true;
                this.bonus.setVisible(false);
                Main.score.incScore(3);
                Main.displayView.show(this.x, this.y, 3);
            }
            else if (type == 1){
                System.out.println("You Got it and +5 score for you");
                this.taken = true;
                this.bonus.setVisible(false);
                Main.score.incScore(5);
                Main.displayView.show(this.x, this.y, 5);
            }
            else{
                this.taken = true;
                this.bonus.setVisible(false);
                Main.teleport = true;
                Main.displayView.showMessage("TELEPORT");

            }

        }
    }

}
