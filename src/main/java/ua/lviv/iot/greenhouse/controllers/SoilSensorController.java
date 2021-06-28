package ua.lviv.iot.greenhouse.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.greenhouse.dto.soil_sesnor.SoilSensorDTO;
import ua.lviv.iot.greenhouse.dto.soil_sesnor.SoilSensorHumidityDTO;
import ua.lviv.iot.greenhouse.dto.soil_sesnor.SoilSensorTemperatureDTO;
import ua.lviv.iot.greenhouse.dto.soil_sesnor.SoilSensorToUpdateDTO;
import ua.lviv.iot.greenhouse.models.SoilSensor;
import ua.lviv.iot.greenhouse.services.SoilSensorService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/sensors/soil-sensor")
@RequiredArgsConstructor
public class SoilSensorController {

    private final SoilSensorService soilSensorService;

    @GetMapping
    public List<SoilSensor> getAllSensorData(@RequestParam(required = false) String date) {
        return soilSensorService.getAllSensorData(date);
    }

    @GetMapping("/humidity-data")
    public List<SoilSensorHumidityDTO> getHumidity(@RequestParam(required = false) String date) {
        return soilSensorService.getHumidityData(date);
    }

    @GetMapping("/temperature-data")
    public List<SoilSensorTemperatureDTO> getTemperature(@RequestParam(required = false) String date) {
        return soilSensorService.getTemperatureData(date);
    }

    @PostMapping
    public SoilSensor addSoilSensorData(final @RequestBody SoilSensorDTO soilSensorDTO) {
        soilSensorDTO.setLocalDateTime(LocalDateTime.now());
        return soilSensorService.createSensorData(soilSensorDTO);
    }

    @DeleteMapping
    public void deleteAllSensorData(@RequestParam(required = false) String date) {
        soilSensorService.deleteAllSensorData(date);
    }

    @PutMapping("{id}")
    public SoilSensor updateCurrentDataById(final @RequestBody SoilSensorDTO soilSensorDTO,
                                           final @PathVariable("id") Long id) {
        return soilSensorService.updateDataById(
                new SoilSensorToUpdateDTO(id, soilSensorDTO.getSoilHumidity(), soilSensorDTO.getSoilTemperature())
        );
    }
}
