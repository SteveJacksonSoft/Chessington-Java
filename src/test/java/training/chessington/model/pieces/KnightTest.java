package training.chessington.model.pieces;

import org.junit.Test;
import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

public class KnightTest {

    @Test
    public void knightCanMoveToCorrect8SpacesWhenInCentreOfBoard() {
        Board board = Board.empty();
        Piece knight = new Knight(PlayerColour.WHITE, board);
        Coordinates position = new Coordinates(4,4);
        board.placePiece(position, knight);

        List<Move> moves = knight.getAllowedMoves(position);

        List<Coordinates> correctDestinations = Arrays.asList(
                new Coordinates(5, 2),
                new Coordinates(6, 3),
                new Coordinates(6, 5),
                new Coordinates(5, 6),
                new Coordinates(3, 2),
                new Coordinates(2, 3),
                new Coordinates(2, 5),
                new Coordinates(3, 6)
        );
        List<Move> validMoves = correctDestinations.stream().map(destination -> new Move(position, destination)).collect(Collectors.toList());
        assertThat(moves).containsAll(validMoves);
    }

    @Test
    public void knightCannotMoveOutsideOfLShape() {
        Board board = Board.empty();
        Piece knight = new Knight(PlayerColour.WHITE, board);
        Coordinates position = new Coordinates(4,4);
        board.placePiece(position, knight);

        List<Move> moves = knight.getAllowedMoves(position);

        List<Coordinates> correctDestinations = Arrays.asList(
                new Coordinates(5, 2),
                new Coordinates(6, 3),
                new Coordinates(6, 5),
                new Coordinates(5, 6),
                new Coordinates(3, 2),
                new Coordinates(2, 3),
                new Coordinates(2, 5),
                new Coordinates(3, 6)
        );
        List<Move> validMoves = correctDestinations.stream().map(destination -> new Move(position, destination)).collect(Collectors.toList());
        assertThat(validMoves).containsAll(moves);
    }

    @Test
    public void knightCannotMoveMoreThan2SpacesInOneDirection() {
        Board board = Board.empty();
        Piece knight = new Knight(PlayerColour.WHITE, board);
        Coordinates position = new Coordinates(3,4);
        board.placePiece(position, knight);

        List<Move> moves = knight.getAllowedMoves(position);

        for (Move move: moves) {
            assertThat(move.getTo().getRow()).isBetween(1, 5);
            assertThat(move.getTo().getCol()).isBetween(2, 6);
        }
    }

    @Test
    public void knightCannotMoveOffBoardWhenOnTheEdge() {
        Board board = Board.empty();
        Piece knight = new Knight(PlayerColour.WHITE, board);
        Coordinates position = new Coordinates(7,4);
        board.placePiece(position, knight);

        List<Move> moves = knight.getAllowedMoves(position);

        for (Move move: moves) {
            assertThat(move.getTo().getRow()).isLessThan(8);
        }
    }

    @Test
    public void knightCannotMoveOffBoardWhenNearCorner() {
        Board board = Board.empty();
        Piece knight = new Knight(PlayerColour.WHITE, board);
        Coordinates position = new Coordinates(0,6);
        board.placePiece(position, knight);

        List<Move> moves = knight.getAllowedMoves(position);

        for (Move move: moves) {
            assertThat(move.getTo().getRow()).isGreaterThan(-1);
            assertThat(move.getTo().getCol()).isLessThan(8);
        }
    }

    @Test
    public void knightCannotMoveToSquareOccupiedBySameColourPiece() {
        Board board = Board.empty();
        Piece knight = new Knight(PlayerColour.WHITE, board);
        Coordinates knightPosition = new Coordinates(0,6);
        Piece pawn = new Pawn(PlayerColour.WHITE, board);
        Coordinates pawnPosition = new Coordinates(2,5);
        board.placePiece(knightPosition, knight);
        board.placePiece(pawnPosition, pawn);

        List<Move> moves = knight.getAllowedMoves(knightPosition);

        assertThat(moves).doesNotContain(new Move(knightPosition, pawnPosition));
    }

    @Test
    public void knightCanMoveToSquareOccupiedByDifferentColourPiece() {
        Board board = Board.empty();
        Piece knight = new Knight(PlayerColour.WHITE, board);
        Coordinates knightPosition = new Coordinates(4,2);
        Piece pawn = new Pawn(PlayerColour.BLACK, board);
        Coordinates pawnPosition = new Coordinates(5,0);
        board.placePiece(knightPosition, knight);
        board.placePiece(pawnPosition, pawn);

        List<Move> moves = knight.getAllowedMoves(knightPosition);

        assertThat(moves).contains(new Move(knightPosition, pawnPosition));
    }

}
