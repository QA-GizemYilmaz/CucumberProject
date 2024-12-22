package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    WebDriver driver;
    WebDriverWait wait;

    // Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Çerez kabul etme metodu
    public void acceptCookies() {
        try {
            WebElement acceptCookiesButton = driver.findElement(By.id("onetrust-accept-btn-handler"));
            acceptCookiesButton.click();
        } catch (Exception e) {
            System.out.println("Çerez kabul etme butonu bulunamadı: " + e.getMessage());
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
