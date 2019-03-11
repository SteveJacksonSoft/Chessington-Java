package training.chessington.model.pieces;

import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.ArrayList;
import java.util.List;

public class Queen extends AbstractPiece {
    public Queen(PlayerColour colour, Board board) {
        super(PieceType.QUEEN, colour, board);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from) {
        List<Move> allowedMoves = new ArrayList<>();
        allowedMoves.addAll(super.getHorizontalMoves(from));
        allowedMoves.addAll(super.getVerticalMoves(from));
        allowedMoves.addAll(super.getDiagonalMoves(from));
        allowedMoves = super.removeInvalidMoves(allowedMoves);
        return allowedMoves;
    }

    @Override
    protected boolean moveIsValid(Move move) {
        return !board.squareContainsAlly(move.getTo(), this.colour)
                && !board.moveIsBlocked(move);
    }
}
