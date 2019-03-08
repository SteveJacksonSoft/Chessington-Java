package training.chessington.model;

import training.chessington.model.pieces.*;

public class Board {

    private Piece[][] board = new Piece[8][8];

    private Board() {
    }

    public static Board forNewGame() {
        Board board = new Board();
        board.setBackRow(0, PlayerColour.BLACK);
        board.setBackRow(7, PlayerColour.WHITE);

        for (int col = 0; col < 8; col++) {
            board.board[1][col] = new Pawn(PlayerColour.BLACK);
            board.board[6][col] = new Pawn(PlayerColour.WHITE);
        }

        return board;
    }

    public static Board empty() {
        return new Board();
    }

    private void setBackRow(int rowIndex, PlayerColour colour) {
        board[rowIndex][0] = new Rook(colour);
        board[rowIndex][1] = new Knight(colour);
        board[rowIndex][2] = new Bishop(colour);
        board[rowIndex][3] = new Queen(colour);
        board[rowIndex][4] = new King(colour);
        board[rowIndex][5] = new Bishop(colour);
        board[rowIndex][6] = new Knight(colour);
        board[rowIndex][7] = new Rook(colour);
    }

    public Piece get(Coordinates coords) {
        return board[coords.getRow()][coords.getCol()];
    }

    public void move(Coordinates from, Coordinates to) {
        board[to.getRow()][to.getCol()] = board[from.getRow()][from.getCol()];
        board[from.getRow()][from.getCol()] = null;
    }

    public void placePiece(Coordinates coords, Piece piece) {
        board[coords.getRow()][coords.getCol()] = piece;
    }

    public boolean moveIsBlocked(Move move) {
        
        int fromRow = move.getFrom().getRow();
        int fromCol = move.getFrom().getCol();
        int toRow = move.getTo().getRow();
        int toCol = move.getTo().getCol();
        
        boolean isHorizontal = fromRow == toRow;
        boolean isVertical = fromCol == toCol;
        boolean isDiagonal = (fromRow - toRow) == (fromCol - toCol);
        
        if (isHorizontal) {
            return linearMoveIsBlocked(fromRow, fromCol, toCol, false);
        } else if (isVertical) {
            return linearMoveIsBlocked(fromCol, fromRow, toRow, true);
        }
        // else if (isDiagonal) {...}
        return false;
    }
    
    private boolean linearMoveIsBlocked(int lineIndex, int fromIndex, int toIndex, boolean moveIsVertical) {
        for (int spaceIndex = Math.min(fromIndex + 1, toIndex); spaceIndex <= Math.max(toIndex, fromIndex - 1); spaceIndex++) {
            if (moveIsVertical) {
                if (this.get(new Coordinates(spaceIndex, lineIndex)) != null) {
                    return true;
                }
            } else {
                if (this.get(new Coordinates(lineIndex, spaceIndex)) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean contains(Coordinates position) {
        int row = position.getRow();
        int col = position.getCol();
        return 0 <= row && row < 8 && 0 <= col && col < 8;
    }
}
