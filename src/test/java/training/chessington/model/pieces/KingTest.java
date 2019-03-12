package training.chessington.model.pieces;

import org.junit.Test;
import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static training.chessington.model.pieces.PieceAssert.*;
import static org.assertj.core.api.Assertions.*;

public class KingTest {

    @Test
    public void kingCanMoveInAnyDirection() {
        Board board = Board.empty();
        King king = new King(PlayerColour.WHITE, board);
        Coordinates kingPosition = new Coordinates(4, 4);
        board.placePiece(kingPosition, king);
        List<Move> validMoves = Arrays.stream(new Coordinates[] {
                new Coordinates(3, 3),
                new Coordinates(4, 3),
                new Coordinates(5, 3),
                new Coordinates(3, 4),
                new Coordinates(5, 4),
                new Coordinates(3, 5),
                new Coordinates(4, 5),
                new Coordinates(5, 5)
        })
                .map(destination -> new Move(kingPosition, destination))
                .collect(Collectors.toList());

        List<Move> moves = king.getAllowedMoves(kingPosition);

        assertThat(moves).containsAll(validMoves);
    }

    @Test
    public void kingCanMoveOnlyOneSquare() {
        Board board = Board.empty();
        King king = new King(PlayerColour.WHITE, board);
        Coordinates kingPosition = new Coordinates(2, 5);
        board.placePiece(kingPosition, king);

        List<Move> moves = king.getAllowedMoves(kingPosition);

        for (Move move: moves) {
            assertThat(move.getTo().getRow()).isBetween(1, 3);
            assertThat(move.getTo().getCol()).isBetween(4, 6);
        }
    }

    @Test
    public void kingCannotMoveToSquareOccupiedByAlly() {
        Board board = Board.empty();
        King king = new King(PlayerColour.WHITE, board);
        Pawn pawn = new Pawn(PlayerColour.WHITE, board);
        Coordinates kingPosition = new Coordinates(2, 5);
        Coordinates pawnPosition = new Coordinates(1, 5);
        board.placePiece(kingPosition, king);
        board.placePiece(pawnPosition, pawn);

        List<Move> moves = king.getAllowedMoves(kingPosition);

        assertThat(moves).doesNotContain(new Move(kingPosition, pawnPosition));
    }

    @Test
    public void kingCanMoveToSquareOccupiedByEnemy() {
        Board board = Board.empty();
        King king = new King(PlayerColour.WHITE, board);
        Pawn pawn = new Pawn(PlayerColour.BLACK, board);
        Coordinates kingPosition = new Coordinates(2, 5);
        Coordinates pawnPosition = new Coordinates(1, 5);
        board.placePiece(kingPosition, king);
        board.placePiece(pawnPosition, pawn);

        List<Move> moves = king.getAllowedMoves(kingPosition);

        assertThat(moves).contains(new Move(kingPosition, pawnPosition));
    }

    @Test
    public void kingCannotMoveOffBoard() {
        Board board = Board.empty();
        King king = new King(PlayerColour.WHITE, board);
        Coordinates kingPosition = new Coordinates(0, 0);
        board.placePiece(kingPosition, king);

        List<Move> moves = king.getAllowedMoves(kingPosition);

        for (Move move: moves) {
            assertThat(move.getTo().getRow()).isBetween(0, 7);
            assertThat(move.getTo().getCol()).isBetween(0, 7);
        }
    }
}
