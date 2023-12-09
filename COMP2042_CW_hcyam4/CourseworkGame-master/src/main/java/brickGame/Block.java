package brickGame;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.Random;

public class Block implements Serializable {
    private static Block block = new Block(-1, -1, "choco.jpg", 99);
    //choco.jpg is default brick
    public int row;
    public int column;
    public boolean isDestroyed = false;
    private String color;
    public int type;
    public int hits;

    public int x;
    public int y;

    private final int width = 85;
    private final int height = 35;
    private final int paddingTop = height * 2;//padding from the top vertically
    private final int paddingH = 37; //padding from the left side horizontally
    public Rectangle rect;

    public static int NO_HIT = -1;
    public static int HIT_RIGHT = 0;
    public static int HIT_BOTTOM = 1;
    public static int HIT_LEFT = 2;
    public static int HIT_TOP = 3;

    public static int BLOCK_NORMAL = 99;
    public static int BLOCK_CHOCO = 100;
    public static int BLOCK_STAR = 101;
    public static int BLOCK_HEART = 102;


    public Block(int row, int column, String color, int type) {
        this.row = row;
        this.column = column;
        this.color = color;
        this.type = type;
        this.hits = 0;

        draw();
    }

    /**
     * draw() function creates the brick
     * it assigns the brick image appropriately
     * based on the type of brick
     */
    private void draw() {
        x = (column * width) + paddingH;
        y = (row * height) + paddingTop;

        rect = new Rectangle();
        rect.setWidth(width);
        rect.setHeight(height);
        rect.setX(x);
        rect.setY(y);

        if (type == BLOCK_CHOCO) {
            color="choco.jpg";
        } else if (type == BLOCK_HEART) {
            color="heart.jpg";
        } else if (type == BLOCK_STAR) {
            color="star.jpg";
        }
        Image image = new Image(color);
        ImagePattern pattern = new ImagePattern(image);
        rect.setFill(pattern);

    }


    public int checkHitToBlock(double xBall, double yBall, int ballRadius) {
        if (isDestroyed) {
            return NO_HIT;
        }
        double blockCenterX = x + width / 2;
        double blockCenterY = y + height / 2;
        double distance = Math.sqrt(Math.pow(xBall - blockCenterX, 2) + Math.pow(yBall - blockCenterY, 2));
        if (distance <= ballRadius + Math.max(width, height) / 2) {// Collision detected
            double relativeX = xBall - blockCenterX;
            double relativeY = yBall - blockCenterY;
            if (Math.abs(relativeX) > Math.abs(relativeY)) {
                return relativeX > 0 ? HIT_LEFT : HIT_RIGHT;
            } else {
                return relativeY > 0 ? HIT_TOP : HIT_BOTTOM;
            }
        }
        return NO_HIT;
    }



    public static int getPaddingTop() {
        return block.paddingTop;
    }

    public static int getPaddingH() {
        return block.paddingH;
    }

    public static int getHeight() {
        return block.height;
    }

    public static int getWidth() {
        return block.width;
    }

    public void setBlockFill(ImagePattern pattern) {
        rect.setFill(pattern);
    }
    public static void initBoard(int level) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < level + 2; j++) {
                int r = new Random().nextInt(500);
                int type;
                if (r % 10 == 1) {
                    type = Block.BLOCK_CHOCO;
                } else if (r % 10 == 2) {
                    if (!Main.isExistHeartBlock) {
                        type = Block.BLOCK_HEART;
                        Main.isExistHeartBlock = true;
                    } else {
                        type = Block.BLOCK_NORMAL;
                    }
                } else if (r % 10 == 3) {
                    type = Block.BLOCK_STAR;
                } else {
                    type = Block.BLOCK_NORMAL;
                }
                //Main.blocks.add(new Block(j, i, Main.colors[r % (Main.colors.length)], type));
                Main.blocks.add(new Block(j, i,"concrete.jpg", type));
            }
        }
    }
}


