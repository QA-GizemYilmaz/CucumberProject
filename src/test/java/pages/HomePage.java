package pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    WebDriver driver;
    WebDriverWait wait;

    // Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void goToUrl(String url) {
        // ChromeOptions ile tarayıcı ayarları
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");

        // WebDriverManager ile tarayıcı sürümünü eşleştir
        WebDriverManager.chromedriver().browserVersion("131.0").setup();

        // WebDriver'i başlat
        driver = new ChromeDriver(options);
        driver.get(url);

        // Tarayıcıyı tam ekran yap
        driver.manage().window().maximize();
    }

    // Çerez kabul etme metodu
    public void acceptCookies() {
        try {
            // WebDriverWait ile maksimum 10 saniye bekler
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 10 saniye bekleme süresi

            // Elementi bekler ve bulunduğunda döner
            WebElement acceptCookiesButton = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='onetrust-accept-btn-handler' and text()='Kabul et']"))
            );

            // Bulunan elementi tıkla
            acceptCookiesButton.click();
            System.out.println("Cookie accept button clicked.");
        } catch (Exception e) {
            System.out.println("Cookie accept button not found: " + e.getMessage());
        }
    }

    // Bekleme methodu
    public void waitForSeconds(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void waitForElementToBeVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
