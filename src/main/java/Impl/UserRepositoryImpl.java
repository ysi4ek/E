package Impl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import Model.Address;
import Model.User;
import Repository.AbstractRepository;
import Repository.UserRepository;

public class UserRepositoryImpl extends AbstractRepository implements UserRepository {

	private static final Logger LOGGER = Logger.getLogger(UserRepositoryImpl.class);

	public static final String GET_USER_BY_ID = "select * from user where id = ?";
	public static final String GET_ALL_USERS = "select * from user";
	public static final String ADD_NEW_USER = "insert into user (name, age, address_id) values (?, ?, ?)";
	public static final String UPDATE_USER = "update user set name = ?, age = ?, address_id = ? where id = ?";
	public static final String DELETE_USER = "delete from user where id = ?";
	public static final String GET_USERS_IN_RANGE = "select * from user where age >= ? and age <= ?";
	public static final String GET_LAST_ID = "SELECT LAST_INSERT_ID();";

	public User getById(Long id) {
		User user = null;
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(GET_USER_BY_ID);
			preparedStatement.setLong(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				user = buildUser(rs);
			}
		} catch (SQLException e) {
			LOGGER.error("Exception while preparing statement for query: " + GET_USER_BY_ID);
		} finally {
			if (preparedStatement != null || connection != null)
				try {
					preparedStatement.close();
					connection.close();
				} catch (SQLException e) {
					LOGGER.error("Exception while closing resources");
				}
		}
		return user;
	}

	public List<User> getAll() {
		List<User> userList = null;
		Statement statement = null;
		Connection connection = null;
		try {
			userList = new ArrayList<User>();
			connection = getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(GET_ALL_USERS);
			while (rs.next()) {
				User user = buildUser(rs);
				userList.add(user);
			}
		} catch (SQLException e) {
			LOGGER.error("Exception while creating statement for query: " + GET_ALL_USERS);
		} finally {
			if (statement != null || connection != null)
				try {
					statement.close();
					connection.close();
				} catch (SQLException e) {
					LOGGER.error("Exception while closing resources");
				}
		}
		return userList;
	}

	public Long add(User user) {
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(ADD_NEW_USER);
			preparedStatement.setString(1, user.getUserName());
			preparedStatement.setInt(2, user.getAge());
			preparedStatement.setLong(3, user.getAddress().getAddressId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("Exception while preparing statement for query: " + ADD_NEW_USER);
		} finally {
			if (preparedStatement != null || connection != null)
				try {
					preparedStatement.close();
					connection.close();
				} catch (SQLException e) {
					LOGGER.error("Exception while closing resources");
				}
		}

		return getLastInsertedId();
	}

	public void update(User user) {
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(UPDATE_USER);
			preparedStatement.setString(1, user.getUserName());
			preparedStatement.setInt(2, user.getAge());
			preparedStatement.setLong(3, user.getAddress().getAddressId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("Exception while preparing statement for query: " + UPDATE_USER);
		} finally {
			if (preparedStatement != null || connection != null)
				try {
					preparedStatement.close();
					connection.close();
				} catch (SQLException e) {
					LOGGER.error("Exception while closing resources");
				}
		}
	}

	public void delete(User user) {
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(DELETE_USER);
			preparedStatement.setLong(1, user.getUserId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("Exception while preparing statement for query: " + DELETE_USER);
		} finally {
			if (preparedStatement != null || connection != null)
				try {
					preparedStatement.close();
					connection.close();
				} catch (SQLException e) {
					LOGGER.error("Exception while closing resources");
				}
		}
	}

	public List<User> getInAgeRange(int from, int to) {
		List<User> userList = null;
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			userList = new ArrayList<User>();
			connection = getConnection();
			preparedStatement = connection.prepareStatement(GET_USER_BY_ID);
			preparedStatement.setInt(1, from);
			preparedStatement.setInt(2, to);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				User user = buildUser(rs);
				userList.add(user);
			}
		} catch (SQLException e) {
			LOGGER.error("Exception while preparing statement for query: " + GET_USER_BY_ID);
		} finally {
			if (preparedStatement != null || connection != null)
				try {
					preparedStatement.close();
					connection.close();
				} catch (SQLException e) {
					LOGGER.error("Exception while closing resources");
				}
		}
		return userList;
	}

	private User buildUser(ResultSet rs) throws SQLException {
		User user = new User();

		user.setUserId(rs.getLong(1));
		user.setUserName(rs.getString(2));
		user.setAge(rs.getInt(3));
		Long addressId = rs.getLong(4);
		Address address = addressId != null ? new Address(addressId) : null;
		user.setAddress(address);

		return user;
	}

	private Long getLastInsertedId() {
		Long id = null;
		Connection connection = null;
		try {
			connection = getConnection();
			id = connection.createStatement().executeQuery(GET_LAST_ID).getLong(1);
		} catch (SQLException e) {
			LOGGER.error("Exception while preparing statement for query: " + GET_LAST_ID);
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					LOGGER.error("Exception while closing resources");
				}
		}
		return id;
	}
}