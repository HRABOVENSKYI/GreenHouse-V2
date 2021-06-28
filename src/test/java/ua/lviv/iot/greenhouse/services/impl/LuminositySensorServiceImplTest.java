package ua.lviv.iot.greenhouse.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.lviv.iot.greenhouse.dao.LuminositySensorDAO;
import ua.lviv.iot.greenhouse.dto.luminosity_sensor.LuminositySensorDTO;
import ua.lviv.iot.greenhouse.dto.luminosity_sensor.LuminositySensorToUpdateDTO;
import ua.lviv.iot.greenhouse.exception.NoDataFoundException;
import ua.lviv.iot.greenhouse.models.LuminositySensor;

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

@ExtendWith(MockitoExtension.class)
class LuminositySensorServiceImplTest {

    @Mock
    private LuminositySensorDAO luminositySensorDAO;
    private LuminositySensorServiceImpl luminositySensorService;

    @BeforeEach
    void setUp() {
        luminositySensorService = new LuminositySensorServiceImpl(luminositySensorDAO);
    }

    @Test
    void createSensorData() {

        // given
        LocalDateTime dateTime = LocalDateTime.of(2021, Month.MAY, 1, 23, 45, 2);
        LuminositySensorDTO SensorDTO = new LuminositySensorDTO(dateTime, 70.6);
        LuminositySensor expectedLuminositySensor = new LuminositySensor(new LuminositySensor.Data(dateTime, 70.6));

        // when
        luminositySensorService.createSensorData(SensorDTO);

        // then
        ArgumentCaptor<LuminositySensor> argumentCaptor = ArgumentCaptor.forClass(LuminositySensor.class);
        verify(luminositySensorDAO).save(argumentCaptor.capture());
        LuminositySensor capturedLuminositySensor = argumentCaptor.getValue();
        assertThat(capturedLuminositySensor).isEqualTo(expectedLuminositySensor);
    }

    @Test
    void canGetAllSensorDataWhenDateIsNullAndThereIsData() {

        // given
        LocalDateTime dateTime = LocalDateTime.of(2021, Month.MAY, 1, 23, 45, 2);
        LuminositySensor luminositySensor = new LuminositySensor(new LuminositySensor.Data(dateTime, 70.6));

        given(luminositySensorDAO.findAll()).willReturn(List.of(luminositySensor, luminositySensor)); // not empty list

        // when
        luminositySensorService.getAllSensorData(null);

        // then
        verify(luminositySensorDAO).findAll();
    }

    @Test
    void throwExceptionWhenDateIsNullAndThereIsNoData() {

        // given
        given(luminositySensorDAO.findAll()).willReturn(Collections.emptyList()); // not empty list

        // when
        // then
        assertThatThrownBy(() -> luminositySensorService.getAllSensorData(null))
                .isInstanceOf(NoDataFoundException.class)
                .hasMessageContaining("There is no luminosity sensor data yet");
    }

    @Test
    void canGetAllSensorDataWhenDateIsGivenAndThereIsData() {

        // given
        LocalDate date = LocalDate.of(2021, Month.MAY, 1);
        LocalDateTime dateTime = LocalDateTime.of(2021, Month.MAY, 1, 23, 45, 2);
        LuminositySensor luminositySensor = new LuminositySensor(new LuminositySensor.Data(dateTime, 70.6));

        given(luminositySensorDAO.findSensorByData_LocalDateTimeBetween(
                date.atTime(LocalTime.MIN),
                date.atTime(LocalTime.MAX)
        ))
                .willReturn(List.of(luminositySensor, luminositySensor)); // not empty list

        // when
        luminositySensorService.getAllSensorData(date.toString());

        // then
        verify(luminositySensorDAO).findSensorByData_LocalDateTimeBetween(
                date.atTime(LocalTime.MIN),
                date.atTime(LocalTime.MAX)
        );
    }

    @Test
    void throwExceptionWhenDateIsGivenAndThereIsNoData() {

        // given
        LocalDate date = LocalDate.of(2020, Month.MAY, 1);

        given(luminositySensorDAO.findSensorByData_LocalDateTimeBetween(
                date.atTime(LocalTime.MIN),
                date.atTime(LocalTime.MAX)
        ))
                .willReturn(Collections.emptyList()); // empty list

        // when
        // then
        assertThatThrownBy(() -> luminositySensorService.getAllSensorData(date.toString()))
                .isInstanceOf(NoDataFoundException.class)
                .hasMessageContaining("There is no luminosity sensor data for this time");
    }

    @Test
    void canUpdateDataByIdWhenIdIsCorrect() {

        // given
        LuminositySensorToUpdateDTO luminositySensorToUpdateDTO = new LuminositySensorToUpdateDTO(1L, 60.8);
        given(luminositySensorDAO.existsById(luminositySensorToUpdateDTO.getId())).willReturn(true);
        LocalDateTime dateTime = LocalDateTime.of(2021, Month.MAY, 1, 23, 45, 2);
        LuminositySensor luminositySensor = new LuminositySensor(1L, new LuminositySensor.Data(dateTime, 70.6));
        given(luminositySensorDAO.findSensorById(luminositySensorToUpdateDTO.getId())).willReturn(luminositySensor);

        // when
        luminositySensorService.updateDataById(luminositySensorToUpdateDTO);

        // then
        ArgumentCaptor<LuminositySensor> argumentCaptor = ArgumentCaptor.forClass(LuminositySensor.class);
        verify(luminositySensorDAO).save(argumentCaptor.capture());
        LuminositySensor capturedLuminositySensor = argumentCaptor.getValue();
        assertThat(capturedLuminositySensor.getId()).isEqualTo(luminositySensorToUpdateDTO.getId());
        assertThat(capturedLuminositySensor.getData().getLuminosity()).isEqualTo(luminositySensorToUpdateDTO.getLuminosity());
    }

    @Test
    void canDeleteAllSensorDataWhenDateIsNull() {

        // when
        luminositySensorService.deleteAllSensorData(null);

        // then
        verify(luminositySensorDAO).deleteAll();
    }
}