package training.chessington.model.pieces;

import training.chessington.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Pawn extends AbstractPiece {
    public Pawn(PlayerColour colour, Board board) {
        super(Piece.PieceType.PAWN, colour, board);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from) {
        List<Move> allowedMoves = new ArrayList<>();

        Optional.ofNullable(this.getStandardMove(from))
                .ifPresent(allowedMoves::add);
        allowedMoves.addAll(this.getAttackingMoves(from));
        Optional.ofNullable(this.getDoubleMove(from))
                .ifPresent(allowedMoves::add);
        return allowedMoves;
    }

    private Coordinates nextSquareForward(Coordinates currentSquare) {
        if (this.colour == PlayerColour.BLACK) {
            return currentSquare.plus(1, 0);
        } else {
            return currentSquare.plus(-1, 0);
        }
    }

    private Move getStandardMove(Coordinates currentSquare) {
        try {
            Coordinates squareInFront = this.nextSquareForward(currentSquare);
            if (super.board.get(squareInFront) == null) {
                return new Move(currentSquare, squareInFront);
            } else {
                return null;
            }
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    private Move getDoubleMove(Coordinates currentSquare) {
        Coordinates destination;

        if (this.colour == PlayerColour.WHITE && currentSquare.getRow() == 6) {
            destination = currentSquare.plus(-2, 0);
            if (board.get(currentSquare.plus(-1, 0)) == null && board.get(destination) == null) {
                return new Move(currentSquare, destination);
            }
        } else if (this.colour == PlayerColour.BLACK && currentSquare.getRow() == 1) {
            destination = currentSquare.plus(2, 0);
            if (board.get(currentSquare.plus(1, 0)) == null && board.get(destination) == null) {
                return new Move(currentSquare, destination);
            }
        }

        return null;
    }

    private List<Move> getAttackingMoves(Coordinates currentSquare) {
        List<Move> allowedMoves = new ArrayList<>();
        Coordinates squareInFront = this.nextSquareForward(currentSquare);
        try {
            if (super.board.squareContainsEnemy(squareInFront.plus(0,1), this.colour)) {
                allowedMoves.add(new Move(currentSquare, squareInFront.plus(0, 1)));
            }
            if (super.board.squareContainsEnemy(squareInFront.plus(0,-1), this.colour)) {
                allowedMoves.add(new Move(currentSquare, squareInFront.plus(0, -1)));
            }
        } catch (IndexOutOfBoundsException e) {}
        return allowedMoves;
    }
}
