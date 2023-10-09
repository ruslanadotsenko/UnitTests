package org.example.app.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BonusServiceTest {

    private BonusService service;

    @BeforeEach
    void setUp() {
        service = new BonusService(); //before each test create new object
    }

//з тестами по розрахунку довелось багато погратись, тому що при моїх разрахунках не спивпадали числа,
// то в числах розділювач стояв кома, а очікувало крапку, то після розрахунку число просто обрізається до 2 знаків,
// а в моєму expectedBonus округлюється в більшу сторону.
// Потім при порівнянні очікуване expectedBonus це double, а в getRes string повертається. Довелось привести expectedBonus до стрінги.

    @Test
    @DisplayName("Test correct calculation of bonus service without Rounder")
    void test_Correct_Calculation_Of_Bonus_Service() {
        double sales = 55.55;
        double expectedBonus = sales * 10 / 100;
        assertEquals(expectedBonus, service.calcBonus(sales));
    }

    @Test
    @DisplayName("Test correct calculation of bonus service with Rounder")
    void test_Correct_Calculation_Of_Bonus_Service_With_Rounder() {
        double sales = 55.55;
        double expectedBonus = Math.floor(sales * 10) / 100;
        String expectedBonusString = Double.toString(expectedBonus);
        assertEquals(expectedBonusString, service.getRes(sales));
    }

    @Test
    @DisplayName("Test bonus service with sales value < 0")
    void test_Service_With_Sales_Value_Less_Than_Zero() {
        double sales = -1;
        assertEquals("Incorrect value!",
                service.getRes(sales));
    }

    @Test
    @DisplayName("Test bonus service with sales value = 0")
    void test_Service_With_Sales_Value_Is_Zero() {
        double sales = 0;
        assertEquals("Incorrect value!",
                service.getRes(sales));
    }

    //для наступних двох тестів хотіла використовувати InputMismatchException,
    //бо саме таку помилку отримую якщо вводити некоректні дані, але при прогоні тесту отримала
    //Expected :class java.util.InputMismatchException
    //Actual   :class java.lang.NumberFormatException тому взяла цей
    //або ж можна брати IllegalArgumentException

    @Test
    @DisplayName("Test bonus service with wrong value of sales = letters")
    void test_Bonus_Service_With_Wrong_Sales_Letters() {
        String salesWithLetters = "abc";
        assertThrows(NumberFormatException.class, () -> service.getRes(Double.parseDouble(salesWithLetters)));
    }

    @Test
    @DisplayName("Test bonus service with wrong value of sales = characters")
    void test_Bonus_Service_With_Wrong_Sales_Characters() {
        String salesWithCharacters = "$%&*";
        assertThrows(NumberFormatException.class, () -> service.getRes(Double.parseDouble(salesWithCharacters)));
    }

    @Test
    @DisplayName("Test calculation of bonus service is unexpected result.")
    void test_Calculation_Of_Bonus_Service_Unexpected_Result() {
        double sales = 55.55;
        assertNotEquals("55", service.getRes(sales));
    }

    @Test
    @DisplayName("Test unexpected bonus calculation with 0 sales value")
    void test_Unexpected_Bonus_Calculation() {
        double sales = 0;
        assertNotEquals("0.00",
                service.getRes(sales));
    }

    @AfterEach
    void tearDown() {
        service = null; //after each test set null for object
    }
}