package ua.lviv.iot.greenhouse.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.greenhouse.dto.luminosity_sensor.LuminositySensorDTO;
import ua.lviv.iot.greenhouse.dto.luminosity_sensor.LuminositySensorToUpdateDTO;
import ua.lviv.iot.greenhouse.models.LuminositySensor;
import ua.lviv.iot.greenhouse.services.LuminositySensorService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/sensors/luminosity-sensor")
@RequiredArgsConstructor
public class LuminositySensorController {

    private final LuminositySensorService luminositySensorService;

    @GetMapping
    public List<LuminositySensor> getAllSensorData(@RequestParam(required = false) String date) {
        return luminositySensorService.getAllSensorData(date);
    }

    @PostMapping
    public LuminositySensor addLuminositySensorData(final @RequestBody LuminositySensorDTO luminositySensorDTO) {
        luminositySensorDTO.setLocalDateTime(LocalDateTime.now());
        return luminositySensorService.createSensorData(luminositySensorDTO);
    }

    @DeleteMapping
    public void deleteAllSensorData(@RequestParam(required = false) String date) {
        luminositySensorService.deleteAllSensorData(date);
    }

    @PutMapping("{id}")
    public LuminositySensor updateCurrentDataById(final @RequestBody LuminositySensorDTO luminositySensorDTO,
                                           final @PathVariable("id") Long id) {
        return luminositySensorService.updateDataById(
                new LuminositySensorToUpdateDTO(id, luminositySensorDTO.getLuminosity())
        );
    }
}
