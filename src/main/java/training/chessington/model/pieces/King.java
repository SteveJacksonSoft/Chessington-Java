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
        List<Move> allowedMoves = new ArrayList<>();
        for (int rowDiff = -1; rowDiff <= 1; rowDiff++) {
            for (int colDiff = -1; colDiff <= 1; colDiff++) {
                if ( (rowDiff != 0 || colDiff != 0)
                        && this.squareIsValidDestination(from.plus(rowDiff, colDiff)) ) {
                    allowedMoves.add(new Move(from, from.plus(rowDiff, colDiff)));
                }
            }
        }
        return allowedMoves;
    }

    private boolean squareIsValidDestination(Coordinates square) {
        try {
            return !board.squareContainsAlly(square, super.colour);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }
}
