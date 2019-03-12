package training.chessington.model;

import training.chessington.model.pieces.*;

import java.util.Optional;
import java.util.function.Function;

public class Board {

    private Piece[][] board = new Piece[8][8];

    private Board() {
    }

    public static Board forNewGame() {
        Board board = new Board();
        board.setBackRow(0, PlayerColour.BLACK);
        board.setBackRow(7, PlayerColour.WHITE);

        for (int col = 0; col < 8; col++) {
            board.board[1][col] = new Pawn(PlayerColour.BLACK, board);
            board.board[6][col] = new Pawn(PlayerColour.WHITE, board);
        }

        return board;
    }

    public static Board empty() {
        return new Board();
    }

    private void setBackRow(int rowIndex, PlayerColour colour) {
        board[rowIndex][0] = new Rook(colour, this);
        board[rowIndex][1] = new Knight(colour, this);
        board[rowIndex][2] = new Bishop(colour, this);
        board[rowIndex][3] = new Queen(colour, this);
        board[rowIndex][4] = new King(colour, this);
        board[rowIndex][5] = new Bishop(colour, this);
        board[rowIndex][6] = new Knight(colour, this);
        board[rowIndex][7] = new Rook(colour, this);
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

    public boolean contains(Coordinates position) {
        int row = position.getRow();
        int col = position.getCol();
        return 0 <= row && row < 8 && 0 <= col && col < 8;
    }

    public boolean squareContainsEnemy(Coordinates square, PlayerColour colour) throws IndexOutOfBoundsException {
        return Optional.ofNullable(get(square))
                .map(piece -> piece.getColour() != colour)
                .orElse(false);
    }

    public boolean squareContainsAlly(Coordinates square, PlayerColour colour) throws IndexOutOfBoundsException {
        return Optional.ofNullable(get(square))
                .map(piece -> piece.getColour() == colour)
                .orElse(false);
    }
}
