package taxi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import taxi.exception.DataProcessingException;
import taxi.lib.Dao;
import taxi.model.Manufacturer;
import taxi.util.ConnectionUtil;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final Logger logger = LogManager.getLogger(ManufacturerDaoImpl.class);

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        logger.info("Method create was called with params: manufacturer = {}", manufacturer);
        String query = "INSERT INTO manufacturers (name, country) VALUES (?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            setUpdate(statement, manufacturer).executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                manufacturer.setId(resultSet.getObject(1, Long.class));
            }
            logger.debug("Method create was accomplished. Value of manufacturer = {}",
                            manufacturer);
            return manufacturer;
        } catch (SQLException e) {
            logger.error("Can't create manufacturer {}, reason: {}", manufacturer, e.getMessage());
            throw new DataProcessingException("Can't create manufacturer " + manufacturer, e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        logger.info("Method get was called with params: id = {}", id);
        String query = "SELECT * FROM manufacturers WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = parseManufacturerFromResultSet(resultSet);
            }
            logger.debug("Method get was accomplished.");
            return Optional.ofNullable(manufacturer);
        } catch (SQLException e) {
            logger.error("Can't get manufacturer by id {}, reason: {}", id, e.getMessage());
            throw new DataProcessingException("Can't get manufacturer by id " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        logger.info("Method getAll was called");
        String query = "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            List<Manufacturer> manufacturers = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                manufacturers.add(parseManufacturerFromResultSet(resultSet));
            }
            logger.debug("Method getAll was accomplished.");
            return manufacturers;
        } catch (SQLException e) {
            logger.error("Can't get all manufacturers, reason: {}", e.getMessage());
            throw new DataProcessingException("Can't get a list of manufacturers.", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        logger.info("Method update was called with params: manufacturer = {}",
                    manufacturer);
        String query = "UPDATE manufacturers SET name = ?, country = ?"
                + " WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement
                        = setUpdate(connection.prepareStatement(query), manufacturer)) {
            statement.setLong(3, manufacturer.getId());
            statement.executeUpdate();
            logger.debug("Method update was accomplished. Value of manufacturer = {}",
                            manufacturer);
            return manufacturer;
        } catch (SQLException e) {
            logger.error("Can't update manufacturer {}, reason: {}", manufacturer, e.getMessage());
            throw new DataProcessingException("Can't update a manufacturer "
                    + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        logger.info("Method delete was called with params: id = {}", id);
        String query = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            boolean isDeleted = statement.executeUpdate() > 0;
            logger.debug("Method delete was accomplished. isDeleted = {}", isDeleted);
            return isDeleted;
        } catch (SQLException e) {
            logger.error("Can't delete manufacturer by id {}, reason: {}", id, e.getMessage());
            throw new DataProcessingException("Can't delete a manufacturer by id " + id, e);
        }
    }

    private Manufacturer parseManufacturerFromResultSet(ResultSet resultSet) throws SQLException {
        logger.info("Method parseManufacturerFromResultSet was called");
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String country = resultSet.getString("country");
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(id);
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        logger.debug("Method parseManufacturerFromResultSet was accomplished.");
        return manufacturer;
    }

    private PreparedStatement setUpdate(PreparedStatement statement,
                                        Manufacturer manufacturer) throws SQLException {
        logger.info("Method setUpdate was called");
        statement.setString(1, manufacturer.getName());
        statement.setString(2, manufacturer.getCountry());
        logger.debug("Method setUpdate was accomplished.");
        return statement;
    }
}
