package training.chessington.model.pieces;

import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pawn extends AbstractPiece {
    public Pawn(PlayerColour colour) {
        super(Piece.PieceType.PAWN, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Board board) {
        List<Move> allowedMoves = new ArrayList<>();
        Coordinates[] squaresInFront = this.squaresInFront(from);
        if (board.contains(squaresInFront[0])) {
            if (board.get(squaresInFront[0]) == null) {
                allowedMoves.add(new Move(from, squaresInFront[0]));
            }
        }
        if ((this.colour == PlayerColour.WHITE && from.getRow() == 6)
                || (this.colour == PlayerColour.BLACK && from.getRow() == 1)) {
            allowedMoves.add(new Move(from, squaresInFront[1]));
        }
        return allowedMoves;
    }

    private Coordinates[] squaresInFront(Coordinates currentSquare) {
        if (this.colour == PlayerColour.BLACK) {
            return new Coordinates[]{currentSquare.plus(1, 0), currentSquare.plus(2, 0)};
        } else {
            return new Coordinates[]{currentSquare.plus(-1, 0), currentSquare.plus(-2, 0)};
        }
    }
}
