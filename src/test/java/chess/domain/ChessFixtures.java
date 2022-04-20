package chess.domain;

import static chess.domain.piece.Symbol.BISHOP;
import static chess.domain.piece.Symbol.EMPTY;
import static chess.domain.piece.Symbol.KING;
import static chess.domain.piece.Symbol.KNIGHT;
import static chess.domain.piece.Symbol.PAWN;
import static chess.domain.piece.Symbol.QUEEN;
import static chess.domain.piece.Symbol.ROOK;
import static chess.domain.piece.Team.BLACK;
import static chess.domain.piece.Team.NONE;
import static chess.domain.piece.Team.WHITE;

import chess.domain.board.coordinate.Coordinate;
import chess.domain.piece.Piece;

public class ChessFixtures {

    public static final Piece ROOK_BLACK = Piece.of(ROOK.name(), BLACK.name());
    public static final Piece ROOK_WHITE = Piece.of(ROOK.name(), WHITE.name());
    public static final Piece KNIGHT_BLACK = Piece.of(KNIGHT.name(), BLACK.name());
    public static final Piece KNIGHT_WHITE = Piece.of(KNIGHT.name(), WHITE.name());
    public static final Piece BISHOP_BLACK = Piece.of(BISHOP.name(), BLACK.name());
    public static final Piece BISHOP_WHITE = Piece.of(BISHOP.name(), WHITE.name());
    public static final Piece QUEEN_BLACK = Piece.of(QUEEN.name(), BLACK.name());
    public static final Piece QUEEN_WHITE = Piece.of(QUEEN.name(), WHITE.name());
    public static final Piece KING_BLACK = Piece.of(KING.name(), BLACK.name());
    public static final Piece KING_WHITE = Piece.of(KING.name(), WHITE.name());
    public static final Piece PAWN_BLACK = Piece.of(PAWN.name(), BLACK.name());
    public static final Piece PAWN_WHITE = Piece.of(PAWN.name(), WHITE.name());
    public static final Piece EMPTY_PIECE = Piece.of(EMPTY.name(), NONE.name());

    public static final Coordinate A1 = Coordinate.of("a1");
    public static final Coordinate A2 = Coordinate.of("a2");
    public static final Coordinate A4 = Coordinate.of("a4");
    public static final Coordinate A5 = Coordinate.of("a5");
    public static final Coordinate A6 = Coordinate.of("a6");
    public static final Coordinate A7 = Coordinate.of("a7");
    public static final Coordinate B3 = Coordinate.of("b3");
    public static final Coordinate C1 = Coordinate.of("c1");
    public static final Coordinate C2 = Coordinate.of("c2");
    public static final Coordinate C3 = Coordinate.of("c3");
    public static final Coordinate C4 = Coordinate.of("c4");
    public static final Coordinate C5 = Coordinate.of("c5");
    public static final Coordinate C6 = Coordinate.of("c6");
    public static final Coordinate D7 = Coordinate.of("d7");
    public static final Coordinate E3 = Coordinate.of("e3");
}
