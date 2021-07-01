package ua.lviv.iot.greenhouse.mappers;

import ua.lviv.iot.greenhouse.dto.soil_sesnor.SoilSensorDTO;
import ua.lviv.iot.greenhouse.dto.soil_sesnor.SoilSensorHumidityDTO;
import ua.lviv.iot.greenhouse.dto.soil_sesnor.SoilSensorTemperatureDTO;
import ua.lviv.iot.greenhouse.models.SoilSensor;

public class SoilSensorMapper {

    private SoilSensorMapper() {
    }

    public static SoilSensor mapSoilSensorDTOtoSoilSensor(SoilSensorDTO airSensorDTO) {
        return new SoilSensor(new SoilSensor.Data(
                airSensorDTO.getLocalDateTime(),
                airSensorDTO.getSoilHumidity(),
                airSensorDTO.getSoilTemperature()
        ));
    }

    public static SoilSensorHumidityDTO mapSoilSensorToSoilSensorHumidityDTO(SoilSensor airSensor) {
        return new SoilSensorHumidityDTO(
                airSensor.getData().getLocalDateTime(),
                airSensor.getData().getSoilHumidity()
        );
    }

    public static SoilSensorTemperatureDTO mapSoilSensorToSoilSensorTemperatureDTO(SoilSensor airSensor) {
        return new SoilSensorTemperatureDTO(
                airSensor.getData().getLocalDateTime(),
                airSensor.getData().getSoilTemperature()
        );
    }
}
