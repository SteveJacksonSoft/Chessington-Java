package training.chessington.model.pieces;

import training.chessington.model.*;

public abstract class AbstractPiece implements Piece {

    protected final Piece.PieceType type;
    protected final PlayerColour colour;
    protected final Board board;
    protected final MoveUtil moveUtil;

    protected AbstractPiece(Piece.PieceType type, PlayerColour colour, Board board) {
        this.type = type;
        this.colour = colour;
        this.board = board;
        this.moveUtil = new MoveUtil(board);
    }

    @Override
    public Piece.PieceType getType() {
        return type;
    }

    @Override
    public PlayerColour getColour() {
        return colour;
    }

    @Override
    public String toString() {
        return colour.toString() + " " + type.toString();
    }
}
