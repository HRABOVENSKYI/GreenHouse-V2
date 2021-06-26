package ua.lviv.iot.greenhouse.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.lviv.iot.greenhouse.dao.SoilSensorDAO;
import ua.lviv.iot.greenhouse.dto.soil_sesnor.SoilSensorDTO;
import ua.lviv.iot.greenhouse.dto.soil_sesnor.SoilSensorHumidityDTO;
import ua.lviv.iot.greenhouse.dto.soil_sesnor.SoilSensorTemperatureDTO;
import ua.lviv.iot.greenhouse.exception.NoDataFoundException;
import ua.lviv.iot.greenhouse.models.SoilSensor;
import ua.lviv.iot.greenhouse.services.SoilSensorService;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SoilSensorServiceImpl implements SoilSensorService {

    private final SoilSensorDAO soilSensorDAO;

    @Override
    public SoilSensor createSensorData(SoilSensorDTO soilSensorDTO) {
        return soilSensorDAO.save(new SoilSensor(new SoilSensor.Data(
                soilSensorDTO.getLocalDateTime(),
                soilSensorDTO.getSoilHumidity(),
                soilSensorDTO.getSoilTemperature()
        )));
    }

    @Override
    public List<SoilSensor> getAllSensorData(String date) {
        if (date == null) {
            List<SoilSensor> sensorData = soilSensorDAO.findAll();

            if (sensorData.isEmpty()) {
                throw new NoDataFoundException("There is no soil sensor data yet");
            } else {
                return sensorData;
            }

        } else {
            LocalDate localDate = LocalDate.parse(date);

            List<SoilSensor> sensorData = soilSensorDAO.findSensorByData_LocalDateTimeBetween(
                    localDate.atTime(LocalTime.MIN),
                    localDate.atTime(LocalTime.MAX)
            );

            if (sensorData.isEmpty()) {
                throw new NoDataFoundException("There is no soil sensor data for this time");
            } else {
                return sensorData;
            }
        }
    }

    @Override
    public List<SoilSensorHumidityDTO> getHumidityData(String date) {
        if (date == null) {
            List<SoilSensor> soilSensorData = soilSensorDAO.findAll(); // TODO: Read from the DB only needed fields

            if (soilSensorData.isEmpty()) {
                throw new NoDataFoundException("There is no soil sensor data yet");
            } else {
                return soilSensorData.stream()
                        .map(soilSensorD -> new SoilSensorHumidityDTO(
                                soilSensorD.getData().getLocalDateTime(),
                                soilSensorD.getData().getSoilHumidity()
                        ))
                        .collect(Collectors.toList());
            }

        } else {
            LocalDate localDate = LocalDate.parse(date);

            List<SoilSensor> soilSensorData = soilSensorDAO.findSensorByData_LocalDateTimeBetween(
                    localDate.atTime(LocalTime.MIN),
                    localDate.atTime(LocalTime.MAX)
            ); // TODO: Read from the DB only needed fields

            if (soilSensorData.isEmpty()) {
                throw new NoDataFoundException("There is no soil sensor data for this time");
            } else {
                return soilSensorData.stream()
                        .map(soilSensorD -> new SoilSensorHumidityDTO(
                                soilSensorD.getData().getLocalDateTime(),
                                soilSensorD.getData().getSoilHumidity()
                        ))
                        .collect(Collectors.toList());
            }
        }
    }

    @Override
    public List<SoilSensorTemperatureDTO> getTemperatureData(String date) {
        if (date == null) {
            List<SoilSensor> soilSensorData = soilSensorDAO.findAll(); // TODO: Read from the DB only needed fields

            if (soilSensorData.isEmpty()) {
                throw new NoDataFoundException("There is no soil sensor data yet");
            } else {
                return soilSensorData.stream()
                        .map(soilSensorD -> new SoilSensorTemperatureDTO(
                                soilSensorD.getData().getLocalDateTime(),
                                soilSensorD.getData().getSoilTemperature()
                        ))
                        .collect(Collectors.toList());
            }

        } else {
            LocalDate localDate = LocalDate.parse(date);

            List<SoilSensor> soilSensorData = soilSensorDAO.findSensorByData_LocalDateTimeBetween(
                    localDate.atTime(LocalTime.MIN),
                    localDate.atTime(LocalTime.MAX)
            ); // TODO: Read from the DB only needed fields

            if (soilSensorData.isEmpty()) {
                throw new NoDataFoundException("There is no soil sensor data for this time");
            } else {
                return soilSensorData.stream()
                        .map(soilSensorD -> new SoilSensorTemperatureDTO(
                                soilSensorD.getData().getLocalDateTime(),
                                soilSensorD.getData().getSoilTemperature()
                        ))
                        .collect(Collectors.toList());
            }
        }
    }

    @Override
    public SoilSensor updateDataById(Long id, double soilHumidity, double soilTemperature) {
        if (!soilSensorDAO.existsById(id)) {
            throw new NoDataFoundException("There is no data for the soil sensor with ID " + id);
        }

        SoilSensor sensor = soilSensorDAO.findSensorById(id);
        sensor.getData().setSoilHumidity(soilHumidity);
        sensor.getData().setSoilTemperature(soilTemperature);

        return soilSensorDAO.save(sensor);
    }

    @Override
    public void deleteAllSensorData(String date) {
        if (date == null) {
            soilSensorDAO.deleteAll();
        } else {
            LocalDate localDate = LocalDate.parse(date);

            soilSensorDAO.deleteSensorByData_LocalDateTimeBetween(
                    localDate.atTime(LocalTime.MIN),
                    localDate.atTime(LocalTime.MAX)
            );
        }
    }
}
