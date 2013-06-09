package com.loban.chinesecheckers.model;

/**
 * Created by loban on 5/26/13.
 *
 * @author Loban Rahman <loban.rahman@gmail.com>
 */
public class Player
{
    private GameColor color;

    private String name;

    public Player(GameColor color, String name) {
        this.color = color;
        this.name = name;
    }

    public GameColor getColor() {
        return color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
