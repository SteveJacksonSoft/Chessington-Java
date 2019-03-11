package training.chessington.model.pieces;

import org.junit.Test;
import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PawnTest {
    @Test
    public void whitePawnCanMoveUpOneSquare() {
        // Arrange
        Board board = Board.empty();
        Piece pawn = new Pawn(PlayerColour.WHITE, board);
        Coordinates coords = new Coordinates(6, 4);
        board.placePiece(coords, pawn);

        // Act
        List<Move> moves = pawn.getAllowedMoves(coords);

        // Assert
        assertThat(moves).contains(new Move(coords, coords.plus(-1, 0)));
    }

    @Test
    public void blackPawnCanMoveDownOneSquare() {
        // Arrange
        Board board = Board.empty();
        Piece pawn = new Pawn(PlayerColour.BLACK, board);
        Coordinates coords = new Coordinates(1, 4);
        board.placePiece(coords, pawn);

        // Act
        List<Move> moves = pawn.getAllowedMoves(coords);

        // Assert
        assertThat(moves).contains(new Move(coords, coords.plus(1, 0)));
    }

    @Test
    public void whitePawnCanMoveUpTwoSquaresIfNotMoved() {
        // Arrange
        Board board = Board.empty();
        Piece pawn = new Pawn(PlayerColour.WHITE, board);
        Coordinates coords = new Coordinates(6, 4);
        board.placePiece(coords, pawn);

        // Act
        List<Move> moves = pawn.getAllowedMoves(coords);

        // Assert
        assertThat(moves).contains(new Move(coords, coords.plus(-2, 0)));
    }

    @Test
    public void blackPawnCanMoveDownTwoSquaresIfNotMoved() {
        // Arrange
        Board board = Board.empty();
        Piece pawn = new Pawn(PlayerColour.BLACK, board);
        Coordinates coords = new Coordinates(1, 4);
        board.placePiece(coords, pawn);

        // Act
        List<Move> moves = pawn.getAllowedMoves(coords);

        // Assert
        assertThat(moves).contains(new Move(coords, coords.plus(2, 0)));
    }

    @Test
    public void whitePawnCannotMoveUpTwoSquaresIfDestinationBlocked() {

        Board board = Board.empty();
        Piece pawn = new Pawn(PlayerColour.WHITE, board);
        Piece rook = new Rook(PlayerColour.BLACK, board);
        Coordinates pawnPos = new Coordinates(6,2);
        Coordinates rookPos = new Coordinates(4, 2);
        board.placePiece(pawnPos, pawn);
        board.placePiece(rookPos, rook);

        List<Move> moves = pawn.getAllowedMoves(pawnPos);

        assertThat(moves).doesNotContain(new Move(pawnPos, rookPos));

    }

    @Test
    public void blackPawnCannotMoveDownTwoSquaresIfDestinationBlocked() {

        Board board = Board.empty();
        Piece pawn = new Pawn(PlayerColour.BLACK, board);
        Piece rook = new Rook(PlayerColour.WHITE, board);
        Coordinates pawnPos = new Coordinates(1,2);
        Coordinates rookPos = new Coordinates(3, 2);
        board.placePiece(pawnPos, pawn);
        board.placePiece(rookPos, rook);

        List<Move> moves = pawn.getAllowedMoves(pawnPos);

        assertThat(moves).doesNotContain(new Move(pawnPos, rookPos));

    }

    @Test
    public void whitePawnCannotMoveUpTwoSquaresIfPathBlocked() {

        Board board = Board.empty();
        Piece pawn = new Pawn(PlayerColour.WHITE, board);
        Piece rook = new Rook(PlayerColour.BLACK, board);
        Coordinates pawnPos = new Coordinates(6,2);
        Coordinates rookPos = new Coordinates(5, 2);
        board.placePiece(pawnPos, pawn);
        board.placePiece(rookPos, rook);

        List<Move> moves = pawn.getAllowedMoves(pawnPos);

        assertThat(moves).doesNotContain(new Move(pawnPos, new Coordinates(4, 2)));

    }

    @Test
    public void blackPawnCannotMoveDownTwoSquaresIfPathBlocked() {

        Board board = Board.empty();
        Piece pawn = new Pawn(PlayerColour.BLACK, board);
        Piece rook = new Rook(PlayerColour.WHITE, board);
        Coordinates pawnPos = new Coordinates(1,2);
        Coordinates rookPos = new Coordinates(2, 2);
        board.placePiece(pawnPos, pawn);
        board.placePiece(rookPos, rook);

        List<Move> moves = pawn.getAllowedMoves(pawnPos);

        assertThat(moves).doesNotContain(new Move(pawnPos, new Coordinates(3, 2)));

    }

    @Test
    public void whitePawnCannotMoveUpTwoSquaresIfAlreadyMoved() {
        // Arrange
        Board board = Board.empty();
        Piece pawn = new Pawn(PlayerColour.WHITE, board);
        Coordinates initial = new Coordinates(6, 4);
        board.placePiece(initial, pawn);

        Coordinates moved = initial.plus(-1, 0);
        board.move(initial, moved);

        // Act
        List<Move> moves = pawn.getAllowedMoves(moved);

        // Assert
        assertThat(moves).doesNotContain(new Move(moved, moved.plus(-2, 0)));
    }

    @Test
    public void blackPawnCannotMoveDownTwoSquaresIfAlreadyMoved() {
        // Arrange
        Board board = Board.empty();
        Piece pawn = new Pawn(PlayerColour.BLACK, board);
        Coordinates initial = new Coordinates(1, 4);
        board.placePiece(initial, pawn);

        Coordinates moved = initial.plus(1, 0);
        board.move(initial, moved);

        // Act
        List<Move> moves = pawn.getAllowedMoves(moved);

        // Assert
        assertThat(moves).doesNotContain(new Move(moved, moved.plus(2, 0)));
    }

    @Test
    public void pawnsCannotMoveIfPieceInFront() {
        // Arrange
        Board board = Board.empty();

        Piece blackPawn = new Pawn(PlayerColour.BLACK, board);
        Coordinates blackCoords = new Coordinates(3, 4);
        board.placePiece(blackCoords, blackPawn);

        Piece whitePawn = new Pawn(PlayerColour.WHITE, board);
        Coordinates whiteCoords = new Coordinates(4, 4);
        board.placePiece(whiteCoords, whitePawn);

        // Act
        List<Move> blackMoves = blackPawn.getAllowedMoves(blackCoords);
        List<Move> whiteMoves = whitePawn.getAllowedMoves(whiteCoords);

        // Assert
        assertThat(blackMoves).isEmpty();
        assertThat(whiteMoves).isEmpty();
    }

    @Test
    public void pawnsCannotMoveTwoSquaresIfPieceTwoInFront() {
        // Arrange
        Board board = Board.empty();

        Piece blackPawn = new Pawn(PlayerColour.BLACK, board);
        Coordinates blackCoords = new Coordinates(2, 4);
        board.placePiece(blackCoords, blackPawn);

        Piece whitePawn = new Pawn(PlayerColour.WHITE, board);
        Coordinates whiteCoords = new Coordinates(4, 4);
        board.placePiece(whiteCoords, whitePawn);

        // Act
        List<Move> blackMoves = blackPawn.getAllowedMoves(blackCoords);
        List<Move> whiteMoves = whitePawn.getAllowedMoves(whiteCoords);

        // Assert
        assertThat(blackMoves).doesNotContain(new Move(blackCoords, blackCoords.plus(2, 0)));
        assertThat(whiteMoves).doesNotContain(new Move(blackCoords, blackCoords.plus(-2, 0)));
    }

    @Test
    public void whitePawnsCannotMoveAtTopOfBoard() {
        // Arrange
        Board board = Board.empty();
        Piece pawn = new Pawn(PlayerColour.WHITE, board);
        Coordinates coords = new Coordinates(0, 4);
        board.placePiece(coords, pawn);

        // Act
        List<Move> moves = pawn.getAllowedMoves(coords);

        // Assert
        assertThat(moves).isEmpty();
    }

    @Test
    public void blackPawnsCannotMoveAtBottomOfBoard() {
        // Arrange
        Board board = Board.empty();
        Piece pawn = new Pawn(PlayerColour.BLACK, board);
        Coordinates coords = new Coordinates(7, 4);
        board.placePiece(coords, pawn);

        // Act
        List<Move> moves = pawn.getAllowedMoves(coords);

        // Assert
        assertThat(moves).isEmpty();
    }

    @Test
    public void whitePawnsCanCaptureDiagonally() {
        // Arrange
        Board board = Board.empty();
        Piece pawn = new Pawn(PlayerColour.WHITE, board);
        Piece enemyPiece = new Rook(PlayerColour.BLACK, board);
        Coordinates pawnCoords = new Coordinates(4, 4);
        board.placePiece(pawnCoords, pawn);

        Coordinates enemyCoords = pawnCoords.plus(-1, 1);
        board.placePiece(enemyCoords, enemyPiece);

        // Act
        List<Move> moves = pawn.getAllowedMoves(pawnCoords);

        // Assert
        assertThat(moves).contains(new Move(pawnCoords, enemyCoords));
    }

    @Test
    public void blackPawnsCanCaptureDiagonally() {
        // Arrange
        Board board = Board.empty();
        Piece pawn = new Pawn(PlayerColour.BLACK, board);
        Piece enemyPiece = new Rook(PlayerColour.WHITE, board);
        Coordinates pawnCoords = new Coordinates(3, 4);
        board.placePiece(pawnCoords, pawn);

        Coordinates enemyCoords = pawnCoords.plus(1, 1);
        board.placePiece(enemyCoords, enemyPiece);

        // Act
        List<Move> moves = pawn.getAllowedMoves(pawnCoords);

        // Assert
        assertThat(moves).contains(new Move(pawnCoords, enemyCoords));
    }

    @Test
    public void pawnsCannotMoveDiagonallyOffBoard() {
        // Arrange
        Board board = Board.empty();

        Piece blackPawn = new Pawn(PlayerColour.BLACK, board);
        Coordinates blackCoords = new Coordinates(3, 0);
        board.placePiece(blackCoords, blackPawn);

        Piece whitePawn = new Pawn(PlayerColour.WHITE, board);
        Coordinates whiteCoords = new Coordinates(4, 0);
        board.placePiece(whiteCoords, whitePawn);

        // Act
        List<Move> blackMoves = blackPawn.getAllowedMoves(blackCoords);
        List<Move> whiteMoves = whitePawn.getAllowedMoves(whiteCoords);

        // Assert
        assertThat(blackMoves).isEmpty();
        assertThat(whiteMoves).isEmpty();
    }

    @Test
    public void whitePawnsCannotMoveDiagonallyNotToCapture() {
        // Arrange
        Board board = Board.empty();
        Piece pawn = new Pawn(PlayerColour.WHITE, board);
        Piece friendlyPiece = new Rook(PlayerColour.WHITE, board);
        Coordinates pawnCoords = new Coordinates(4, 4);
        board.placePiece(pawnCoords, pawn);

        Coordinates rookCoords = pawnCoords.plus(-1, 1);
        board.placePiece(rookCoords, friendlyPiece);

        // Act
        List<Move> moves = pawn.getAllowedMoves(pawnCoords);

        // Assert
        assertThat(moves).doesNotContain(new Move(pawnCoords, rookCoords));
        Coordinates otherDiagonal = pawnCoords.plus(-1, -1);
        assertThat(moves).doesNotContain(new Move(pawnCoords, otherDiagonal));
    }

    @Test
    public void blackPawnsCannotMoveDiagonallyNotToCapture() {
        // Arrange
        Board board = Board.empty();
        Piece pawn = new Pawn(PlayerColour.BLACK, board);
        Piece friendlyPiece = new Rook(PlayerColour.BLACK, board);
        Coordinates pawnCoords = new Coordinates(3, 4);
        board.placePiece(pawnCoords, pawn);

        Coordinates rookCoords = pawnCoords.plus(1, 1);
        board.placePiece(rookCoords, friendlyPiece);

        // Act
        List<Move> moves = pawn.getAllowedMoves(pawnCoords);

        // Assert
        assertThat(moves).doesNotContain(new Move(pawnCoords, rookCoords));
        Coordinates otherDiagonal = pawnCoords.plus(1, -1);
        assertThat(moves).doesNotContain(new Move(pawnCoords, otherDiagonal));
    }
}
