package training.chessington.model.pieces;

import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class AbstractPiece implements Piece {

    protected final Piece.PieceType type;
    protected final PlayerColour colour;
    protected final Board board;

    protected AbstractPiece(Piece.PieceType type, PlayerColour colour, Board board) {
        this.type = type;
        this.colour = colour;
        this.board = board;
    }

    @Override
    public Piece.PieceType getType() {
        return type;
    }

    @Override
    public PlayerColour getColour() {
        return colour;
    }

    @Override
    public String toString() {
        return colour.toString() + " " + type.toString();
    }

    protected List<Move> getDiagonalMoves(Coordinates from) {
        List<Function<Coordinates, Coordinates>> directionVectors = new ArrayList<>();
        directionVectors.add((initialPosition) -> initialPosition.plus(1, 1));
        directionVectors.add((initialPosition) -> initialPosition.plus(1, -1));
        directionVectors.add((initialPosition) -> initialPosition.plus(-1, -1));
        directionVectors.add((initialPosition) -> initialPosition.plus(-1, 1));
        return this.getLinearMovesManyDirections(from, directionVectors);
    }

    protected List<Move> getHorizontalMoves(Coordinates from) {
        List<Function<Coordinates, Coordinates>> directionVectors = new ArrayList<>();
        directionVectors.add((initialPosition) -> initialPosition.plus(0, 1));
        directionVectors.add((initialPosition) -> initialPosition.plus(0, -1));
        return this.getLinearMovesManyDirections(from, directionVectors);
    }

    protected List<Move> getVerticalMoves(Coordinates from) {
        List<Function<Coordinates, Coordinates>> directionVectors = new ArrayList<>();
        directionVectors.add((initialPosition) -> initialPosition.plus(1, 0));
        directionVectors.add((initialPosition) -> initialPosition.plus(-1, 0));
        return this.getLinearMovesManyDirections(from, directionVectors);
    }

    private List<Move> getLinearMovesManyDirections(Coordinates initial, Iterable<Function<Coordinates, Coordinates>> directionVectors) {
        List<Move> linearMoves = new ArrayList<>();
        for (Function<Coordinates, Coordinates> directionVector: directionVectors) {
            linearMoves.addAll(this.getLinearMovesOneDirection(initial, directionVector));
        }
        return linearMoves;
    }

    private List<Move> getLinearMovesOneDirection(Coordinates initialPosition, Function<Coordinates, Coordinates> directionVector) {
        List<Move> linearMoves = new ArrayList<>();
        Coordinates nextPosition = directionVector.apply(initialPosition);
        while (board.contains(nextPosition)) {
            linearMoves.add(new Move(initialPosition, nextPosition));
            nextPosition = directionVector.apply(nextPosition);
        }
        return linearMoves;
    }

    protected List<Move> removeInvalidMoves(List<Move> moveList) {
        List<Move> movesToRemove = new ArrayList<>();
        moveList.forEach(move -> {
            if (!this.moveIsValid(move)) {
                movesToRemove.add(move);
            }
        });
        moveList.removeAll(movesToRemove);
        return moveList;
    }

    protected abstract boolean moveIsValid(Move move);
}
