package ua.lviv.iot.greenhouse.dto.luminosity_sensor;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LuminositySensorDTO {

    private LocalDateTime localDateTime;
    @NonNull
    private double luminosity;
}
