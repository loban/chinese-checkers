package com.loban.chinesecheckers.model;

import com.loban.chinesecheckers.enums.PlayerColor;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by loban on 7/17/13.
 *
 * @author Loban Rahman <loban.rahman@gmail.com>
 */
public class Game
{
    private static final String[] INITIAL_PLAYERS = {
        "RY",
        "RGB",
        "RYCG",
        "RYCGB",
        "RYCGBM",
    };

    private Collection<Player> mPlayerCollection = new ArrayList<Player>();

    private Board mBoard;

    public Game(int numPlayers) {
        // Initialize the players
        assert(numPlayers >= 2 && numPlayers <= 6);
        for (int i = 0; i < INITIAL_PLAYERS[numPlayers - 2].length(); i++) {
            switch (INITIAL_PLAYERS[numPlayers - 2].charAt(i)) {
                case 'R':
                    mPlayerCollection.add(new Player(PlayerColor.RED, "Red"));
                    break;
                case 'G':
                    mPlayerCollection.add(new Player(PlayerColor.GREEN, "Green"));
                    break;
                case 'B':
                    mPlayerCollection.add(new Player(PlayerColor.BLUE, "Blue"));
                    break;
                case 'M':
                    mPlayerCollection.add(new Player(PlayerColor.MAGENTA, "Magenta"));
                    break;
                case 'Y':
                    mPlayerCollection.add(new Player(PlayerColor.YELLOW, "Yellow"));
                    break;
                case 'C':
                    mPlayerCollection.add(new Player(PlayerColor.CYAN, "Cyan"));
                    break;
            }
        }

        // Initialize the board
        mBoard = new Board(mPlayerCollection);
    }

    public Collection<Player> getPlayers() {
        return mPlayerCollection;
    }

    public Board getBoard() {
        return mBoard;
    }
}
