package ua.lviv.iot.greenhouse.services;

import ua.lviv.iot.greenhouse.dto.general_sensor.GeneralSensorDTO;
import ua.lviv.iot.greenhouse.models.GeneralSensor;

import java.util.List;

public interface GeneralSensorService {
    GeneralSensor createSensorData(GeneralSensorDTO generalSensorDTO);

    List<GeneralSensor> getAllSensorData(String date);

    void deleteSensorData(String date);
}
