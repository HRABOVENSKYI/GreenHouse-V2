package ua.lviv.iot.greenhouse.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "general_sensor")
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class GeneralSensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private Data data;

    public GeneralSensor(Data data) {
        this.data = data;
    }

    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    public static class Data {
        private Double temperature;
        private Double humidity;
        private Double luminosity;
        private Double soilMoisture;
        private LocalDateTime time;
    }
}
