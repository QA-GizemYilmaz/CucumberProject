package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {
    WebDriver driver;

    // Constructor
    public CartPage(WebDriver driver) {
        this.driver = driver;
    }


    public void GoToCart() {
        driver.findElement(By.id("shoppingCart")).click();
    }
}
