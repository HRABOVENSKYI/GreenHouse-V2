package ua.lviv.iot.greenhouse.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.lviv.iot.greenhouse.models.GeneralSensor;

import java.time.LocalDateTime;
import java.util.List;

public interface GeneralSensorDao extends JpaRepository<GeneralSensor, Long> {

    List<GeneralSensor> findSensorByData_TimeBetween(LocalDateTime after, LocalDateTime before);

    void deleteSensorByData_TimeBetween(LocalDateTime after, LocalDateTime before);
}
