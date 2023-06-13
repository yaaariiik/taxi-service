package taxi.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import taxi.dao.CarDao;
import taxi.lib.Inject;
import taxi.lib.Service;
import taxi.model.Car;
import taxi.model.Driver;

@Service
public class CarServiceImpl implements CarService {
    private static final Logger logger = LogManager.getLogger(CarServiceImpl.class);
    @Inject
    private CarDao carDao;

    @Override
    public void addDriverToCar(Driver driver, Car car) {
        logger.info("Method addDriverToCar was called with params: driver = {}"
                + ", car = {}", driver, car);
        car.getDrivers().add(driver);
        carDao.update(car);
        logger.debug("Method login was accomplished");
    }

    @Override
    public void removeDriverFromCar(Driver driver, Car car) {
        logger.info("Method removeDriverFromCar was called with params: driver = {}"
                + ", car = {}", driver, car);
        car.getDrivers().remove(driver);
        carDao.update(car);
        logger.debug("Method login was accomplished");
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        logger.info("Method getAllByDriver was called with params: "
                + "driverId = {}", driverId);
        return carDao.getAllByDriver(driverId);
    }

    @Override
    public Car create(Car car) {
        logger.info("Method create was called with params: car = {}", car);
        return carDao.create(car);
    }

    @Override
    public Car get(Long id) {
        logger.info("Method get was called with params: id = {}", id);
        Optional<Car> carFromDb = carDao.get(id);
        if (carFromDb.isEmpty()) {
            logger.error("Obtained empty result on get car by id request "
                    + "with id {}", id);
            throw new NoSuchElementException("Can't get car by id: " + id);
        }
        logger.debug("Information about car was successfully fetched from DB.");
        return carFromDb.get();
    }

    @Override
    public List<Car> getAll() {
        logger.info("Method getAll was called");
        return carDao.getAll();
    }

    @Override
    public Car update(Car car) {
        logger.info("Method update was called with params: car = {}", car);
        return carDao.update(car);
    }

    @Override
    public boolean delete(Long id) {
        logger.info("Method delete was called with params: id = {}", id);
        return carDao.delete(id);
    }
}
