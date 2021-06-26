package ua.lviv.iot.greenhouse.dto.soil_sesnor;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SoilSensorTemperatureDTO {

    private LocalDateTime localDateTime;
    @NonNull
    private double soilTemperature;
}