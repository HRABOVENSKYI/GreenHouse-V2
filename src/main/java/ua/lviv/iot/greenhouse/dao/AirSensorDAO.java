package ua.lviv.iot.greenhouse.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.lviv.iot.greenhouse.models.AirSensor;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AirSensorDAO extends JpaRepository<AirSensor, Long> {

    List<AirSensor> findSensorByData_LocalDateTimeBetween(LocalDateTime after, LocalDateTime before);

    void deleteSensorByData_LocalDateTimeBetween(LocalDateTime after, LocalDateTime before);

    AirSensor findSensorById(Long id);
}
