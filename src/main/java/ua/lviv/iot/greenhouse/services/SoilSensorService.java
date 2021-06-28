package ua.lviv.iot.greenhouse.services;

import ua.lviv.iot.greenhouse.dto.soil_sesnor.SoilSensorDTO;
import ua.lviv.iot.greenhouse.dto.soil_sesnor.SoilSensorHumidityDTO;
import ua.lviv.iot.greenhouse.dto.soil_sesnor.SoilSensorTemperatureDTO;
import ua.lviv.iot.greenhouse.dto.soil_sesnor.SoilSensorToUpdateDTO;
import ua.lviv.iot.greenhouse.models.SoilSensor;

import java.util.List;

public interface SoilSensorService {

    SoilSensor createSensorData(SoilSensorDTO airSensorDTO);

    List<SoilSensor> getAllSensorData(String date);

    List<SoilSensorHumidityDTO> getHumidityData(String date);

    List<SoilSensorTemperatureDTO> getTemperatureData(String date);

    SoilSensor updateDataById(SoilSensorToUpdateDTO soilSensorToUpdateDTO);

    void deleteAllSensorData(String date);
}
