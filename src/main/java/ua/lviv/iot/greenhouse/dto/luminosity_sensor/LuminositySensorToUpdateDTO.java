package ua.lviv.iot.greenhouse.dto.luminosity_sensor;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LuminositySensorToUpdateDTO {

    private Long id;
    @NonNull
    private double luminosity;
}
