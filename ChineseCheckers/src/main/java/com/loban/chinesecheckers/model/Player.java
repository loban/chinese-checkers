package com.loban.chinesecheckers.model;

import com.loban.chinesecheckers.enums.PlayerColor;

/**
 * Created by loban on 5/26/13.
 *
 * @author Loban Rahman <loban.rahman@gmail.com>
 */
public class Player
{
    private PlayerColor mPlayerColor;

    private String mName;

    public Player(PlayerColor playerColor, String name) {
        mPlayerColor = playerColor;
        mName = name;
    }

    public PlayerColor getPlayerColor() {
        return mPlayerColor;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getName() {
        return mName;
    }
}
