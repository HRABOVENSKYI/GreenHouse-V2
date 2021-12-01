package ua.lviv.iot.greenhouse.dto.general_sensor;

import lombok.*;

import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GeneralSensorDTO {

    @NotNull
    private Double temperature;

    @NotNull
    private Double humidity;

    @NotNull
    private Double luminosity;

    @NotNull
    private Double soilMoisture;

    @NotNull
    private LocalDateTime time;
}
