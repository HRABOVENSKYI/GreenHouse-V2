package ua.lviv.iot.greenhouse.services;

import ua.lviv.iot.greenhouse.dto.air_sensor.AirSensorDTO;
import ua.lviv.iot.greenhouse.dto.air_sensor.AirSensorHumidityDTO;
import ua.lviv.iot.greenhouse.dto.air_sensor.AirSensorTemperatureDTO;
import ua.lviv.iot.greenhouse.dto.air_sensor.AirSensorToUpdateDTO;
import ua.lviv.iot.greenhouse.models.AirSensor;

import java.util.List;

public interface AirSensorService {

    AirSensor createSensorData(AirSensorDTO airSensorDTO);

    List<AirSensor> getAllSensorData(String date);

    List<AirSensorHumidityDTO> getHumidityData(String date);

    List<AirSensorTemperatureDTO> getTemperatureData(String date);

    AirSensor updateDataById(AirSensorToUpdateDTO airSensorToUpdateDTO);

    void deleteAllSensorData(String date);
}
