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
@EqualsAndHashCode
public class LuminositySensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private Data data;

    public LuminositySensor(Data data) {
        this.data = data;
    }

    public LuminositySensor(Long id, Data data) {
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
        private double luminosity;
    }
}
