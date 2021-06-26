package ua.lviv.iot.greenhouse.dto.soil_sesnor;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SoilSensorHumidityDTO {

    private LocalDateTime localDateTime;
    @NonNull
    private double soilHumidity;
}
