package ua.lviv.iot.greenhouse.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.lviv.iot.greenhouse.dao.LuminositySensorDAO;
import ua.lviv.iot.greenhouse.dto.luminosity_sensor.LuminositySensorToUpdateDTO;
import ua.lviv.iot.greenhouse.dto.luminosity_sensor.LuminositySensorDTO;
import ua.lviv.iot.greenhouse.exception.NoDataFoundException;
import ua.lviv.iot.greenhouse.exception.WrongDateFormatException;
import ua.lviv.iot.greenhouse.models.LuminositySensor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Optional;

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
    void canGetAllSensorDataWhenDateIsNull() {

        // when
        luminositySensorService.getAllSensorData(null);

        // then
        verify(luminositySensorDAO).findAll();
    }

    @Test
    void canGetAllSensorDataWhenDateIsGiven() {

        // given
        LocalDate date = LocalDate.of(2021, Month.MAY, 1);

        // when
        luminositySensorService.getAllSensorData(date.toString());

        // then
        verify(luminositySensorDAO).findSensorByData_LocalDateTimeBetween(
                date.atTime(LocalTime.MIN),
                date.atTime(LocalTime.MAX)
        );
    }

    @Test
    void throwExceptionInGetAllSensorDataWhenWrongDateFormat() {

        // given
        String date = "2002.11.23";

        // when
        // then
        assertThatThrownBy(() -> luminositySensorService.getAllSensorData(date))
                .isInstanceOf(WrongDateFormatException.class)
                .hasMessageContaining("Wrong date format. Change to match yyyy-mm-dd and " +
                        "and make sure entered values are valid");
    }

    @Test
    void canUpdateDataByIdWhenIdIsCorrect() {

        // given
        LuminositySensorToUpdateDTO luminositySensorToUpdateDTO = new LuminositySensorToUpdateDTO(1L, 60.8);
        LocalDateTime dateTime = LocalDateTime.of(2021, Month.MAY, 1, 23, 45, 2);
        Optional<LuminositySensor> luminositySensor = Optional.of(
                new LuminositySensor(1L, new LuminositySensor.Data(dateTime, 70.6)));
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
    void throwExceptionWhileUpdatingDataByIdWhenSensorWithIdIsMissing() {

        // given
        LuminositySensorToUpdateDTO luminositySensorToUpdateDTO = new LuminositySensorToUpdateDTO(1L, 60.8);
        given(luminositySensorDAO.findSensorById(luminositySensorToUpdateDTO.getId())).willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> luminositySensorService.updateDataById(luminositySensorToUpdateDTO))
                .isInstanceOf(NoDataFoundException.class)
                .hasMessageContaining("There is no data for the luminosity sensor with ID " +
                        luminositySensorToUpdateDTO.getId());
    }

    @Test
    void canDeleteAllSensorDataWhenDateIsNull() {

        // when
        luminositySensorService.deleteAllSensorData(null);

        // then
        verify(luminositySensorDAO).deleteAll();
    }

    @Test
    void canDeleteAllSensorDataWhenDateIsGiven() {

        // given
        LocalDate date = LocalDate.of(2021, Month.MAY, 1);

        // when
        luminositySensorService.deleteAllSensorData(date.toString());

        // then
        verify(luminositySensorDAO).deleteSensorByData_LocalDateTimeBetween(
                date.atTime(LocalTime.MIN),
                date.atTime(LocalTime.MAX)
        );
    }

    @Test
    void throwExceptionInDeleteAllSensorDataWhenWrongDateFormat() {

        // given
        String date = "2002.11.23";

        // when
        // then
        assertThatThrownBy(() -> luminositySensorService.deleteAllSensorData(date))
                .isInstanceOf(WrongDateFormatException.class)
                .hasMessageContaining("Wrong date format. Change to match yyyy-mm-dd and " +
                        "and make sure entered values are valid");
    }
}