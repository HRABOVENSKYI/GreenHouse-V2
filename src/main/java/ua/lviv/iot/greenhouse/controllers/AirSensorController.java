package ua.lviv.iot.greenhouse.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.greenhouse.dto.air_sensor.AirSensorDTO;
import ua.lviv.iot.greenhouse.dto.air_sensor.AirSensorHumidityDTO;
import ua.lviv.iot.greenhouse.dto.air_sensor.AirSensorTemperatureDTO;
import ua.lviv.iot.greenhouse.dto.air_sensor.AirSensorToUpdateDTO;
import ua.lviv.iot.greenhouse.models.AirSensor;
import ua.lviv.iot.greenhouse.services.AirSensorService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/sensors/air-sensor")
@RequiredArgsConstructor
public class AirSensorController {

    private final AirSensorService airSensorService;

    @GetMapping
    public List<AirSensor> getAllSensorData(@RequestParam(required = false) String date) {
        return airSensorService.getAllSensorData(date);
    }

    @GetMapping("/humidity-data")
    public List<AirSensorHumidityDTO> getHumidity(@RequestParam(required = false) String date) {
        return airSensorService.getHumidityData(date);
    }

    @GetMapping("/temperature-data")
    public List<AirSensorTemperatureDTO> getTemperature(@RequestParam(required = false) String date) {
        return airSensorService.getTemperatureData(date);
    }

    @PostMapping
    public AirSensor addAirSensorData(final @RequestBody AirSensorDTO airSensorDTO) {
        airSensorDTO.setLocalDateTime(LocalDateTime.now());
        return airSensorService.createSensorData(airSensorDTO);
    }

    @DeleteMapping
    public void deleteAllSensorData(@RequestParam(required = false) String date) {
        airSensorService.deleteAllSensorData(date);
    }

    @PutMapping("{id}")
    public AirSensor updateCurrentDataById(final @RequestBody AirSensorDTO airSensorDTO,
                                           final @PathVariable("id") Long id) {
        return airSensorService.updateDataById(
                new AirSensorToUpdateDTO(id, airSensorDTO.getAirHumidity(), airSensorDTO.getAirTemperature())
        );
    }
}
