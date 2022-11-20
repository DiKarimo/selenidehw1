import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.open;
import static java.time.Duration.*;
import static org.openqa.selenium.Keys.*;


public class CardDeliveryTest {
    private String date (int addDays) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    public void shouldSuccessfulCardApplication () {
        String planDate = date(3);
        open ("http://localhost:9999");
        $("[data-test-id=\"city\"] input").setValue("Казань");
        $("[data-test-id=\"date\"] input").sendKeys(chord(PAGE_DOWN,END),DELETE);
        $("[data-test-id=\"date\"] input").sendKeys(planDate);
        $("[data-test-id=\"name\"] input").setValue("Гадя Хренова");
        $("[data-test-id=\"phone\"] input").sendKeys("+66666666666");
        $("[data-test-id=\"agreement\"]").click();
        $x("//*[text()='Забронировать']").click();
        $(byText("Успешно!")).should(visible, ofSeconds(15));
        $(".notification__content")
                .shouldBe(visible)
                .shouldHave(text("Встреча успешно забронирована на " + planDate), ofSeconds(15));

    }
}
