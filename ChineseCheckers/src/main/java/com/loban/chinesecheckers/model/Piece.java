package com.loban.chinesecheckers.model;

/**
 * Created by loban on 5/26/13.
 *
 * @author Loban Rahman <loban.rahman@gmail.com>
 */
public class Piece
{
    private GameColor color;

    private Hole hole;

    public Piece(GameColor color, Hole hole) {
        this.color = color;
        this.hole = hole;
    }

    public GameColor getColor() {
        return color;
    }

    public void setHole(Hole hole) {
        this.hole = hole;
    }

    public Hole getHole() {
        return hole;
    }

    public int getRow() {
        return hole.getRow();
    }

    public int getCol() {
        return hole.getCol();
    }
}
