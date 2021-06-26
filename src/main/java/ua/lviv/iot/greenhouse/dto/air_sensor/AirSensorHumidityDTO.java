package ua.lviv.iot.greenhouse.dto.air_sensor;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AirSensorHumidityDTO {

    private LocalDateTime localDateTime;
    @NonNull
    private double airHumidity;
}
