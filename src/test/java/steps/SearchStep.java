package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

import static java.lang.Thread.sleep;

public class SearchStep {

    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Given("booking search page is opened")
    public void bookingSearchPageIsOpened() {
        driver.get("https://www.booking.com/searchresults.en-gb.html");
    }

    @When("user searches for {string}")
    public void userSearchesFor(String hotel) throws InterruptedException {
        sleep(5000);
        WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='ss']")));
        searchInput.sendKeys(hotel);
        sleep(5000);
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
        searchButton.click();
    }

    @Then("{string} hotel is show")
    public void hotelIsShow(String expectedResult) throws InterruptedException {
        String xpathExpression = String.format("//*[@data-testid='title' and contains(., '%s')]", expectedResult);
        By hotelTitleLocator = By.xpath(xpathExpression);
        WebElement hotelTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(hotelTitleLocator));
        Assert.assertTrue(hotelTitle.isDisplayed());
    }

    @And("{string} hotel raiting is {string}")
    public void akraKemerHotelRaitingIs(String hotel, String raiting) {
        String xpathExpression = String.format(
                "//div[@data-testid='property-card'][.//div[@data-testid='title' and contains(text(), '%s')]]" +
                        "//div[@data-testid='review-score']//div[@aria-hidden='true']",
                hotel);
        By raitingLocator = By.xpath(xpathExpression);
        WebElement raitingElement = wait.until(ExpectedConditions.visibilityOfElementLocated(raitingLocator));
        String actualRating = raitingElement.getText().trim();
        Assert.assertEquals(actualRating, raiting, "Рейтинг отеля не совпадает!");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
