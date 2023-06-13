package taxi.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import taxi.dao.ManufacturerDao;
import taxi.lib.Inject;
import taxi.lib.Service;
import taxi.model.Manufacturer;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {
    private static final Logger logger = LogManager.getLogger(ManufacturerServiceImpl.class);
    @Inject
    private ManufacturerDao manufacturerDao;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        logger.info("Method create was called with params: manufacturer = {}",
                    manufacturer);
        return manufacturerDao.create(manufacturer);
    }

    @Override
    public Manufacturer get(Long id) {
        logger.info("Method get was called with params: id = {}", id);
        Optional<Manufacturer> manufacturerFromDb = manufacturerDao.get(id);
        if (manufacturerFromDb.isEmpty()) {
            logger.error("Obtained empty result on get manufacturer by id request "
                    + "with id {}", id);
            throw new NoSuchElementException("Can't get manufacturer by id: " + id);
        }
        logger.debug("Information about manufacturer was successfully fetched from DB.");
        return manufacturerFromDb.get();
    }

    @Override
    public List<Manufacturer> getAll() {
        logger.info("Method getAll was called");
        return manufacturerDao.getAll();
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        logger.info("Method update was called with params: manufacturer = {}",
                    manufacturer);
        return manufacturerDao.update(manufacturer);
    }

    @Override
    public boolean delete(Long id) {
        logger.info("Method delete was called with params: id = {}", id);
        return manufacturerDao.delete(id);
    }
}
