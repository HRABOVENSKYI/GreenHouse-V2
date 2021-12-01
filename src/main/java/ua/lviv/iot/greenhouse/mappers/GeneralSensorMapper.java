package ua.lviv.iot.greenhouse.mappers;

import ua.lviv.iot.greenhouse.dto.general_sensor.GeneralSensorDTO;
import ua.lviv.iot.greenhouse.models.GeneralSensor;

public class GeneralSensorMapper {

    private GeneralSensorMapper() {
    }

    public static GeneralSensor mapGeneralSensorDTOtoGeneralSensor(GeneralSensorDTO generalSensorDTO) {
        return new GeneralSensor(new GeneralSensor.Data(
                generalSensorDTO.getTemperature(),
                generalSensorDTO.getHumidity(),
                generalSensorDTO.getLuminosity(),
                generalSensorDTO.getSoilMoisture(),
                generalSensorDTO.getTime()
        ));
    }
}
