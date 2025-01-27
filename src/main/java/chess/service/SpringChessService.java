package chess.service;

import chess.domain.Room;
import chess.domain.board.Board;
import chess.domain.board.Position;
import chess.domain.piece.Color;
import chess.domain.piece.MoveResult;
import chess.domain.piece.Piece;
import chess.dto.GameCreateRequest;
import chess.dto.GameCreateResponse;
import chess.dto.GameDeleteRequest;
import chess.dto.GameDeleteResponse;
import chess.dto.GameDto;
import chess.dto.MoveRequest;
import chess.dto.MoveResponse;
import chess.dto.PositionDto;
import chess.exception.DeleteFailOnPlayingException;
import chess.exception.PasswordNotMatchedException;
import chess.repository.BoardRepository;
import chess.repository.RoomRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SpringChessService implements ChessService {

    private final BoardRepository boardRepository;
    private final RoomRepository roomRepository;
    private final PasswordEncoder passwordEncoder;

    public SpringChessService(BoardRepository boardRepository, RoomRepository roomRepository,
                              PasswordEncoder passwordEncoder) {
        this.boardRepository = boardRepository;
        this.roomRepository = roomRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public GameCreateResponse create(GameCreateRequest gameCreateRequest) {
        gameCreateRequest.setRoomPassword(passwordEncoder.encode(gameCreateRequest.getRoomPassword()));
        Room room = roomRepository.save(gameCreateRequest);
        boardRepository.save(room.getId());
        return new GameCreateResponse(room.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<GameDto> findAll() {
        return roomRepository.findAll()
                .stream()
                .map(this::getGameDtoByRoom)
                .collect(Collectors.toList());
    }

    private GameDto getGameDtoByRoom(Room room) {
        return new GameDto(room.getId(), room.getName(), room.getWhite(), room.getBlack(), room.isFinished());
    }

    @Override
    @Transactional(readOnly = true)
    public GameDto findById(long id) {
        return getGameDtoByRoom(roomRepository.findById(Long.parseLong(String.valueOf(id))));
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Color, Double> findScoreById(long gameId) {
        final Board board = boardRepository.findById(gameId);
        return board.getScore();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PositionDto> findPositionsById(long gameId) {
        final Board board = boardRepository.findById(gameId);
        final Map<Position, Piece> boardData = board.getBoard();

        return boardData.entrySet()
                .stream()
                .map(entry -> PositionDto.of(entry.getKey().getName(), entry.getValue().getName()))
                .collect(Collectors.toList());
    }

    @Override
    public MoveResponse updateBoard(long id, MoveRequest moveRequest) {
        final Board board = boardRepository.findById(moveRequest.getGameId());
        final MoveResult moveResult = board.move(Position.from(moveRequest.getFrom()),
                Position.from(moveRequest.getTo()));
        if (moveResult != MoveResult.FAIL) {
            boardRepository.updateMove(moveRequest);
            roomRepository.changeTurnById(moveRequest.getGameId());
        }
        if (moveResult == MoveResult.END) {
            roomRepository.finishById(moveRequest.getGameId());
        }
        return new MoveResponse(moveRequest.getPiece(), moveRequest.getFrom(), moveRequest.getTo(),
                moveResult);
    }

    @Override
    public GameDeleteResponse deleteById(GameDeleteRequest gameDeleteRequest) {
        validateFinished(gameDeleteRequest.getId());
        validatePassword(gameDeleteRequest.getId(), gameDeleteRequest.getPassword());
        roomRepository.deleteById(gameDeleteRequest.getId());
        boardRepository.deleteById(gameDeleteRequest.getId());
        return new GameDeleteResponse(true, String.valueOf(gameDeleteRequest.getId()));
    }

    private void validateFinished(int id) {
        final Board boardByGameId = boardRepository.findById(id);
        if (boardByGameId.isFinished()) {
            return;
        }

        throw new DeleteFailOnPlayingException();
    }

    private void validatePassword(long id, String password) {
        final Room room = roomRepository.findById(id);
        if (room.matchPassword(password, passwordEncoder)) {
            return;
        }

        throw new PasswordNotMatchedException();
    }

    @Override
    public long deleteAll() {
        final long rows = roomRepository.deleteAll();
        boardRepository.deleteAll();
        return rows;
    }
}
