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

public class BishopTest {
    
    @Test
    public void bishopCanMoveOnUpRightDiagonal() {
        Board board = Board.empty();
        Bishop bishop = new Bishop(PlayerColour.WHITE, board);
        Coordinates bishopPosition = new Coordinates(2, 2);
        board.placePiece(bishopPosition, bishop);
        Coordinates[] validDestinations = {
                new Coordinates(0, 4),
                new Coordinates(1, 3),
                new Coordinates(3, 1),
                new Coordinates(4, 0),
        };
        List<Move> validMoves = Arrays.stream(validDestinations)
                .map(destination -> new Move(bishopPosition, destination))
                .collect(Collectors.toList());

        List<Move> moves = bishop.getAllowedMoves(bishopPosition);

        assertThat(moves).containsAll(validMoves);
    }

    @Test
    public void bishopCanMoveOnDownRightDiagonal() {
        Board board = Board.empty();
        Bishop bishop = new Bishop(PlayerColour.WHITE, board);
        Coordinates bishopPosition = new Coordinates(6, 4);
        board.placePiece(bishopPosition, bishop);
        Coordinates[] validDestinations = {
                new Coordinates(2, 0),
                new Coordinates(3, 1),
                new Coordinates(4, 2),
                new Coordinates(5, 3),
                new Coordinates(7, 5)
        };
        List<Move> validMoves = Arrays.stream(validDestinations)
                .map(destination -> new Move(bishopPosition, destination))
                .collect(Collectors.toList());

        List<Move> moves = bishop.getAllowedMoves(bishopPosition);

        assertThat(moves).containsAll(validMoves);
    }

    @Test
    public void bishopCannotMoveOffBoard() {
        Board board = Board.empty();
        Bishop bishop = new Bishop(PlayerColour.WHITE, board);
        Coordinates bishopPosition = new Coordinates(2, 2);
        board.placePiece(bishopPosition, bishop);

        List<Move> moves = bishop.getAllowedMoves(bishopPosition);

        for (Move move: moves) {
            assertThat(move.getTo().getRow()).isBetween(0,7);
            assertThat(move.getTo().getCol()).isBetween(0,7);
        }
    }
    
    @Test
    public void bishopCannotMoveToSpaceOccupiedByAlly() {
        Board board = Board.empty();
        Bishop bishop = new Bishop(PlayerColour.WHITE, board);
        Pawn pawn = new Pawn(PlayerColour.WHITE, board);
        Coordinates bishopPosition = new Coordinates(6,5);
        Coordinates pawnPosition = new Coordinates(4, 7);
        board.placePiece(bishopPosition, bishop);
        board.placePiece(pawnPosition, pawn);

        List<Move> moves = bishop.getAllowedMoves(bishopPosition);

        assertThat(moves).doesNotContain(new Move(bishopPosition, pawnPosition));
    }

    @Test
    public void bishopCanMoveToSpaceOccupiedByEnemy() {
        Board board = Board.empty();
        Bishop bishop = new Bishop(PlayerColour.WHITE, board);
        Pawn pawn = new Pawn(PlayerColour.BLACK, board);
        Coordinates bishopPosition = new Coordinates(2, 2);
        Coordinates pawnPosition = new Coordinates(5, 5);
        board.placePiece(bishopPosition, bishop);
        board.placePiece(pawnPosition, pawn);

        List<Move> moves = bishop.getAllowedMoves(bishopPosition);

        assertThat(moves).contains(new Move(bishopPosition, pawnPosition));
    }

    @Test
    public void bishopCannotMoveThroughAllies() {
        Board board = Board.empty();
        Bishop bishop = new Bishop(PlayerColour.WHITE, board);
        Pawn pawn = new Pawn(PlayerColour.WHITE, board);
        Coordinates bishopPosition = new Coordinates(4, 2);
        Coordinates pawnPosition = new Coordinates(5, 1);
        board.placePiece(bishopPosition, bishop);
        board.placePiece(pawnPosition, pawn);

        List<Move> moves = bishop.getAllowedMoves(bishopPosition);

        assertThat(moves).doesNotContain(new Move(bishopPosition, new Coordinates(6, 0)));
    }

    @Test
    public void bishopCannotMoveThroughEnemies() {
        Board board = Board.empty();
        Bishop bishop = new Bishop(PlayerColour.WHITE, board);
        Pawn pawn = new Pawn(PlayerColour.BLACK, board);
        Coordinates bishopPosition = new Coordinates(0, 2);
        Coordinates pawnPosition = new Coordinates(4, 6);
        board.placePiece(bishopPosition, bishop);
        board.placePiece(pawnPosition, pawn);

        List<Move> moves = bishop.getAllowedMoves(bishopPosition);

        assertThat(moves).doesNotContain(new Move(bishopPosition, new Coordinates(5, 7)));
    }

}
