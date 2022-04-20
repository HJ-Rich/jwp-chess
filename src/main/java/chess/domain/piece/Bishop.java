package chess.domain.piece;

import static chess.domain.direction.Direction.DOWN_LEFT;
import static chess.domain.direction.Direction.DOWN_RIGHT;
import static chess.domain.direction.Direction.UP_LEFT;
import static chess.domain.direction.Direction.UP_RIGHT;

import chess.domain.direction.Direction;
import chess.domain.piece.movestrategy.MoveStrategy;
import chess.domain.piece.movestrategy.RepeatableMoveStrategy;
import java.util.List;

public final class Bishop extends Piece {

    Bishop(Symbol symbol, Team team) {
        super(symbol, team);
    }

    @Override
    public List<Direction> direction() {
        return List.of(UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT);
    }

    @Override
    public MoveStrategy moveStrategy() {
        return new RepeatableMoveStrategy();
    }
}
