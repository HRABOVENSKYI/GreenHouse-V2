package ua.lviv.iot.greenhouse.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "luminosity_sensor")
@Table
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LuminositySensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private Data data;

    public LuminositySensor(Data data) {
        this.data = data;
    }

    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class Data {
        private LocalDateTime localDateTime;
        private double luminosity;
    }
}
