package com.loban.chinesecheckers.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by loban on 5/26/13.
 *
 * @author Loban Rahman <loban.rahman@gmail.com>
 */
public class Board
{
    public static int SIZE = 19;

    private static String[] initialColors = {
        ". . . . . . . . . . . . . . . . . . .",
        ". . . . . . . . . . . . . Y . . . . .",
        ". . . . . . . . . . . . Y Y . . . . .",
        ". . . . . . . . . . . Y Y Y . . . . .",
        ". . . . . . . . . . Y Y Y Y . . . . .",
        ". . . . . B B B B = = = = = G G G G .",
        ". . . . . B B B = = = = = = G G G . .",
        ". . . . . B B = = = = = = = G G . . .",
        ". . . . . B = = = = = = = = G . . . .",
        ". . . . . = = = = = = = = = . . . . .",
        ". . . . C = = = = = = = = M . . . . .",
        ". . . C C = = = = = = = M M . . . . .",
        ". . C C C = = = = = = M M M . . . . .",
        ". C C C C = = = = = M M M M . . . . .",
        ". . . . . R R R R . . . . . . . . . .",
        ". . . . . R R R . . . . . . . . . . .",
        ". . . . . R R . . . . . . . . . . . .",
        ". . . . . R . . . . . . . . . . . . .",
        ". . . . . . . . . . . . . . . . . . .",
    };

    private Collection<Hole> holes = new ArrayList<Hole>();

    public Board() {
        resetHoles();
    }

    public Iterator<Hole> getHoleIterator() {
        return holes.iterator();
    }

    public void resetHoles() {
        Hole[][] holes = new Hole[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                GameColor color = null;
                switch (initialColors[i].charAt(j * 2)) {
                    case '=':
                        color = GameColor.WHITE;
                        break;
                    case 'R':
                        color = GameColor.RED;
                        break;
                    case 'G':
                        color = GameColor.GREEN;
                        break;
                    case 'B':
                        color = GameColor.BLUE;
                        break;
                    case 'M':
                        color = GameColor.MAGENTA;
                        break;
                    case 'Y':
                        color = GameColor.YELLOW;
                        break;
                    case 'C':
                        color = GameColor.CYAN;
                        break;
                }
                if (color != null) {
                    holes[i][j] = new Hole(color, i, j);
                    if (color != GameColor.WHITE)
                        holes[i][j].setPiece(new Piece(color, holes[i][j]));
                }
            }
        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (holes[i][j] == null)
                    continue;
                if (i + 1 < Board.SIZE)
                    holes[i][j].setHole(HolePosition.BOTTOM_RIGHT, holes[i + 1][j]);
                if (i + 1 < Board.SIZE && j - 1 >= 0)
                    holes[i][j].setHole(HolePosition.BOTTOM_LEFT, holes[i + 1][j - 1]);
                if (j - 1 >= 0)
                    holes[i][j].setHole(HolePosition.LEFT, holes[i][j - 1]);
                if (i - 1 >= 0)
                    holes[i][j].setHole(HolePosition.TOP_LEFT, holes[i - 1][j]);
                if (i - 1 >= 0 && j + 1 < Board.SIZE)
                    holes[i][j].setHole(HolePosition.TOP_RIGHT, holes[i - 1][j + 1]);
                if (j + 1 < Board.SIZE)
                    holes[i][j].setHole(HolePosition.RIGHT, holes[i + 1][j - 1]);
            }
        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (holes[i][j] != null)
                    this.holes.add(holes[i][j]);
            }
        }
    }
}
