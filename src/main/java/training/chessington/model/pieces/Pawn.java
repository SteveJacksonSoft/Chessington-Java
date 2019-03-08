package training.chessington.model.pieces;

import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends AbstractPiece {
    public Pawn(PlayerColour colour) {
        super(Piece.PieceType.PAWN, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Board board) {
        List<Move> allowedMoves = new ArrayList<>();
        if (this.colour == PlayerColour.WHITE) {
            allowedMoves.add(new Move(from, from.plus(-1, 0)));
            if (from.getRow() == 6) {
                allowedMoves.add(new Move(from, from.plus(-2, 0)));
            }
        } else if (this.colour == PlayerColour.BLACK){
            allowedMoves.add(new Move(from, from.plus(1, 0)));
            if (from.getRow() == 1) {
                allowedMoves.add(new Move(from, from.plus(2, 0)));
            }
        }
        return allowedMoves;
    }
}
