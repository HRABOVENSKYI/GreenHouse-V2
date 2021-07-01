package ua.lviv.iot.greenhouse.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.lviv.iot.greenhouse.dao.SoilSensorDAO;
import ua.lviv.iot.greenhouse.dto.soil_sesnor.SoilSensorDTO;
import ua.lviv.iot.greenhouse.dto.soil_sesnor.SoilSensorHumidityDTO;
import ua.lviv.iot.greenhouse.dto.soil_sesnor.SoilSensorTemperatureDTO;
import ua.lviv.iot.greenhouse.dto.soil_sesnor.SoilSensorToUpdateDTO;
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
            return soilSensorDAO.findAll();

        } else {
            LocalDate localDate = LocalDate.parse(date);

            return soilSensorDAO.findSensorByData_LocalDateTimeBetween(
                    localDate.atTime(LocalTime.MIN),
                    localDate.atTime(LocalTime.MAX)
            );
        }
    }

    @Override
    public List<SoilSensorHumidityDTO> getHumidityData(String date) {
        return getAllSensorData(date).stream() // TODO: Read from the DB only needed fields
                .map(sensorData -> new SoilSensorHumidityDTO(
                        sensorData.getData().getLocalDateTime(),
                        sensorData.getData().getSoilHumidity()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<SoilSensorTemperatureDTO> getTemperatureData(String date) {
        return getAllSensorData(date).stream() // TODO: Read from the DB only needed fields
                .map(sensorData -> new SoilSensorTemperatureDTO(
                        sensorData.getData().getLocalDateTime(),
                        sensorData.getData().getSoilTemperature()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public SoilSensor updateDataById(SoilSensorToUpdateDTO soilSensorToUpdateDTO) {
        SoilSensor sensor = soilSensorDAO.findSensorById(soilSensorToUpdateDTO.getId())
                .orElseThrow(() -> new NoDataFoundException("There is no data for the soil sensor with ID " +
                        soilSensorToUpdateDTO.getId()));

        sensor.getData().setSoilHumidity(soilSensorToUpdateDTO.getSoilHumidity());
        sensor.getData().setSoilTemperature(soilSensorToUpdateDTO.getSoilTemperature());

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
