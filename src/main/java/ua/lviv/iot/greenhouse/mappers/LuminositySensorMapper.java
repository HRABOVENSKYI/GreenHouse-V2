package ua.lviv.iot.greenhouse.mappers;

import ua.lviv.iot.greenhouse.dto.luminosity_sensor.LuminositySensorDTO;
import ua.lviv.iot.greenhouse.models.LuminositySensor;

public class LuminositySensorMapper {

    private LuminositySensorMapper() {
    }

    public static LuminositySensor mapLuminositySensorDTOtoLuminositySensor(LuminositySensorDTO airSensorDTO) {
        return new LuminositySensor(new LuminositySensor.Data(
                airSensorDTO.getLocalDateTime(),
                airSensorDTO.getLuminosity()
        ));
    }
}
