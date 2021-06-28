package ua.lviv.iot.greenhouse.dto.soil_sesnor;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SoilSensorToUpdateDTO {

    private Long id;
    @NonNull
    private double soilHumidity;
    @NonNull
    private double soilTemperature;
}
