package ru.hse.elarateam.adminconsole.services;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.hse.elarateam.adminconsole.dto.ColorInfoDTO;
import ru.hse.elarateam.adminconsole.web.services.ColorsService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
@ActiveProfiles("local-dev")
class ColorsServiceTest {

    @Autowired
    private ColorsService colorsService;

    @Test
    void createColor() {
        var colorInfoDTO = ColorInfoDTO.builder()
                .name("red")
                .hex("#ff0000")
                .build();

        var color = colorsService.createColor(colorInfoDTO);

        log.info("created color: {}", color);
        assertEquals(colorInfoDTO.getHex(), color.getHex());
        assertEquals(colorInfoDTO.getName(), color.getName());
    }

    @Test
    void createColorAlreadyExists() {
        var colorInfoDTO = ColorInfoDTO.builder()
                .id(1L)
                .name("pink")
                .hex("#ff0ff0")
                .build();

        assertThrows(RuntimeException.class, () -> colorsService.createColor(colorInfoDTO));
    }

    @Test
    void createColorNameBlank() {
        var colorInfoDTO = ColorInfoDTO.builder()
                .name("")
                .hex("#ff0000")
                .build();

        assertThrows(ConstraintViolationException.class, () -> colorsService.createColor(colorInfoDTO));
    }

    @Test
    void createColorHexBlank() {
        var colorInfoDTO = ColorInfoDTO.builder()
                .name("sdf")
                .hex("")
                .build();

        assertThrows(ConstraintViolationException.class, () -> colorsService.createColor(colorInfoDTO));
    }

    @Test
    void createColorNameNull() {
        var colorInfoDTO = ColorInfoDTO.builder()
                .name(null)
                .hex("#ff0000")
                .build();

        assertThrows(ConstraintViolationException.class, () -> colorsService.createColor(colorInfoDTO));
    }

    @Test
    void updateColor() {
        var colorInfoDTO = ColorInfoDTO.builder()
                .name("red")
                .hex("#ff0000")
                .build();

        var color = colorsService.createColor(colorInfoDTO);

        log.info("created color: {}", color);

        var updatedColorInfoDTO = ColorInfoDTO.builder()
                .id(color.getId())
                .name("blue")
                .hex("#0000ff")
                .build();

        var updatedColor = colorsService.updateColor(updatedColorInfoDTO);

        log.info("updated color: {}", updatedColor);

        assertEquals(updatedColorInfoDTO.getName(), updatedColor.getName());
        assertEquals(updatedColorInfoDTO.getHex(), updatedColor.getHex());
    }

    @Test
    void updateColorNameBlank() {
        var colorInfoDTO = ColorInfoDTO.builder()
                .name("red")
                .hex("#ff0000")
                .build();

        var color = colorsService.createColor(colorInfoDTO);

        log.info("created color: {}", color);

        var updatedColorInfoDTO = ColorInfoDTO.builder()
                .id(color.getId())
                .name("")
                .hex("#0000ff")
                .build();

        assertThrows(ConstraintViolationException.class, () -> {
            colorsService.updateColor(updatedColorInfoDTO);
        });
    }

    @Test
    void updateColorHexBlank() {
        var colorInfoDTO = ColorInfoDTO.builder()
                .name("red")
                .hex("#ff0000")
                .build();

        var color = colorsService.createColor(colorInfoDTO);

        log.info("created color: {}", color);

        var updatedColorInfoDTO = ColorInfoDTO.builder()
                .id(color.getId())
                .name("")
                .hex("#0000ff")
                .build();

        assertThrows(ConstraintViolationException.class, () -> {
            colorsService.updateColor(updatedColorInfoDTO);
        });
    }

    @Test
    void updateColorNotFound() {
        var updatedColorInfoDTO = ColorInfoDTO.builder()
                .id(1L)
                .name("Nonexistent color")
                .hex("#0000ff")
                .build();

        assertThrows(RuntimeException.class, () -> {
            colorsService.updateColor(updatedColorInfoDTO);
        });
    }

    @Test
    void deleteColor() {
        var colorInfoDTO = ColorInfoDTO.builder()
                .name("red")
                .hex("#ff0000")
                .build();

        var color = colorsService.createColor(colorInfoDTO);

        log.info("created color: {}", color);

        colorsService.deleteColor(color.getId());
    }

    @Test
    void deleteColorNotFound() {
        assertThrows(RuntimeException.class, () -> {
            colorsService.deleteColor(1L);
        });
    }
}