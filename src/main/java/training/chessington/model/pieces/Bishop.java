package training.chessington.model.pieces;

import training.chessington.model.*;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends AbstractPiece {
    public Bishop(PlayerColour colour, Board board) {
        super(PieceType.BISHOP, colour, board);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from) {
        return new ArrayList<>(moveUtil.getDiagonalMoves(from, super.colour));
    }
}
