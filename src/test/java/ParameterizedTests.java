import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class ParameterizedTests {

    @BeforeEach
    void prediction() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";
    }

    @AfterEach
    void closeBrowser() {
        closeWebDriver();
    }

    @ValueSource(strings = {"Yes", "Impressive"})
    @ParameterizedTest(name = "Проверка выбора \"{0}\"")
    void radioButtonTest(String testData) {
        open("/radio-button");

        $(byText(testData)).click();

        $(".text-success").shouldHave(text(testData));
    }

    @CsvSource({
            "0, Cierra, Vega, 39, cierra@example.com, Insurance",
            "1, Alden, Cantrell, 45, alden@example.com, 12000, Compliance",
            "2, Kierra, Gentry, 29, kierra@example.com, 2000, Legal"
    })
    @ParameterizedTest(name = "Проверка в таблице строки \"{0}\"")
    void webtablesTest(int rowNumber, String firstName, String lastName, String age,
                       String email, String department) {
        open("/webtables");

        $(".rt-tbody").$$(".rt-tr-group").get(rowNumber)
                .shouldHave(text(firstName))
                .shouldHave(text(lastName))
                .shouldHave(text(age))
                .shouldHave(text(email))
                .shouldHave(text(department));

    }

    static Stream<Arguments> DataProvider() {
        return Stream.of(
                Arguments.of("0", List.of()),
                Arguments.of("1", List.of("Cras justo odio")),
                Arguments.of("2", List.of("Cras justo odio", "Dapibus ac facilisis in")),
                Arguments.of("3", List.of("Cras justo odio", "Dapibus ac facilisis in", "Morbi leo risus")),
                Arguments.of("4", List.of("Cras justo odio", "Dapibus ac facilisis in", "Morbi leo risus", "Porta ac consectetur ac"))
        );
    }

    @MethodSource(value = "DataProvider")
    @ParameterizedTest(name = "Проверка выбора элементов в количестве: {0}")
    void selectableTest(String countItems, List<String> itemList)
    {
        open("/selectable");

        for(String item: itemList) {
            $("#verticalListContainer").$$("li").findBy(text(item)).shouldHave(text(item));
        }
    }

}
