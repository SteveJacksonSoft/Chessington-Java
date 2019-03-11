package training.chessington.model.pieces;

import training.chessington.model.*;

import java.util.ArrayList;
import java.util.List;

public class Queen extends AbstractPiece {
    public Queen(PlayerColour colour, Board board) {
        super(PieceType.QUEEN, colour, board);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from) {
        List<Move> allowedMoves = new ArrayList<>();
        allowedMoves.addAll(moveUtil.getHorizontalMoves(from, super.colour));
        allowedMoves.addAll(moveUtil.getVerticalMoves(from, super.colour));
        allowedMoves.addAll(moveUtil.getDiagonalMoves(from, super.colour));
        return allowedMoves;
    }
}
