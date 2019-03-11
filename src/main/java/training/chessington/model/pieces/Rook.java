package training.chessington.model.pieces;

import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.ArrayList;
import java.util.List;

public class Rook extends AbstractPiece {
    public Rook(PlayerColour colour, Board board) {
        super(PieceType.ROOK, colour, board);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from) {
        List<Move> allowedMoves = new ArrayList<>();
        allowedMoves.addAll(super.getVerticalMoves(from));
        allowedMoves.addAll(super.getHorizontalMoves(from));
        allowedMoves = super.removeInvalidMoves(allowedMoves);
        return allowedMoves;
    }

    protected boolean moveIsValid(Move move) {
        return !board.squareContainsAlly(move.getTo(), this.colour)
                && !board.moveIsBlocked(move);
    }
}
