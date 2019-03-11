package training.chessington.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class MoveUtil {
    private final Board board;

    public MoveUtil(Board board) {
        this.board = board;
    }

    public List<Move> getDiagonalMoves(Coordinates from, PlayerColour colour) {
        List<Function<Coordinates, Coordinates>> directionalSteps = new ArrayList<>();
        directionalSteps.add((initialPosition) -> initialPosition.plus(1, 1));
        directionalSteps.add((initialPosition) -> initialPosition.plus(1, -1));
        directionalSteps.add((initialPosition) -> initialPosition.plus(-1, -1));
        directionalSteps.add((initialPosition) -> initialPosition.plus(-1, 1));
        return getLinearMovesManyDirections(from, directionalSteps, colour);
    }

    public List<Move> getHorizontalMoves(Coordinates from, PlayerColour colour) {
        List<Function<Coordinates, Coordinates>> directionalSteps = new ArrayList<>();
        directionalSteps.add((initialPosition) -> initialPosition.plus(0, 1));
        directionalSteps.add((initialPosition) -> initialPosition.plus(0, -1));
        return getLinearMovesManyDirections(from, directionalSteps, colour);
    }

    public List<Move> getVerticalMoves(Coordinates from, PlayerColour colour) {
        List<Function<Coordinates, Coordinates>> directionalSteps = new ArrayList<>();
        directionalSteps.add((initialPosition) -> initialPosition.plus(1, 0));
        directionalSteps.add((initialPosition) -> initialPosition.plus(-1, 0));
        return getLinearMovesManyDirections(from, directionalSteps, colour);
    }

    private List<Move> getLinearMovesManyDirections(Coordinates initialSquare,
                                                    Iterable<Function<Coordinates, Coordinates>> directionalSteps,
                                                    PlayerColour colour) {
        List<Move> linearMoves = new ArrayList<>();
        for (Function<Coordinates, Coordinates> directionalStep: directionalSteps) {
            linearMoves.addAll(getLinearMovesOneDirection(initialSquare, directionalStep, colour));
        }
        return linearMoves;
    }

    private List<Move> getLinearMovesOneDirection(Coordinates initialSquare,
                                Function<Coordinates, Coordinates> directionalStep,
                                PlayerColour colour) {
        List<Move> moves = new ArrayList<>();
        Coordinates nextSquare = directionalStep.apply(initialSquare);
        if (nextSquare.equals(initialSquare)) {
            return moves;
        }
        while (board.contains(nextSquare)) {
            if (board.squareContainsAlly(nextSquare, colour)) {
                return moves;
            } else if (board.squareContainsEnemy(nextSquare, colour)) {
                moves.add(new Move(initialSquare, nextSquare));
                return moves;
            } else {
                moves.add(new Move(initialSquare, nextSquare));
                nextSquare = directionalStep.apply(nextSquare);
            }
        }
        return moves;
    }
}
