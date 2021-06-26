package ua.lviv.iot.greenhouse.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.lviv.iot.greenhouse.models.SoilSensor;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SoilSensorDAO extends JpaRepository<SoilSensor, Long> {

    List<SoilSensor> findSensorByData_LocalDateTimeBetween(LocalDateTime after, LocalDateTime before);

    void deleteSensorByData_LocalDateTimeBetween(LocalDateTime after, LocalDateTime before);

    SoilSensor findSensorById(Long id);
}
