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
import Repository.AbstractRepository;
import Repository.AddressRepository;


public class AddressRepositoryImpl extends AbstractRepository implements AddressRepository {

	private static final Logger LOGGER = Logger.getLogger(AddressRepositoryImpl.class);

	public static final String GET_ADDRESS_BY_ID = "select * from address where id = ?";
	public static final String GET_ALL_ADDRESS = "select * from address";
	public static final String ADD_NEW_ADDRESS = "insert into address (city, street, house, flat) values (?, ?, ?, ?)";
	public static final String UPDATE_ADDRESS = "update address set city = ?, street = ?, house = ?, flat = ? where id = ?";
	public static final String DELETE_ADDRESS = "delete from address where id = ?";
	public static final String GET_LAST_ID = "SELECT LAST_INSERT_ID()";

	public Address getById(Long id) {
		Address address = null;
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(GET_ADDRESS_BY_ID);
			preparedStatement.setLong(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()){
				address = buildAddress(rs);
			}
		} catch (SQLException e) {
			LOGGER.error("Exception while preparing statement for query: " + GET_ADDRESS_BY_ID);
		} finally {
			if (preparedStatement != null || connection != null)
				try {
					preparedStatement.close();
					connection.close();
				} catch (SQLException e) {
					LOGGER.error("Exception while closing resources");
				}
		}
		return address;
	}

	public List<Address> getAll() {
		List<Address> addressList = null;
		Statement statement = null;
		Connection connection = null;
		try {
			addressList = new ArrayList<Address>();
			connection = getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(GET_ALL_ADDRESS);
			while (rs.next()) {
				Address address = buildAddress(rs);
				addressList.add(address);
			}
		} catch (SQLException e) {
			LOGGER.error("Exception while creating statement for query: " + GET_ALL_ADDRESS);
		} finally {
			if (statement != null || connection != null)
				try {
					statement.close();
					connection.close();
				} catch (SQLException e) {
					LOGGER.error("Exception while closing resources");
				}
		}
		return addressList;
	}

	public Long add(Address address) {
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		Long generatedKey = null;
		try {
			connection = getConnection();

			if (checkIfExists(address.getAddressId(), connection)) {
				LOGGER.info("Address already exists: " + address);
			}
			preparedStatement = connection.prepareStatement(ADD_NEW_ADDRESS, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, address.getCity());
			preparedStatement.setString(2, address.getStreet());
			preparedStatement.setInt(3, address.getHouse());
			preparedStatement.setLong(4, address.getFlat());
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();

			if (rs.next()) {
			    generatedKey = (long) rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.error("Exception while preparing statement for query: " + ADD_NEW_ADDRESS);
		} finally {
			if (preparedStatement != null || connection != null)
				try {
					preparedStatement.close();
					connection.close();
				} catch (SQLException e) {
					LOGGER.error("Exception while closing resources");
				}
		}
		return generatedKey;
	}

	public void update(Address address) {
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(UPDATE_ADDRESS);
			preparedStatement.setString(1, address.getCity());
			preparedStatement.setString(2, address.getStreet());
			preparedStatement.setInt(3, address.getHouse());
			preparedStatement.setLong(4, address.getFlat());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("Exception while preparing statement for query: " + UPDATE_ADDRESS);
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

	public void delete(Address address) {
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(DELETE_ADDRESS);
			preparedStatement.setLong(1, address.getAddressId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("Exception while preparing statement for query: " + DELETE_ADDRESS);
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

	private Address buildAddress(ResultSet rs) throws SQLException {
		Address address = new Address();
		address.setAddressId(rs.getLong(1));
		address.setCity(rs.getString(2));
		address.setStreet(rs.getString(3));
		address.setHouse(rs.getInt(4));
		address.setFlat(rs.getInt(5));
		return address;
	}

	private boolean checkIfExists(Long id, Connection connection) {
		if(id == null){
			return false;
		}
		PreparedStatement preparedStatement = null;
		Address address = null;
		try {
			preparedStatement = connection.prepareStatement(GET_ADDRESS_BY_ID);
			preparedStatement.setLong(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				address = buildAddress(rs);
			}
		} catch (SQLException e) {
			LOGGER.error("Exception while preparing statement for query: " + GET_ADDRESS_BY_ID);
		} finally {
			if (preparedStatement != null || connection != null)
				try {
					preparedStatement.close();
					connection.close();
				} catch (SQLException e) {
					LOGGER.error("Exception while closing resources");
				}
		}
		return address != null ? true : false;
	}

	private Long getLastInsertedId() {
		Long id = null;
		Connection connection = null;
		Statement statement = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(GET_LAST_ID);
			while(rs.next()){
				id = rs.getLong(1);
			}
		} catch (SQLException e) {
			LOGGER.error("Exception while preparing statement for query: " + GET_LAST_ID);
		} finally {
			if (connection != null || statement != null)
				try {
					statement.close();
					connection.close();
				} catch (SQLException e) {
					LOGGER.error("Exception while closing resources");
				}
		}
		return id;
	}
}