package com.loban.chinesecheckers.model;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created by loban on 5/26/13.
 *
 * @author Loban Rahman <loban.rahman@gmail.com>
 */
public class Hole
{
    private GameColor color;

    private int row;
    private int col;

    private Piece piece;

    Map<HolePosition, Hole> holes = new EnumMap<HolePosition, Hole>(HolePosition.class);

    public Hole(GameColor color, int row, int col) {
        this.color = color;
        this.row = row;
        this.col = col;
    }

    public GameColor getColor() {
        return color;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setHole(HolePosition pos, Hole hole) {
        holes.put(pos, hole);
    }

    public Hole getHole(HolePosition pos) {
        return holes.get(pos);
    }
}
