package training.chessington.model.pieces;

import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Pawn extends AbstractPiece {
    public Pawn(PlayerColour colour) {
        super(Piece.PieceType.PAWN, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Board board) {
        List<Move> allowedMoves = new ArrayList<>();

        Optional.ofNullable(this.getStandardMove(from, board))
                .ifPresent(allowedMoves::add);
        allowedMoves.addAll(this.getAttackingMoves(from, board));
        Optional.ofNullable(this.getDoubleMove(from, board))
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

    private Move getStandardMove(Coordinates currentSquare, Board board) {
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

    private Move getDoubleMove(Coordinates currentSquare, Board board) {
        if (this.colour == PlayerColour.WHITE && currentSquare.getRow() == 6) {
            Coordinates destination = currentSquare.plus(-2, 0);
            if (board.get(destination) == null) {
                return new Move(currentSquare, currentSquare.plus(-2, 0));
            }
        } else if (this.colour == PlayerColour.BLACK && currentSquare.getRow() == 1) {
            Coordinates destination = currentSquare.plus(2, 0);
            if (board.get(destination) == null) {
                return new Move(currentSquare, currentSquare.plus(2, 0));
            }
        }
        return null;
    }

    private List<Move> getAttackingMoves(Coordinates currentSquare, Board board) {
        List<Move> allowedMoves = new ArrayList<>();
        Coordinates squareInFront = this.nextSquareForward(currentSquare);
        try {
            if (this.squareContainsEnemy(squareInFront.plus(0,1), board)) {
                allowedMoves.add(new Move(currentSquare, squareInFront.plus(0, 1)));
            }
            if (this.squareContainsEnemy(squareInFront.plus(0,-1), board)) {
                allowedMoves.add(new Move(currentSquare, squareInFront.plus(0, -1)));
            }
        } catch (IndexOutOfBoundsException e) {}
        return allowedMoves;
    }

    private boolean squareContainsEnemy(Coordinates square, Board board) throws IndexOutOfBoundsException {
        return Optional.ofNullable(board.get(square))
                .map(piece ->
                        (piece.getColour() == PlayerColour.WHITE && this.colour == PlayerColour.BLACK) ||
                                (piece.getColour() == PlayerColour.BLACK && this.colour == PlayerColour.WHITE)
                )
                .orElse(false);
    }
}
