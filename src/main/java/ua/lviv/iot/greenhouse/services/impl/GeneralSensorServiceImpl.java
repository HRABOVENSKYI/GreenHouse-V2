package ua.lviv.iot.greenhouse.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.lviv.iot.greenhouse.dao.GeneralSensorDao;
import ua.lviv.iot.greenhouse.dto.general_sensor.GeneralSensorDTO;
import ua.lviv.iot.greenhouse.exception.WrongDateFormatException;
import ua.lviv.iot.greenhouse.mappers.GeneralSensorMapper;
import ua.lviv.iot.greenhouse.models.GeneralSensor;
import ua.lviv.iot.greenhouse.services.GeneralSensorService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public class GeneralSensorServiceImpl implements GeneralSensorService {

    private final GeneralSensorDao generalSensorDao;

    @Override
    public GeneralSensor createSensorData(GeneralSensorDTO generalSensorDTO) {
        return generalSensorDao.save(GeneralSensorMapper.mapGeneralSensorDTOtoGeneralSensor(generalSensorDTO));
    }

    @Override
    public List<GeneralSensor> getAllSensorData(String date) {
        if (date == null) {
            return generalSensorDao.findAll();
        } else {
            try {
                LocalDate localDate = LocalDate.parse(date);
                return generalSensorDao.findSensorByData_TimeBetween(
                        localDate.atTime(LocalTime.MIN),
                        localDate.atTime(LocalTime.MAX)
                );
            } catch (Exception e) {
                throw new WrongDateFormatException("Wrong date format. Change to match yyyy-mm-dd and " +
                        "and make sure entered values are valid");
            }
        }
    }

    @Override
    @Transactional
    public void deleteSensorData(String date) {
        if (date == null) {
            generalSensorDao.deleteAll();
        } else {
            try {
                LocalDate localDate = LocalDate.parse(date);
                generalSensorDao.deleteSensorByData_TimeBetween(
                        localDate.atTime(LocalTime.MIN),
                        localDate.atTime(LocalTime.MAX)
                );
            } catch (Exception e) {
                throw new WrongDateFormatException("Wrong date format. Change to match yyyy-mm-dd and " +
                        "and make sure entered values are valid");
            }
        }
    }
}
