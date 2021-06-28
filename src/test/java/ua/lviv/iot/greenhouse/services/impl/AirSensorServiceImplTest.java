package ua.lviv.iot.greenhouse.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.lviv.iot.greenhouse.dao.AirSensorDAO;
import ua.lviv.iot.greenhouse.dto.air_sensor.AirSensorDTO;
import ua.lviv.iot.greenhouse.dto.air_sensor.AirSensorToUpdateDTO;
import ua.lviv.iot.greenhouse.exception.NoDataFoundException;
import ua.lviv.iot.greenhouse.models.AirSensor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class) // Automatically opens and closes Mocks
class AirSensorServiceImplTest {

    @Mock
    private AirSensorDAO airSensorDAO;
    private AirSensorServiceImpl airSensorService;

    @BeforeEach
    void setUp() {
        airSensorService = new AirSensorServiceImpl(airSensorDAO);
    }

    @Test
    void canCreateSensorData() {

        // given
        LocalDateTime dateTime = LocalDateTime.of(2021, Month.MAY, 1, 23, 45, 2);
        AirSensorDTO airSensorDTO = new AirSensorDTO(dateTime, 70.6, 23.38);
        AirSensor expectedAirSensor = new AirSensor(new AirSensor.Data(dateTime, 70.6, 23.38));

        // when
        airSensorService.createSensorData(airSensorDTO);

        // then
        ArgumentCaptor<AirSensor> argumentCaptor = ArgumentCaptor.forClass(AirSensor.class);
        verify(airSensorDAO).save(argumentCaptor.capture());
        AirSensor capturedAirSensor = argumentCaptor.getValue();
        assertThat(capturedAirSensor).isEqualTo(expectedAirSensor);
    }

    @Test
    void canGetAllSensorDataWhenDateIsNullAndThereIsData() {

        // given
        LocalDateTime dateTime = LocalDateTime.of(2021, Month.MAY, 1, 23, 45, 2);
        AirSensor airSensor = new AirSensor(new AirSensor.Data(dateTime, 70.6, 23.38));

        given(airSensorDAO.findAll()).willReturn(List.of(airSensor, airSensor)); // not empty list

        // when
        airSensorService.getAllSensorData(null);

        // then
        verify(airSensorDAO).findAll();
    }

    @Test
    void throwExceptionWhenDateIsNullAndThereIsNoData() {

        // given
        given(airSensorDAO.findAll()).willReturn(Collections.emptyList()); // not empty list

        // when
        // then
        assertThatThrownBy(() -> airSensorService.getAllSensorData(null))
                .isInstanceOf(NoDataFoundException.class)
                .hasMessageContaining("There is no air sensor data yet");
    }

    @Test
    void canGetAllSensorDataWhenDateIsGivenAndThereIsData() {

        // given
        LocalDate date = LocalDate.of(2021, Month.MAY, 1);
        LocalDateTime dateTime = LocalDateTime.of(2021, Month.MAY, 1, 23, 45, 2);
        AirSensor airSensor = new AirSensor(new AirSensor.Data(dateTime, 70.6, 23.38));

        given(airSensorDAO.findSensorByData_LocalDateTimeBetween(
                date.atTime(LocalTime.MIN),
                date.atTime(LocalTime.MAX)
        ))
                .willReturn(List.of(airSensor, airSensor)); // not empty list

        // when
        airSensorService.getAllSensorData(date.toString());

        // then
        verify(airSensorDAO).findSensorByData_LocalDateTimeBetween(
                date.atTime(LocalTime.MIN),
                date.atTime(LocalTime.MAX)
        );
    }

    @Test
    void throwExceptionWhenDateIsGivenAndThereIsNoData() {

        // given
        LocalDate date = LocalDate.of(2020, Month.MAY, 1);

        given(airSensorDAO.findSensorByData_LocalDateTimeBetween(
                date.atTime(LocalTime.MIN),
                date.atTime(LocalTime.MAX)
        ))
                .willReturn(Collections.emptyList()); // empty list

        // when
        // then
        assertThatThrownBy(() -> airSensorService.getAllSensorData(date.toString()))
                .isInstanceOf(NoDataFoundException.class)
                .hasMessageContaining("There is no air sensor data for this time");
    }

    @Test
    @Disabled // Test completely depends on getAllSensorData() method, which is tested
    void canGetHumidityData() {
    }

    @Test
    @Disabled // Test completely depends on getAllSensorData() method, which is tested
    void canGetTemperatureData() {
    }

    @Test
    void canUpdateDataByIdWhenIdIsCorrect() {

        // given
        AirSensorToUpdateDTO airSensorToUpdateDTO = new AirSensorToUpdateDTO(1L, 60.8, 23.2);
        given(airSensorDAO.existsById(airSensorToUpdateDTO.getId())).willReturn(true);
        LocalDateTime dateTime = LocalDateTime.of(2021, Month.MAY, 1, 23, 45, 2);
        AirSensor airSensor = new AirSensor(1L, new AirSensor.Data(dateTime, 70.6, 23.38));
        given(airSensorDAO.findSensorById(airSensorToUpdateDTO.getId())).willReturn(airSensor);

        // when
        airSensorService.updateDataById(airSensorToUpdateDTO);

        // then
        ArgumentCaptor<AirSensor> argumentCaptor = ArgumentCaptor.forClass(AirSensor.class);
        verify(airSensorDAO).save(argumentCaptor.capture());
        AirSensor capturedAirSensor = argumentCaptor.getValue();
        assertThat(capturedAirSensor.getId()).isEqualTo(airSensorToUpdateDTO.getId());
        assertThat(capturedAirSensor.getData().getAirHumidity()).isEqualTo(airSensorToUpdateDTO.getAirHumidity());
        assertThat(capturedAirSensor.getData().getAirTemperature()).isEqualTo(airSensorToUpdateDTO.getAirTemperature());
    }

    @Test
    void canDeleteAllSensorDataWhenDateIsNull() {

        // when
        airSensorService.deleteAllSensorData(null);

        // then
        verify(airSensorDAO).deleteAll();
    }
}