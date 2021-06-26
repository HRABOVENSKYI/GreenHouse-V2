package ua.lviv.iot.greenhouse.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.lviv.iot.greenhouse.models.LuminositySensor;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LuminositySensorDAO extends JpaRepository<LuminositySensor, Long> {

    List<LuminositySensor> findSensorByData_LocalDateTimeBetween(LocalDateTime after, LocalDateTime before);

    void deleteSensorByData_LocalDateTimeBetween(LocalDateTime after, LocalDateTime before);

    LuminositySensor findSensorById(Long id);
}
