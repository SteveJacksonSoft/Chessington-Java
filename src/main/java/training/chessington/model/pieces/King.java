package training.chessington.model.pieces;

import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.ArrayList;
import java.util.List;

public class King extends AbstractPiece {
    public King(PlayerColour colour, Board board) {
        super(PieceType.KING, colour, board);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from) {
        return new ArrayList<>();
    }

    @Override
    protected boolean moveIsValid(Move move) {
        return true;
    }
}
