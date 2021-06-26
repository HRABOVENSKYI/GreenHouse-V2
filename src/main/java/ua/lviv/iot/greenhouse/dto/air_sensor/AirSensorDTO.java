package ua.lviv.iot.greenhouse.dto.air_sensor;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AirSensorDTO {

    private LocalDateTime localDateTime;
    @NonNull
    private double airHumidity;
    @NonNull
    private double airTemperature;
}
