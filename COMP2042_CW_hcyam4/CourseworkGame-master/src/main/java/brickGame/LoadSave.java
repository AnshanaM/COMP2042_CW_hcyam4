package brickGame;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class LoadSave {
    /*public static boolean          isExistHeartBlock;
    public boolean          isGoldStauts;
    public boolean          goDownBall;
    public boolean          goRightBall;
    public boolean          colideToBreak;
    public boolean          colideToBreakAndMoveToRight;
    public boolean          colideToRightWall;
    public boolean          colideToLeftWall;
    public boolean          colideToRightBlock;
    public boolean          colideToBottomBlock;
    public boolean          colideToLeftBlock;
    public boolean          colideToTopBlock;
    public int              level;
    public int              score;
    public int              heart;
    public int              destroyedBlockCount;
    public double           xBall;
    public double           yBall;
    public double           xBreak;
    public double           yBreak;
    public double           centerBreakX;
    public long             time;
    public long             goldTime;
    public double           vX;*/
    public ArrayList<BlockSerializable> blocks = new ArrayList<>();

    public boolean loadGame() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(Main.savePath)));

            Main.level = inputStream.readInt();
            Main.score.setScore(inputStream.readInt());
            Main.heart = inputStream.readInt();
            Main.destroyedBlockCount = inputStream.readInt();

            Main.xBall = inputStream.readDouble();
            Main.yBall = inputStream.readDouble();
            Main.xBreak = inputStream.readDouble();
            Main.yBreak = inputStream.readDouble();
            Main.centerBreakX = inputStream.readDouble();
            Main.time = inputStream.readLong();
            Main.goldTime = inputStream.readLong();
            Main.vX = inputStream.readDouble();

            Main.isExistHeartBlock = inputStream.readBoolean();
            Main.isGoldStauts = inputStream.readBoolean();
            Main.goDownBall = inputStream.readBoolean();
            Main.goRightBall = inputStream.readBoolean();
            Main.colideToBreak = inputStream.readBoolean();
            Main.colideToBreakAndMoveToRight = inputStream.readBoolean();
            Main.colideToRightWall = inputStream.readBoolean();
            Main.colideToLeftWall = inputStream.readBoolean();
            Main.colideToRightBlock = inputStream.readBoolean();
            Main.colideToBottomBlock = inputStream.readBoolean();
            Main.colideToLeftBlock = inputStream.readBoolean();
            Main.colideToTopBlock = inputStream.readBoolean();
            try {
                Main.destroyedBlockCount = 0;
                blocks = (ArrayList<BlockSerializable>) inputStream.readObject();
//                Main.blocks.clear();
//                Main.bonuses.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("no games saved previously");
            return false;
            //e.printStackTrace();
        }
        return true;
    }
}