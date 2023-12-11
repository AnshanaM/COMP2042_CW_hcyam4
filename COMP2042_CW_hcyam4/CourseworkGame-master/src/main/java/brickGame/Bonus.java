package brickGame;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.Random;

public class Bonus implements Serializable {
    public Rectangle bonus;

    public double x;
    public double y;
    public long timeCreated;
    public boolean taken = false;
    public int type;

    public Bonus(int row, int column) {
        x = (column * (Block.getWidth())) + Block.getPaddingH() + (Block.getWidth() / 2) - 15;
        y = (row * (Block.getHeight())) + Block.getPaddingTop() + (Block.getHeight() / 2) - 15;
        draw();
    }

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
            url = "penalty.png";
            type = 2;
        }
        bonus.setFill(new ImagePattern(new Image(url)));
    }

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
                Main.score.show(this.x, this.y, 3);
            }
            else if (type == 1){
                System.out.println("You Got it and +5 score for you");
                this.taken = true;
                this.bonus.setVisible(false);
                Main.score.incScore(5);
                Main.score.show(this.x, this.y, 5);
            }
            else{
                this.taken = true;
                this.bonus.setVisible(false);
                Main.swap = true;
            }

        }
    }

}
