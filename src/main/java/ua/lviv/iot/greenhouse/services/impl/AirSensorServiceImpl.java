package ua.lviv.iot.greenhouse.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.lviv.iot.greenhouse.dao.AirSensorDAO;
import ua.lviv.iot.greenhouse.dto.air_sensor.AirSensorDTO;
import ua.lviv.iot.greenhouse.dto.air_sensor.AirSensorHumidityDTO;
import ua.lviv.iot.greenhouse.dto.air_sensor.AirSensorTemperatureDTO;
import ua.lviv.iot.greenhouse.dto.air_sensor.AirSensorToUpdateDTO;
import ua.lviv.iot.greenhouse.exception.NoDataFoundException;
import ua.lviv.iot.greenhouse.exception.WrongDateFormatException;
import ua.lviv.iot.greenhouse.mappers.AirSensorMapper;
import ua.lviv.iot.greenhouse.models.AirSensor;
import ua.lviv.iot.greenhouse.services.AirSensorService;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AirSensorServiceImpl implements AirSensorService {

    private final AirSensorDAO airSensorDAO;

    @Override
    public AirSensor createSensorData(AirSensorDTO airSensorDTO) {
        return airSensorDAO.save(AirSensorMapper.mapAirSensorDTOtoAirSensor(airSensorDTO));
    }

    @Override
    public List<AirSensor> getAllSensorData(String date) {
        if (date == null) {
            return airSensorDAO.findAll();
        } else {
            try {
                LocalDate localDate = LocalDate.parse(date);

                return airSensorDAO.findSensorByData_LocalDateTimeBetween(
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
    public List<AirSensorHumidityDTO> getHumidityData(String date) {
        return getAllSensorData(date).stream() // TODO: Read from the DB only needed fields
                .map(AirSensorMapper::mapAirSensorToAirSensorHumidityDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AirSensorTemperatureDTO> getTemperatureData(String date) {
        return getAllSensorData(date).stream() // TODO: Read from the DB only needed fields
                .map(AirSensorMapper::mapAirSensorToAirSensorTemperatureDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AirSensor updateDataById(AirSensorToUpdateDTO airSensorToUpdateDTO) {
        AirSensor sensor = airSensorDAO.findSensorById(airSensorToUpdateDTO.getId())
                .orElseThrow(() -> new NoDataFoundException("There is no data for the air sensor with ID " +
                        airSensorToUpdateDTO.getId()));

        sensor.getData().setAirHumidity(airSensorToUpdateDTO.getAirHumidity());
        sensor.getData().setAirTemperature(airSensorToUpdateDTO.getAirTemperature());

        return airSensorDAO.save(sensor);
    }

    @Override
    public void deleteAllSensorData(String date) {
        if (date == null) {
            airSensorDAO.deleteAll();
        } else {
            try {
                LocalDate localDate = LocalDate.parse(date);

                airSensorDAO.deleteSensorByData_LocalDateTimeBetween(
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
