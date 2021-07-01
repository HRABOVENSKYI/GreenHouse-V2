package ua.lviv.iot.greenhouse.mappers;

import ua.lviv.iot.greenhouse.dto.air_sensor.AirSensorDTO;
import ua.lviv.iot.greenhouse.dto.air_sensor.AirSensorHumidityDTO;
import ua.lviv.iot.greenhouse.dto.air_sensor.AirSensorTemperatureDTO;
import ua.lviv.iot.greenhouse.models.AirSensor;

public class AirSensorMapper {

    private AirSensorMapper() {
    }

    public static AirSensor mapAirSensorDTOtoAirSensor(AirSensorDTO airSensorDTO) {
        return new AirSensor(new AirSensor.Data(
                airSensorDTO.getLocalDateTime(),
                airSensorDTO.getAirHumidity(),
                airSensorDTO.getAirTemperature()
        ));
    }

    public static AirSensorHumidityDTO mapAirSensorToAirSensorHumidityDTO(AirSensor airSensor) {
        return new AirSensorHumidityDTO(
                airSensor.getData().getLocalDateTime(),
                airSensor.getData().getAirHumidity()
        );
    }

    public static AirSensorTemperatureDTO mapAirSensorToAirSensorTemperatureDTO(AirSensor airSensor) {
        return new AirSensorTemperatureDTO(
                airSensor.getData().getLocalDateTime(),
                airSensor.getData().getAirTemperature()
        );
    }
}
