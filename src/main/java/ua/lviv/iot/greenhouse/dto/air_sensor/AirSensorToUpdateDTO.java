package ua.lviv.iot.greenhouse.dto.air_sensor;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AirSensorToUpdateDTO {

    private Long id;
    @NonNull
    private double airHumidity;
    @NonNull
    private double airTemperature;
}
