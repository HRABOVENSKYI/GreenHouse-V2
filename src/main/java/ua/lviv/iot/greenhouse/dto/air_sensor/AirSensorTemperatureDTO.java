package ua.lviv.iot.greenhouse.dto.air_sensor;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AirSensorTemperatureDTO {

    private LocalDateTime localDateTime;
    @NonNull
    private double airTemperature;
}