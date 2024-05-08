package com.shopAminute.shopAminute;

import com.shopAminute.shopAminute.settings.Status;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StatusTest {

    @Test
    void ShouldReturnCorrectNames() {
        String[] expectedNames = {"Принят", "Оформлен", "Ожидает", "Получен"};
        Status[] statuses = Status.values();
        String[] actualNames = new String[statuses.length];
        for (int i = 0; i < statuses.length; i++) {
            actualNames[i] = statuses[i].name();
        }
        assertArrayEquals(expectedNames, actualNames);
    }

    @Test
    void ShouldBeConsistent() {

        assertTrue(Status.Принят.ordinal() < Status.Оформлен.ordinal());
        assertTrue(Status.Оформлен.ordinal() < Status.Ожидает.ordinal());
        assertTrue(Status.Ожидает.ordinal() < Status.Получен.ordinal());
    }

}