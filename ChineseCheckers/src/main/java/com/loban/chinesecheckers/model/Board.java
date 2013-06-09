package com.loban.chinesecheckers.model;

import com.loban.chinesecheckers.enums.BoardDirection;
import com.loban.chinesecheckers.enums.PlayerColor;

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

    private Collection<BoardHole> mBoardHoleCollection = new ArrayList<BoardHole>();

    public Board() {
        // Create the board holes
        BoardHole[][] boardHoles = new BoardHole[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                switch (initialColors[i].charAt(j * 2)) {
                    case '=':
                        boardHoles[i][j] = new BoardHole(i, j);
                        break;
                    case 'R':
                        boardHoles[i][j] = new BoardHole(i, j, PlayerColor.RED);
                        break;
                    case 'G':
                        boardHoles[i][j] = new BoardHole(i, j, PlayerColor.GREEN);
                        break;
                    case 'B':
                        boardHoles[i][j] = new BoardHole(i, j, PlayerColor.BLUE);
                        break;
                    case 'M':
                        boardHoles[i][j] = new BoardHole(i, j, PlayerColor.MAGENTA);
                        break;
                    case 'Y':
                        boardHoles[i][j] = new BoardHole(i, j, PlayerColor.YELLOW);
                        break;
                    case 'C':
                        boardHoles[i][j] = new BoardHole(i, j, PlayerColor.CYAN);
                        break;
                }
                if (boardHoles[i][j] != null)
                    mBoardHoleCollection.add(boardHoles[i][j]);
            }
        }

        // Link the board holes
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (boardHoles[i][j] == null)
                    continue;
                if (i + 1 < Board.SIZE)
                    boardHoles[i][j].setLinkedBoardHole(BoardDirection.BOTTOM_RIGHT, boardHoles[i + 1][j]);
                if (i + 1 < Board.SIZE && j - 1 >= 0)
                    boardHoles[i][j].setLinkedBoardHole(BoardDirection.BOTTOM_LEFT, boardHoles[i + 1][j - 1]);
                if (j - 1 >= 0)
                    boardHoles[i][j].setLinkedBoardHole(BoardDirection.LEFT, boardHoles[i][j - 1]);
                if (i - 1 >= 0)
                    boardHoles[i][j].setLinkedBoardHole(BoardDirection.TOP_LEFT, boardHoles[i - 1][j]);
                if (i - 1 >= 0 && j + 1 < Board.SIZE)
                    boardHoles[i][j].setLinkedBoardHole(BoardDirection.TOP_RIGHT, boardHoles[i - 1][j + 1]);
                if (j + 1 < Board.SIZE)
                    boardHoles[i][j].setLinkedBoardHole(BoardDirection.RIGHT, boardHoles[i + 1][j - 1]);
            }
        }
    }

    public Iterator<BoardHole> getBoardHoleIterator() {
        return mBoardHoleCollection.iterator();
    }
}
