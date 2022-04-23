package chess.service;

import chess.dto.MoveRequestDto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ChessJDBCDao implements ChessDao {
    private static final String URL = "jdbc:mysql://localhost:3306/chess";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    private Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new ChessSQLException(e.getMessage());
        }
        return connection;
    }

    @Override
    public Map<String, String> getBoardByGameId(String gameId) {
        if (exists(gameId)) {
            return findBoardByGameId(gameId);
        }
        return createNewBoard(gameId);
    }

    private boolean exists(String gameId) {
        final String sql = "select count(1) as isExists from game where id = ?";
        int result = 0;
        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, gameId);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getInt("isExists");
            }
        } catch (SQLException e) {
            throw new ChessSQLException(e.getMessage());
        }
        return result > 0;
    }

    private Map<String, String> findBoardByGameId(String gameId) {
        final String sql = "select position, piece from board where game_id = ?";
        Map<String, String> board = new HashMap<>();
        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, gameId);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                board.put(resultSet.getString("position"), resultSet.getString("piece"));
            }
        } catch (SQLException e) {
            throw new ChessSQLException(e.getMessage());
        }
        return board;
    }

    private Map<String, String> createNewBoard(String gameId) {
        createGameNumber(gameId);
        insertPiecesOnPositions(gameId);
        return selectBoardByGameId(gameId);
    }

    private void createGameNumber(String gameId) {
        final String sql = "insert into game(id) value (?)";
        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, gameId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ChessSQLException(e.getMessage());
        }
    }

    private void insertPiecesOnPositions(String gameId) {
        final String sql = "insert into board(game_id, position, piece) select ?, initialPosition, initialPiece from initialBoard";
        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, gameId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ChessSQLException(e.getMessage());
        }
    }

    private Map<String, String> selectBoardByGameId(String gameId) {
        final String sql = "select position, piece from board where game_id = ?";
        final Map<String, String> board = new HashMap<>();

        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, gameId);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                board.put(resultSet.getString("position"), resultSet.getString("piece"));
            }
        } catch (SQLException e) {
            throw new ChessSQLException(e.getMessage());
        }

        return board;
    }

    @Override
    public void move(MoveRequestDto moveRequestDto) {
        final String gameId = moveRequestDto.getGameId();
        final String from = moveRequestDto.getFrom();
        final String to = moveRequestDto.getTo();
        final String piece = moveRequestDto.getPiece();

        deleteFromAndTo(gameId, from, to);
        insertTo(gameId, to, piece);
        changeTurn(gameId);
    }

    private void deleteFromAndTo(String gameId, String from, String to) {
        final String sql = "delete from board where game_id = ? and position in (?,?)";
        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, gameId);
            preparedStatement.setString(2, from);
            preparedStatement.setString(3, to);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ChessSQLException(e.getMessage());
        }
    }

    private void insertTo(String gameId, String to, String piece) {
        final String sql = "insert into board values (?,?,?)";
        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, gameId);
            preparedStatement.setString(2, to);
            preparedStatement.setString(3, piece);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ChessSQLException(e.getMessage());
        }
    }

    @Override
    public int getTurnByGameId(String gameId) {
        final String sql = "select turn from game where id = ?";
        int turn = 0;
        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, gameId);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                turn = resultSet.getInt("turn");
            }
        } catch (SQLException e) {
            throw new ChessSQLException(e.getMessage());
        }
        return turn;
    }

    private void changeTurn(String gameId) {
        int turn = oppositeTurn(getTurnByGameId(gameId));
        final String sql = "update game set turn = ? where id = ?";
        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, turn);
            preparedStatement.setString(2, gameId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ChessSQLException(e.getMessage());
        }
    }

    private int oppositeTurn(int turnByGameId) {
        if (turnByGameId == 0) {
            return 1;
        }
        return 0;
    }
}
