package brickGame;

import java.io.*;
import java.util.ArrayList;


public class LoadSave {
    private static LoadSave instance;
    private static String savePath    = "./save/save.mdds";
    private static String savePathDir = "./save";

    public ArrayList<BlockSerializable> blocks = new ArrayList<>();
    private LoadSave(){
        //no initilaising required in this class
    }
    public static LoadSave getInstance() {
        if (instance == null) {
            instance = new LoadSave();
        }
        return instance;
    }

    public boolean loadGame() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(savePath)));

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
            } catch (Exception e) {
                System.out.println("\nCannot load from file");
            }
            Main.teleport = inputStream.readBoolean();
            Main.displayView.showMessage("Game Loaded");
        } catch (IOException e) {
            System.out.println("No games saved previously");
            return false;
        }
        return true;
    }

    public void saveGame(ArrayList<BlockSerializable> blockSerializables) {
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
                outputStream.writeBoolean(Main.teleport);
                Main.displayView.showMessage("Game Saved");

            } catch (IOException e) {
                System.out.print("Cannot write to File");
            } finally {
                try {
                    assert outputStream != null;
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    System.out.print("File IO access issue");
                }
            }
        }).start();

    }
}