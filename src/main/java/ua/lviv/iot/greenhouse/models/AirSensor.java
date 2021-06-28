package ua.lviv.iot.greenhouse.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "air_sensor")
@Table
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AirSensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private Data data;

    public AirSensor(Data data) {
        this.data = data;
    }

    public AirSensor(Long id, Data data) {
        this.id = id;
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
        private LocalDateTime localDateTime;
        private double airHumidity;
        private double airTemperature;
    }
}
