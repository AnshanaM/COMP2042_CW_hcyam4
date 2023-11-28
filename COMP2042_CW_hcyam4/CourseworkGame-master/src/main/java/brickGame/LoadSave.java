package brickGame;

import java.io.*;
import java.util.ArrayList;

import static brickGame.Main.score;
import static brickGame.Main.yBreak;

public class LoadSave {
    private static String savePath    = "./save/save.mdds";
    private static String savePathDir = "./save";
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
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(savePath)));

            Main.level = inputStream.readInt();
            score.setScore(inputStream.readInt());
            Main.heart = inputStream.readInt();
            Main.destroyedBlockCount = inputStream.readInt();

            Main.xBall = inputStream.readDouble();
            Main.yBall = inputStream.readDouble();
            Main.xBreak = inputStream.readDouble();
            yBreak = inputStream.readDouble();
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

    public void saveGame(Main main, ArrayList<BlockSerializable> blockSerializables) {
        new Thread(() -> {
            new File(savePathDir).mkdirs();
            File file = new File(savePath);
            ObjectOutputStream outputStream = null;

            try {
                outputStream = new ObjectOutputStream(new FileOutputStream(file));

                outputStream.writeInt(Main.level);
                outputStream.writeInt(Main.score.getScore());
                outputStream.writeInt(Main.heart);
                outputStream.writeInt(Main.destroyedBlockCount);


                outputStream.writeDouble(Main.xBall);
                outputStream.writeDouble(Main.yBall);
                outputStream.writeDouble(Main.xBreak);
                outputStream.writeDouble(Main.yBreak);
                outputStream.writeDouble(Main.centerBreakX);
                outputStream.writeLong(Main.time);
                outputStream.writeLong(Main.goldTime);
                outputStream.writeDouble(Main.vX);

                outputStream.writeBoolean(Main.isExistHeartBlock);
                outputStream.writeBoolean(Main.isGoldStauts);
                outputStream.writeBoolean(Main.goDownBall);
                outputStream.writeBoolean(Main.goRightBall);
                outputStream.writeBoolean(Main.colideToBreak);
                outputStream.writeBoolean(Main.colideToBreakAndMoveToRight);
                outputStream.writeBoolean(Main.colideToRightWall);
                outputStream.writeBoolean(Main.colideToLeftWall);
                outputStream.writeBoolean(Main.colideToRightBlock);
                outputStream.writeBoolean(Main.colideToBottomBlock);
                outputStream.writeBoolean(Main.colideToLeftBlock);
                outputStream.writeBoolean(Main.colideToTopBlock);

                outputStream.writeObject(blockSerializables);

                score.showMessage("Game Saved", main);

            } catch (IOException e) {
                System.out.print("File IO access issue");
                return;
                //e.printStackTrace();
            } finally {
                try {
                    assert outputStream != null;
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    System.out.print("File IO access issue");
                    //e.printStackTrace();
                }
            }
        }).start();

    }
}