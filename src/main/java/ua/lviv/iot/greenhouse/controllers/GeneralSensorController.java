package ua.lviv.iot.greenhouse.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.greenhouse.dto.general_sensor.GeneralSensorDTO;
import ua.lviv.iot.greenhouse.models.GeneralSensor;
import ua.lviv.iot.greenhouse.services.GeneralSensorService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/sensors")
public class GeneralSensorController {

    private final GeneralSensorService generalSensorService;

    @GetMapping
    public List<GeneralSensor> getAllSensorData(@RequestParam(required = false) String date) {
        return generalSensorService.getAllSensorData(date);
    }

    @PostMapping
    public GeneralSensor addGeneralSensorData(final @RequestBody GeneralSensorDTO generalSensorDTO) {
        return generalSensorService.createSensorData(generalSensorDTO);
    }

    @DeleteMapping
    public void deleteAllSensorData(@RequestParam(required = false) String date) {
        generalSensorService.deleteSensorData(date);
    }
}
