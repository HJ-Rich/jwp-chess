package chess.domain.piece;

import static chess.domain.Fixture.E4;
import static chess.domain.Fixture.KNIGHT_WHITE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.domain.board.Board;
import chess.domain.board.BoardFactory;
import chess.domain.board.Position;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class KnightTest {
    @ParameterizedTest
    @CsvSource(value = {"F6,SUCCESS", "G5,SUCCESS", "G3,SUCCESS", "F2,SUCCESS",
            "D2,SUCCESS", "C3,SUCCESS", "C5,SUCCESS", "D6,SUCCESS"})
    @DisplayName("나이트는 (1,2) 또는 (2,1) 칸 만큼 이동한다")
    void knightMove(String to, MoveResult expected) {
        final Board board = BoardFixture.of(E4, KNIGHT_WHITE);
        final MoveResult move = board.move(E4, Position.from(to));
        assertThat(move).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"C6", "G6", "G2", "C2"})
    @DisplayName("나이트는 대각선으로 이동할 수 없다")
    void notMovableDiagonal(String to) {
        final Board board = BoardFixture.of(E4, KNIGHT_WHITE);
        assertThatThrownBy(() -> board.move(E4, Position.from(to)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("나이트가 이동할 수 없는 지점입니다");
    }

    @ParameterizedTest
    @CsvSource(value = {"E6", "G4", "E2", "C4"})
    @DisplayName("나이트는 상하좌우로 이동할 수 없다")
    void notMovableVerticalOrHorizontal(String to) {
        final Board board = BoardFixture.of(E4, KNIGHT_WHITE);
        assertThatThrownBy(() -> board.move(E4, Position.from(to)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("나이트가 이동할 수 없는 지점입니다");
    }

    @ParameterizedTest
    @CsvSource(value = {"F6,SUCCESS", "G5,SUCCESS", "G3,SUCCESS", "F2,SUCCESS",
            "D2,SUCCESS", "C3,SUCCESS", "C5,SUCCESS", "D6,SUCCESS"})
    @DisplayName("나이트는 이동 경로에 기물이 존재해도 이동한다")
    void movableThroughPieceOnTheWay(String to, MoveResult expected) {
        final Map<Position, Piece> testBoard = setupSurroundedMap();
        final Board board = BoardFactory.newInstance(testBoard);
        final MoveResult move = board.move(E4, Position.from(to));
        assertThat(move).isEqualTo(expected);
    }

    private Map<Position, Piece> setupSurroundedMap() {
        final Map<Position, Piece> testBoard = new HashMap<>();
        testBoard.put(Position.from("E5"), KNIGHT_WHITE);
        testBoard.put(Position.from("F5"), KNIGHT_WHITE);
        testBoard.put(Position.from("F4"), KNIGHT_WHITE);
        testBoard.put(Position.from("F3"), KNIGHT_WHITE);
        testBoard.put(Position.from("E3"), KNIGHT_WHITE);
        testBoard.put(Position.from("D3"), KNIGHT_WHITE);
        testBoard.put(Position.from("D4"), KNIGHT_WHITE);
        testBoard.put(Position.from("D5"), KNIGHT_WHITE);
        testBoard.put(E4, KNIGHT_WHITE);
        return testBoard;
    }

    @Test
    @DisplayName("나이트는 2.5점으로 계산된다")
    void getScore() {
        final double score = KNIGHT_WHITE.getScore();
        assertThat(score).isEqualTo(2.5);
    }
}
