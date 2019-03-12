package training.chessington.model.pieces;

import org.junit.Test;
import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static training.chessington.model.pieces.PieceAssert.*;
import static org.assertj.core.api.Assertions.*;

public class QueenTest {

    @Test
    public void queenCanMoveHorizontally() {
        Board board = Board.empty();
        Queen queen = new Queen(PlayerColour.WHITE, board);
        Coordinates queenPosition = new Coordinates(3, 2);
        board.placePiece(queenPosition, queen);
        Coordinates[] validDestinations = {
                new Coordinates(3, 0),
                new Coordinates(3, 1),
                new Coordinates(3, 3),
                new Coordinates(3, 4),
                new Coordinates(3, 5),
                new Coordinates(3, 6),
                new Coordinates(3, 7)
        };
        List<Move> validMoves = Arrays.stream(validDestinations)
                .map(destination -> new Move(queenPosition, destination))
                .collect(Collectors.toList());

        List<Move> moves = queen.getAllowedMoves(queenPosition);

        assertThat(moves).containsAll(validMoves);
    }

    @Test
    public void queenCanMoveVertically() {
        Board board = Board.empty();
        Queen queen = new Queen(PlayerColour.WHITE, board);
        Coordinates queenPosition = new Coordinates(6, 4);
        board.placePiece(queenPosition, queen);
        Coordinates[] validDestinations = {
                new Coordinates(0, 4),
                new Coordinates(1, 4),
                new Coordinates(2, 4),
                new Coordinates(3, 4),
                new Coordinates(4, 4),
                new Coordinates(5, 4),
                new Coordinates(7, 4)
        };
        List<Move> validMoves = Arrays.stream(validDestinations)
                .map(destination -> new Move(queenPosition, destination))
                .collect(Collectors.toList());

        List<Move> moves = queen.getAllowedMoves(queenPosition);

        assertThat(moves).containsAll(validMoves);
    }

    @Test
    public void queenCanMoveOnDownRightDiagonal() {
        Board board = Board.empty();
        Queen queen = new Queen(PlayerColour.WHITE, board);
        Coordinates queenPosition = new Coordinates(6, 4);
        board.placePiece(queenPosition, queen);
        Coordinates[] validDestinations = {
                new Coordinates(2, 0),
                new Coordinates(3, 1),
                new Coordinates(4, 2),
                new Coordinates(5, 3),
                new Coordinates(7, 5)
        };
        List<Move> validMoves = Arrays.stream(validDestinations)
                .map(destination -> new Move(queenPosition, destination))
                .collect(Collectors.toList());

        List<Move> moves = queen.getAllowedMoves(queenPosition);

        assertThat(moves).containsAll(validMoves);
    }

    @Test
    public void queenCanMoveOnUpRightDiagonal() {
        Board board = Board.empty();
        Queen queen = new Queen(PlayerColour.WHITE, board);
        Coordinates queenPosition = new Coordinates(2, 2);
        board.placePiece(queenPosition, queen);
        Coordinates[] validDestinations = {
                new Coordinates(0, 4),
                new Coordinates(1, 3),
                new Coordinates(3, 1),
                new Coordinates(4, 0),
        };
        List<Move> validMoves = Arrays.stream(validDestinations)
                .map(destination -> new Move(queenPosition, destination))
                .collect(Collectors.toList());

        List<Move> moves = queen.getAllowedMoves(queenPosition);

        assertThat(moves).containsAll(validMoves);
    }

    @Test
    public void queenCannotMoveOffBoard() {
        Board board = Board.empty();
        Queen queen = new Queen(PlayerColour.WHITE, board);
        Coordinates queenPosition = new Coordinates(2, 2);
        board.placePiece(queenPosition, queen);

        List<Move> moves = queen.getAllowedMoves(queenPosition);

        for (Move move: moves) {
            assertThat(move.getTo().getRow()).isBetween(0,7);
            assertThat(move.getTo().getCol()).isBetween(0,7);
        }
    }

    @Test
    public void queenCannotMoveToSpaceOccupiedByAlly() {
        Board board = Board.empty();
        Queen queen = new Queen(PlayerColour.WHITE, board);
        Pawn pawn = new Pawn(PlayerColour.WHITE, board);
        Coordinates queenPosition = new Coordinates(2, 2);
        Coordinates pawnPosition = new Coordinates(0, 2);
        board.placePiece(queenPosition, queen);
        board.placePiece(pawnPosition, pawn);

        List<Move> moves = queen.getAllowedMoves(queenPosition);

        assertThat(moves).doesNotContain(new Move(queenPosition, pawnPosition));
    }

    @Test
    public void queenCanMoveToSpaceOccupiedByEnemy() {
        Board board = Board.empty();
        Queen queen = new Queen(PlayerColour.WHITE, board);
        Pawn pawn = new Pawn(PlayerColour.BLACK, board);
        Coordinates queenPosition = new Coordinates(2, 2);
        Coordinates pawnPosition = new Coordinates(0, 2);
        board.placePiece(queenPosition, queen);
        board.placePiece(pawnPosition, pawn);

        List<Move> moves = queen.getAllowedMoves(queenPosition);

        assertThat(moves).contains(new Move(queenPosition, pawnPosition));
    }

    @Test
    public void queenCannotMoveThroughAllies() {
        Board board = Board.empty();
        Queen queen = new Queen(PlayerColour.WHITE, board);
        Pawn pawn = new Pawn(PlayerColour.WHITE, board);
        Coordinates queenPosition = new Coordinates(4, 2);
        Coordinates pawnPosition = new Coordinates(5, 1);
        board.placePiece(queenPosition, queen);
        board.placePiece(pawnPosition, pawn);

        List<Move> moves = queen.getAllowedMoves(queenPosition);

        assertThat(moves).doesNotContain(new Move(queenPosition, new Coordinates(6, 0)));
    }

    @Test
    public void queenCannotMoveThroughEnemies() {
        Board board = Board.empty();
        Queen queen = new Queen(PlayerColour.WHITE, board);
        Pawn pawn = new Pawn(PlayerColour.BLACK, board);
        Coordinates queenPosition = new Coordinates(6, 6);
        Coordinates pawnPosition = new Coordinates(2, 6);
        board.placePiece(queenPosition, queen);
        board.placePiece(pawnPosition, pawn);

        List<Move> moves = queen.getAllowedMoves(queenPosition);

        assertThat(moves).doesNotContain(new Move(queenPosition, new Coordinates(1, 6)));
    }

}
