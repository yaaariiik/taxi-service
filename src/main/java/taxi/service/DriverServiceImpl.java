package taxi.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import taxi.dao.DriverDao;
import taxi.lib.Inject;
import taxi.lib.Service;
import taxi.model.Driver;

@Service
public class DriverServiceImpl implements DriverService {
    private static final Logger logger = LogManager.getLogger(DriverServiceImpl.class);
    @Inject
    private DriverDao driverDao;

    @Override
    public Driver create(Driver driver) {
        logger.info("Method create was called with params: driver = {}", driver);
        return driverDao.create(driver);
    }

    @Override
    public Driver get(Long id) {
        logger.info("Method get was called with params: id = {}", id);
        Optional<Driver> driverFromDb = driverDao.get(id);
        if (driverFromDb.isEmpty()) {
            logger.error("Obtained empty result on get driver by id request "
                    + "with id {}", id);
            throw new NoSuchElementException("Can't get driver by id: " + id);
        }
        logger.debug("Information about driver was successfully fetched from DB.");
        return driverFromDb.get();
    }

    @Override
    public List<Driver> getAll() {
        logger.info("Method getAll was called");
        return driverDao.getAll();
    }

    @Override
    public Driver update(Driver driver) {
        logger.info("Method update was called with params: driver = {}", driver);
        return driverDao.update(driver);
    }

    @Override
    public boolean delete(Long id) {
        logger.info("Method delete was called with params: id = {}", id);
        return driverDao.delete(id);
    }

    @Override
    public Optional<Driver> findByLogin(String login) {
        logger.info("Method findByLogin was called with params: login = {}", login);
        return driverDao.findByLogin(login);
    }
}
