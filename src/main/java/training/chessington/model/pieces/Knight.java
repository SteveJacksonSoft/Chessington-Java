package training.chessington.model.pieces;

import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Knight extends AbstractPiece {
    public Knight(PlayerColour colour) {
        super(PieceType.KNIGHT, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Board board) {
        List<Coordinates> validDestinations = Arrays.asList(
                from.plus(2, 1),
                from.plus(2, -1),
                from.plus(1, 2),
                from.plus(1, -2),
                from.plus(-1, 2),
                from.plus(-1, -2),
                from.plus(-2, 1),
                from.plus(-2, -1)
        );
        return validDestinations.stream()
                .filter(board::contains)
                .filter(destination -> !board.squareContainsAlly(destination, this.colour))
                .map(destination -> new Move(from, destination))
                .collect(Collectors.toList());
    }
}
