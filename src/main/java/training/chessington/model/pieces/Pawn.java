package training.chessington.model.pieces;

import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

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
            if (board.get(squareInFront) == null) {
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
        } else if (this.colour == PlayerColour.BLACK && currentSquare.getRow() == 1) {
            destination = currentSquare.plus(2, 0);
        } else { // Not on starting line
            return null;
        }

        Move doubleMove = new Move(currentSquare, destination);
        if (this.moveIsValid(doubleMove)) {
            return doubleMove;
        } else {
            return null;
        }
    }

    private List<Move> getAttackingMoves(Coordinates currentSquare) {
        List<Move> allowedMoves = new ArrayList<>();
        Coordinates squareInFront = this.nextSquareForward(currentSquare);
        try {
            if (board.squareContainsEnemy(squareInFront.plus(0,1), this.colour)) {
                allowedMoves.add(new Move(currentSquare, squareInFront.plus(0, 1)));
            }
            if (board.squareContainsEnemy(squareInFront.plus(0,-1), this.colour)) {
                allowedMoves.add(new Move(currentSquare, squareInFront.plus(0, -1)));
            }
        } catch (IndexOutOfBoundsException e) {}
        return allowedMoves;
    }

    protected boolean moveIsValid(Move move) {
        return !board.squareContainsAlly(move.getTo(), this.colour)
                && !board.squareContainsEnemy(move.getTo(), this.colour)
                && !board.moveIsBlocked(move);
    }
}
