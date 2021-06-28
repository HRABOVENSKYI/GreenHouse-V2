package ua.lviv.iot.greenhouse.services;

import ua.lviv.iot.greenhouse.dto.luminosity_sensor.LuminositySensorDTO;
import ua.lviv.iot.greenhouse.dto.luminosity_sensor.LuminositySensorToUpdateDTO;
import ua.lviv.iot.greenhouse.models.LuminositySensor;

import java.util.List;

public interface LuminositySensorService {

    LuminositySensor createSensorData(LuminositySensorDTO luminositySensorDTO);

    List<LuminositySensor> getAllSensorData(String date);

    LuminositySensor updateDataById(LuminositySensorToUpdateDTO luminositySensorToUpdateDTO);

    void deleteAllSensorData(String date);
}
