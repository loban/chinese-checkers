package com.loban.chinesecheckers.model;

import java.util.EnumMap;
import java.util.Map;

import com.loban.chinesecheckers.enums.BoardDirection;
import com.loban.chinesecheckers.enums.PlayerColor;

/**
 * Created by loban on 5/26/13.
 *
 * @author Loban Rahman <loban.rahman@gmail.com>
 */
public class BoardHole
{
    private int mRow;
    private int mCol;

    private PlayerColor mPlayerColor;

    private PlayerPiece mPlayerPiece;

    Map<BoardDirection, BoardHole> mBoardHoleMap = new EnumMap<BoardDirection, BoardHole>(BoardDirection.class);

    public BoardHole(int row, int col) {
        mRow = row;
        mCol = col;
    }

    public BoardHole(int row, int col, PlayerColor playerColor) {
        this(row, col);
        mPlayerColor = playerColor;
    }

    public int getRow() {
        return mRow;
    }

    public int getCol() {
        return mCol;
    }

    public PlayerColor getPlayerColor() {
        return mPlayerColor;
    }

    public void setPlayerPiece(PlayerPiece playerPiece) {
        if (mPlayerPiece != playerPiece) {
            mPlayerPiece = playerPiece;
            mPlayerPiece.setBoardHole(this);
        }
    }

    public PlayerPiece getPlayerPiece() {
        return mPlayerPiece;
    }

    public void setLinkedBoardHole(BoardDirection pos, BoardHole boardHole) {
        mBoardHoleMap.put(pos, boardHole);
    }

    public BoardHole getLinkedBoardHole(BoardDirection pos) {
        return mBoardHoleMap.get(pos);
    }
}
