package ua.lviv.iot.greenhouse.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.lviv.iot.greenhouse.dao.SoilSensorDAO;
import ua.lviv.iot.greenhouse.dto.soil_sesnor.SoilSensorDTO;
import ua.lviv.iot.greenhouse.dto.soil_sesnor.SoilSensorToUpdateDTO;
import ua.lviv.iot.greenhouse.exception.NoDataFoundException;
import ua.lviv.iot.greenhouse.models.SoilSensor;

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
class SoilSensorServiceImplTest {

    @Mock
    private SoilSensorDAO soilSensorDAO;
    private SoilSensorServiceImpl soilSensorService;

    @BeforeEach
    void setUp() {
        soilSensorService = new SoilSensorServiceImpl(soilSensorDAO);
    }

    @Test
    void canCreateSensorData() {

        // given
        LocalDateTime dateTime = LocalDateTime.of(2021, Month.MAY, 1, 23, 45, 2);
        SoilSensorDTO soilSensorDTO = new SoilSensorDTO(dateTime, 70.6, 23.38);
        SoilSensor expectedSoilSensor = new SoilSensor(new SoilSensor.Data(dateTime, 70.6, 23.38));

        // when
        soilSensorService.createSensorData(soilSensorDTO);

        // then
        ArgumentCaptor<SoilSensor> argumentCaptor = ArgumentCaptor.forClass(SoilSensor.class);
        verify(soilSensorDAO).save(argumentCaptor.capture());
        SoilSensor capturedSoilSensor = argumentCaptor.getValue();
        assertThat(capturedSoilSensor).isEqualTo(expectedSoilSensor);
    }

    @Test
    void canGetAllSensorDataWhenDateIsNullAndThereIsData() {

        // given
        LocalDateTime dateTime = LocalDateTime.of(2021, Month.MAY, 1, 23, 45, 2);
        SoilSensor soilSensor = new SoilSensor(new SoilSensor.Data(dateTime, 70.6, 23.38));

        given(soilSensorDAO.findAll()).willReturn(List.of(soilSensor, soilSensor)); // not empty list

        // when
        soilSensorService.getAllSensorData(null);

        // then
        verify(soilSensorDAO).findAll();
    }

    @Test
    void throwExceptionWhenDateIsNullAndThereIsNoData() {

        // given
        given(soilSensorDAO.findAll()).willReturn(Collections.emptyList()); // not empty list

        // when
        // then
        assertThatThrownBy(() -> soilSensorService.getAllSensorData(null))
                .isInstanceOf(NoDataFoundException.class)
                .hasMessageContaining("There is no soil sensor data yet");
    }

    @Test
    void canGetAllSensorDataWhenDateIsGivenAndThereIsData() {

        // given
        LocalDate date = LocalDate.of(2021, Month.MAY, 1);
        LocalDateTime dateTime = LocalDateTime.of(2021, Month.MAY, 1, 23, 45, 2);
        SoilSensor soilSensor = new SoilSensor(new SoilSensor.Data(dateTime, 70.6, 23.38));

        given(soilSensorDAO.findSensorByData_LocalDateTimeBetween(
                date.atTime(LocalTime.MIN),
                date.atTime(LocalTime.MAX)
        ))
                .willReturn(List.of(soilSensor, soilSensor)); // not empty list

        // when
        soilSensorService.getAllSensorData(date.toString());

        // then
        verify(soilSensorDAO).findSensorByData_LocalDateTimeBetween(
                date.atTime(LocalTime.MIN),
                date.atTime(LocalTime.MAX)
        );
    }

    @Test
    void throwExceptionWhenDateIsGivenAndThereIsNoData() {

        // given
        LocalDate date = LocalDate.of(2020, Month.MAY, 1);

        given(soilSensorDAO.findSensorByData_LocalDateTimeBetween(
                date.atTime(LocalTime.MIN),
                date.atTime(LocalTime.MAX)
        ))
                .willReturn(Collections.emptyList()); // empty list

        // when
        // then
        assertThatThrownBy(() -> soilSensorService.getAllSensorData(date.toString()))
                .isInstanceOf(NoDataFoundException.class)
                .hasMessageContaining("There is no soil sensor data for this time");
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
        SoilSensorToUpdateDTO soilSensorToUpdateDTO = new SoilSensorToUpdateDTO(1L, 60.8, 23.2);
        given(soilSensorDAO.existsById(soilSensorToUpdateDTO.getId())).willReturn(true);
        LocalDateTime dateTime = LocalDateTime.of(2021, Month.MAY, 1, 23, 45, 2);
        SoilSensor soilSensor = new SoilSensor(1L, new SoilSensor.Data(dateTime, 70.6, 23.38));
        given(soilSensorDAO.findSensorById(soilSensorToUpdateDTO.getId())).willReturn(soilSensor);

        // when
        soilSensorService.updateDataById(soilSensorToUpdateDTO);

        // then
        ArgumentCaptor<SoilSensor> argumentCaptor = ArgumentCaptor.forClass(SoilSensor.class);
        verify(soilSensorDAO).save(argumentCaptor.capture());
        SoilSensor capturedSoilSensor = argumentCaptor.getValue();
        assertThat(capturedSoilSensor.getId()).isEqualTo(soilSensorToUpdateDTO.getId());
        assertThat(capturedSoilSensor.getData().getSoilHumidity()).isEqualTo(soilSensorToUpdateDTO.getSoilHumidity());
        assertThat(capturedSoilSensor.getData().getSoilTemperature()).isEqualTo(soilSensorToUpdateDTO.getSoilTemperature());
    }

    @Test
    void canDeleteAllSensorDataWhenDateIsNull() {

        // when
        soilSensorService.deleteAllSensorData(null);

        // then
        verify(soilSensorDAO).deleteAll();
    }
}