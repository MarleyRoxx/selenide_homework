package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CardDeliveryTest {

    @Test
    void shouldRegisteredByAccountNumber() {
        Selenide.open("http://localhost:9999");
        SelenideElement form = $("form");

        form.$("[data-test-id='city'] input")
                .setValue("Кемерово");

        String deliveryDate = LocalDate.now()
                .plusDays(3)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        SelenideElement dateInput = $("[data-test-id='date'] input");
        dateInput.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        dateInput.sendKeys(Keys.BACK_SPACE);
        dateInput.sendKeys(deliveryDate);

        form.$("[data-test-id='name'] input")
                .setValue("Герасимова Екатерина");

        form.$("[data-test-id='phone'] input")
                .setValue("+79950659050");

        form.$("[data-test-id='agreement']")
                .click();

        form.$$("button")
                .findBy(text("Забронировать"))
                .click();

        $("[data-test-id='notification']")
                .should(visible, Duration.ofSeconds(15))
                .shouldHave(text("Успешно!"))
                .shouldHave(text("Встреча успешно забронирована на " + deliveryDate));


    }
}
