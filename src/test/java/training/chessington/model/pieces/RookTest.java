package training.chessington.model.pieces;

import org.junit.Test;
import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

public class RookTest {

    @Test
    public void rookCanMoveHorizontally() {
        Board board = Board.empty();
        Piece rook = new Rook(PlayerColour.WHITE, board);
        Coordinates position = new Coordinates(2, 6);
        board.placePiece(position, rook);
        List<Coordinates> validDestinations = new ArrayList<>();
        for (int col = 0; col < 8; col++) {
            if (col != 6) {
                validDestinations.add(new Coordinates(2, col));
            }
        }
        List<Move> validMoves = validDestinations.stream()
                .map(destination -> new Move(position, destination))
                .collect(Collectors.toList());

        List<Move> moves = rook.getAllowedMoves(position);

        assertThat(moves).containsAll(validMoves);
    }

    @Test
    public void rookCanMoveVertically() {
        Board board = Board.empty();
        Piece rook = new Rook(PlayerColour.WHITE, board);
        Coordinates position = new Coordinates(2, 6);
        board.placePiece(position, rook);
        List<Coordinates> validDestinations = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            if (row != 2) {
                validDestinations.add(new Coordinates(row, 6));
            }
        }
        List<Move> validMoves = validDestinations.stream()
                .map(destination -> new Move(position, destination))
                .collect(Collectors.toList());

        List<Move> moves = rook.getAllowedMoves(position);

        assertThat(moves).containsAll(validMoves);
    }

    @Test
    public void rookCannotMoveOffTheBoard() {
        Board board = Board.empty();
        Piece rook = new Rook(PlayerColour.WHITE, board);
        Coordinates position = new Coordinates(5, 3);
        board.placePiece(position, rook);

        List<Move> moves = rook.getAllowedMoves(position);

        for (Move move: moves) {
            assertThat(move.getTo().getRow()).isBetween(0, 7);
            assertThat(move.getTo().getCol()).isBetween(0, 7);
        }
    }

    @Test
    public void rookCannotMoveToSpaceOccupiedByAlly() {
        Board board = Board.empty();
        Piece rook = new Rook(PlayerColour.WHITE, board);
        Piece pawn = new Rook(PlayerColour.WHITE, board);
        Coordinates rookPosition = new Coordinates(5, 3);
        Coordinates pawnPosition = new Coordinates(1, 3);
        board.placePiece(rookPosition, rook);
        board.placePiece(pawnPosition, pawn);

        List<Move> moves = rook.getAllowedMoves(rookPosition);

        assertThat(moves).doesNotContain(new Move(rookPosition, pawnPosition));
    }

    @Test
    public void rookCanMoveToSpaceOccupiedByEnemy() {
        Board board = Board.empty();
        Piece rook = new Rook(PlayerColour.WHITE, board);
        Piece pawn = new Pawn(PlayerColour.BLACK, board);
        Coordinates rookPosition = new Coordinates(5, 3);
        Coordinates pawnPosition = new Coordinates(1, 3);
        board.placePiece(rookPosition, rook);
        board.placePiece(pawnPosition, pawn);

        List<Move> moves = rook.getAllowedMoves(rookPosition);

        assertThat(moves).contains(new Move(rookPosition, pawnPosition));
    }

    @Test
    public void rookCannotPassThroughEnemies() {
        Board board = Board.empty();
        Piece rook = new Rook(PlayerColour.WHITE, board);
        Piece pawn = new Rook(PlayerColour.BLACK, board);
        Coordinates rookPosition = new Coordinates(5, 3);
        Coordinates pawnPosition = new Coordinates(1, 3);
        board.placePiece(rookPosition, rook);
        board.placePiece(pawnPosition, pawn);

        List<Move> moves = rook.getAllowedMoves(rookPosition);

        assertThat(moves).doesNotContain(new Move(rookPosition, new Coordinates(0, 3)));
    }

    @Test
    public void rookCannotPassThroughAllies() {
        Board board = Board.empty();
        Piece rook = new Rook(PlayerColour.WHITE, board);
        Piece pawn = new Rook(PlayerColour.WHITE, board);
        Coordinates rookPosition = new Coordinates(1, 5);
        Coordinates pawnPosition = new Coordinates(1, 3);
        board.placePiece(rookPosition, rook);
        board.placePiece(pawnPosition, pawn);

        List<Move> moves = rook.getAllowedMoves(rookPosition);

        assertThat(moves).doesNotContain(new Move(rookPosition, new Coordinates(1, 0)));
    }
}
