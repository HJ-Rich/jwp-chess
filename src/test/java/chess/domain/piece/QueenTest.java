package chess.domain.piece;

import static chess.domain.Fixture.E4;
import static chess.domain.Fixture.QUEEN_WHITE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.domain.board.Board;
import chess.domain.board.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class QueenTest {
    @ParameterizedTest
    @CsvSource(value = {"C6,SUCCESS", "G6,SUCCESS", "G2,SUCCESS", "C2,SUCCESS"})
    @DisplayName("퀸은 대각선으로도 이동한다")
    void movableDiagonal(String to, MoveResult expected) {
        final Board board = BoardFixture.of(E4, QUEEN_WHITE);
        final MoveResult move = board.move(E4, Position.from(to));
        assertThat(move).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"E6,SUCCESS", "G4,SUCCESS", "E2,SUCCESS", "C4,SUCCESS"})
    @DisplayName("퀸은 상하좌우로도 이동한다")
    void movableVerticalOrHorizontal(String to, MoveResult expected) {
        final Board board = BoardFixture.of(E4, QUEEN_WHITE);
        final MoveResult move = board.move(E4, Position.from(to));
        assertThat(move).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"C6,D5", "G6,F5", "G2,F3", "C2,D3",
            "E6,E5", "G4,F4", "E2,E3", "C4,D4"})
    @DisplayName("퀸은 이동 경로에 기물이 존재하면 이동할 수 없다")
    void notMovableForPieceOnTheWay(String to, String anotherPiece) {
        final Board board = BoardFixture.of(E4, QUEEN_WHITE, Position.from(anotherPiece), QUEEN_WHITE);
        assertThatThrownBy(() -> board.move(E4, Position.from(to)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이동 경로에 기물이 존재합니다");
    }

    @Test
    @DisplayName("퀸은 9점으로 계산된다")
    void getScore() {
        final double score = QUEEN_WHITE.getScore();
        assertThat(score).isEqualTo(9.0);
    }
}
