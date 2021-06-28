package ua.lviv.iot.greenhouse.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.lviv.iot.greenhouse.dao.AirSensorDAO;
import ua.lviv.iot.greenhouse.dto.air_sensor.AirSensorDTO;
import ua.lviv.iot.greenhouse.dto.air_sensor.AirSensorHumidityDTO;
import ua.lviv.iot.greenhouse.dto.air_sensor.AirSensorTemperatureDTO;
import ua.lviv.iot.greenhouse.dto.air_sensor.AirSensorToUpdateDTO;
import ua.lviv.iot.greenhouse.exception.NoDataFoundException;
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
        return airSensorDAO.save(new AirSensor(new AirSensor.Data(
                airSensorDTO.getLocalDateTime(),
                airSensorDTO.getAirHumidity(),
                airSensorDTO.getAirTemperature()
        )));
    }

    @Override
    public List<AirSensor> getAllSensorData(String date) {
        if (date == null) {
            List<AirSensor> sensorData = airSensorDAO.findAll();

            if (sensorData.isEmpty()) {
                throw new NoDataFoundException("There is no air sensor data yet");
            } else {
                return sensorData;
            }

        } else {
            LocalDate localDate = LocalDate.parse(date);

            List<AirSensor> sensorData = airSensorDAO.findSensorByData_LocalDateTimeBetween(
                    localDate.atTime(LocalTime.MIN),
                    localDate.atTime(LocalTime.MAX)
            );

            if (sensorData.isEmpty()) {
                throw new NoDataFoundException("There is no air sensor data for this time");
            } else {
                return sensorData;
            }
        }
    }

    @Override
    public List<AirSensorHumidityDTO> getHumidityData(String date) {
        return getAllSensorData(date).stream() // TODO: Read from the DB only needed fields
                .map(sensorData -> new AirSensorHumidityDTO(
                        sensorData.getData().getLocalDateTime(),
                        sensorData.getData().getAirHumidity()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<AirSensorTemperatureDTO> getTemperatureData(String date) {
        return getAllSensorData(date).stream() // TODO: Read from the DB only needed fields
                .map(sensorData -> new AirSensorTemperatureDTO(
                        sensorData.getData().getLocalDateTime(),
                        sensorData.getData().getAirTemperature()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public AirSensor updateDataById(AirSensorToUpdateDTO airSensorToUpdateDTO) {
        if (!airSensorDAO.existsById(airSensorToUpdateDTO.getId())) {
            throw new NoDataFoundException("There is no data for the air sensor with ID " + airSensorToUpdateDTO.getId());
        }

        AirSensor sensor = airSensorDAO.findSensorById(airSensorToUpdateDTO.getId());
        sensor.getData().setAirHumidity(airSensorToUpdateDTO.getAirHumidity());
        sensor.getData().setAirTemperature(airSensorToUpdateDTO.getAirTemperature());

        return airSensorDAO.save(sensor);
    }

    @Override
    public void deleteAllSensorData(String date) {
        if (date == null) {
            airSensorDAO.deleteAll();
        } else {
            LocalDate localDate = LocalDate.parse(date);

            airSensorDAO.deleteSensorByData_LocalDateTimeBetween(
                    localDate.atTime(LocalTime.MIN),
                    localDate.atTime(LocalTime.MAX)
            );
        }
    }
}
