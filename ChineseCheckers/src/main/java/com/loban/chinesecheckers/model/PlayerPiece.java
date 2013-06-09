package com.loban.chinesecheckers.model;

/**
 * Created by loban on 5/26/13.
 *
 * @author Loban Rahman <loban.rahman@gmail.com>
 */
public class PlayerPiece
{
    private Player mPlayer;

    private BoardHole mBoardHole;

    public PlayerPiece(Player player, BoardHole boardHole) {
        mPlayer = player;
        mBoardHole = boardHole;
    }

    public Player getPlayer() {
        return mPlayer;
    }

    public void setBoardHole(BoardHole boardHole) {
        this.mBoardHole = boardHole;
    }

    public BoardHole getBoardHole() {
        return mBoardHole;
    }
}
