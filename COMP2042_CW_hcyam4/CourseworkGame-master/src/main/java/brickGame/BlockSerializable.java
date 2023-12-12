/**
 * The BlockSerializable class represents a serializable version of the Block class,
 * containing information about a block's position and type. It is used for saving and
 * loading game state.
 *
 *
 */
package brickGame;

import java.io.Serializable;

public class BlockSerializable implements Serializable {
    /**
     * The row coordinate of the block.
     */
    public final int row;
    /**
     * The column coordinate of the block.
     */
    public final int j;
    /**
     * The type of the block.
     */
    public final int type;
    /**
     * Constructs a new BlockSerializable object with the specified parameters.
     *
     * @param row    The row coordinate of the block.
     * @param j The column coordinate of the block.
     * @param type   The type of the block.
     */
    public BlockSerializable(int row , int j , int type) {
        this.row = row;
        this.j = j;
        this.type = type;
    }
}