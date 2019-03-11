package training.chessington.model.pieces;

import training.chessington.model.*;

import java.util.ArrayList;
import java.util.List;

public class Rook extends AbstractPiece {
    public Rook(PlayerColour colour, Board board) {
        super(PieceType.ROOK, colour, board);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from) {
        List<Move> allowedMoves = new ArrayList<>();
        allowedMoves.addAll(moveUtil.getVerticalMoves(from, super.colour));
        allowedMoves.addAll(moveUtil.getHorizontalMoves(from, super.colour));
        return allowedMoves;
    }
}
