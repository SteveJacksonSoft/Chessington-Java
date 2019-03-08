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

public class KnightTest {

    @Test
    public void knightCanMoveToCorrect8SpacesWhenInCentreOfBoard() {
        Board board = Board.empty();
        Piece knight = new Knight(PlayerColour.WHITE);
        Coordinates position = new Coordinates(4,4);
        board.placePiece(position, knight);

        List<Move> moves = knight.getAllowedMoves(position, board);

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
        Piece knight = new Knight(PlayerColour.WHITE);
        Coordinates position = new Coordinates(4,4);
        board.placePiece(position, knight);

        List<Move> moves = knight.getAllowedMoves(position, board);

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
        Piece knight = new Knight(PlayerColour.WHITE);
        Coordinates position = new Coordinates(7,4);
        board.placePiece(position, knight);

        List<Move> moves = knight.getAllowedMoves(position, board);

        for (Move move: moves) {
            assertThat(move.getTo().getRow()).isLessThan(8);
        }
    }

    @Test
    public void knightCannotMoveOffBoardWhenOnTheEdge() {
        Board board = Board.empty();
        Piece knight = new Knight(PlayerColour.WHITE);
        Coordinates position = new Coordinates(7,4);
        board.placePiece(position, knight);

        List<Move> moves = knight.getAllowedMoves(position, board);

        for (Move move: moves) {
            assertThat(move.getTo().getRow()).isLessThan(8);
        }
    }

    @Test
    public void knightCannotMoveOffBoardWhenNearCorner() {
        Board board = Board.empty();
        Piece knight = new Knight(PlayerColour.WHITE);
        Coordinates position = new Coordinates(0,6);
        board.placePiece(position, knight);

        List<Move> moves = knight.getAllowedMoves(position, board);

        for (Move move: moves) {
            assertThat(move.getTo().getRow()).isGreaterThan(-1);
            assertThat(move.getTo().getCol()).isLessThan(8);
        }
    }

}
