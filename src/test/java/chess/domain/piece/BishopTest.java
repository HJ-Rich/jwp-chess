package chess.domain.piece;

import static chess.domain.Fixture.BISHOP_WHITE;
import static chess.domain.Fixture.E4;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.domain.board.Board;
import chess.domain.board.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BishopTest {
    @ParameterizedTest
    @CsvSource(value = {"C6,SUCCESS", "G6,SUCCESS", "G2,SUCCESS", "C2,SUCCESS"})
    @DisplayName("비숍은 대각선으로 이동한다")
    void movableDiagonal(String to, MoveResult expected) {
        final Board board = BoardFixture.of(E4, BISHOP_WHITE);
        final MoveResult move = board.move(E4, Position.from(to));
        assertThat(move).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"E6", "G4", "E2", "C4"})
    @DisplayName("비숍은 상하좌우로 이동할 수 없다")
    void notMovableVerticalOrHorizontal(String to) {
        final Board board = BoardFixture.of(E4, BISHOP_WHITE);
        assertThatThrownBy(() -> board.move(E4, Position.from(to)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이동할 수 없는 방향입니다");
    }

    @ParameterizedTest
    @CsvSource(value = {"C6,D5", "G6,F5", "G2,F3", "C2,D3"})
    @DisplayName("비숍은 이동 경로에 기물이 존재하면 이동할 수 없다")
    void notMovableForPieceOnTheWay(String to, String anotherPiece) {
        final Board board = BoardFixture.of(E4, BISHOP_WHITE, Position.from(anotherPiece), BISHOP_WHITE);
        assertThatThrownBy(() -> board.move(E4, Position.from(to)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이동 경로에 기물이 존재합니다");
    }

    @Test
    @DisplayName("비숍은 3점으로 계산된다")
    void getScore() {
        final double score = BISHOP_WHITE.getScore();
        assertThat(score).isEqualTo(3.0);
    }
}
