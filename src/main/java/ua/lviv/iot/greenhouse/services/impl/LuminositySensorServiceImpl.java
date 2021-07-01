package ua.lviv.iot.greenhouse.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.lviv.iot.greenhouse.dao.LuminositySensorDAO;
import ua.lviv.iot.greenhouse.dto.luminosity_sensor.LuminositySensorToUpdateDTO;
import ua.lviv.iot.greenhouse.dto.luminosity_sensor.LuminositySensorDTO;
import ua.lviv.iot.greenhouse.exception.NoDataFoundException;
import ua.lviv.iot.greenhouse.models.LuminositySensor;
import ua.lviv.iot.greenhouse.services.LuminositySensorService;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LuminositySensorServiceImpl implements LuminositySensorService {

    private final LuminositySensorDAO luminositySensorDAO;

    @Override
    public LuminositySensor createSensorData(LuminositySensorDTO luminositySensorDTO) {
        return luminositySensorDAO.save(new LuminositySensor(new LuminositySensor.Data(
                luminositySensorDTO.getLocalDateTime(),
                luminositySensorDTO.getLuminosity()
        )));
    }

    @Override
    public List<LuminositySensor> getAllSensorData(String date) {
        if (date == null) {
            return luminositySensorDAO.findAll();

        } else {
            LocalDate localDate = LocalDate.parse(date);

            return luminositySensorDAO.findSensorByData_LocalDateTimeBetween(
                    localDate.atTime(LocalTime.MIN),
                    localDate.atTime(LocalTime.MAX)
            );
        }
    }

    @Override
    public LuminositySensor updateDataById(LuminositySensorToUpdateDTO luminositySensorToUpdateDTO) {
        LuminositySensor sensor = luminositySensorDAO.findSensorById(luminositySensorToUpdateDTO.getId())
                .orElseThrow(() -> new NoDataFoundException("There is no data for the luminosity sensor with ID " +
                        luminositySensorToUpdateDTO.getId()));

        sensor.getData().setLuminosity(luminositySensorToUpdateDTO.getLuminosity());
        sensor.getData().setLuminosity(luminositySensorToUpdateDTO.getLuminosity());

        return luminositySensorDAO.save(sensor);
    }

    @Override
    public void deleteAllSensorData(String date) {
        if (date == null) {
            luminositySensorDAO.deleteAll();
        } else {
            LocalDate localDate = LocalDate.parse(date);

            luminositySensorDAO.deleteSensorByData_LocalDateTimeBetween(
                    localDate.atTime(LocalTime.MIN),
                    localDate.atTime(LocalTime.MAX)
            );
        }
    }
}
