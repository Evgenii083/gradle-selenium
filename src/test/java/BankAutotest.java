import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankAutotest {
    public WebDriver driver;


    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }


    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }


    @Test
    public void successfulCase() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Сергей");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79113438790");
        driver.findElement(By.className("checkbox__text")).click();
        driver.findElement(By.className("button__content")).click();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void successfulSecondCase() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов-Смирнов Антон");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79313456825");
        driver.findElement(By.className("checkbox__text")).click();
        driver.findElement(By.className("button__content")).click();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void emptyFormCase() {

        driver.findElement(By.className("checkbox__text")).click();
        driver.findElement(By.className("button__content")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void emptyNameCase() {
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79993334422");
        driver.findElement(By.className("checkbox__text")).click();
        driver.findElement(By.className("button__content")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void emptyPhoneCase() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Смирнов Илья");
        driver.findElement(By.className("button__content")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void englishInputCase() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Forest Gump");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79992005522");
        driver.findElement(By.className("checkbox__text")).click();
        driver.findElement(By.className("button__content")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void wrongPhoneFormatCase() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Никулин Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("89992005522");
        driver.findElement(By.className("checkbox__text")).click();
        driver.findElement(By.className("button__content")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void wrongPhoneFormatCase2() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Никулин Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7999200552");
        driver.findElement(By.className("checkbox__text")).click();
        driver.findElement(By.className("button__content")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    public void missedCheckBoxCase() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Никулин Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79992005529");
        driver.findElement(By.className("button__content")).click();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        String actual = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid .checkbox__text")).getText().trim();
        assertEquals(expected, actual);
    }
}

