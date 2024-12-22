package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.CartPage;
import pages.HomePage;

import java.time.Duration;
import java.util.List;

public class UI_Steps {
    WebDriver driver;
    String productPrice;
    HomePage homePage;

    public UI_Steps() {
        homePage = new HomePage(driver);
    }

    @Given("Kullanıcı {string} adresine gider")
    public void kullanıcı_adresine_gider(String url) {
        homePage.goToUrl(url);


    }


    @Given("Kullanıcı çerezleri kabul eder")
    public void kullanıcı_çerezleri_kabul_eder() {
        HomePage homePage = new HomePage(driver);
        homePage.acceptCookies();


    }

    @When("Kullanıcı {string} -> {string} -> {string} kategorisine gider")
    public void kullanıcı_kategorisine_gider(String kategori, String altKategori, String altaltKategori) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions actions = new Actions(driver);

        WebElement elektronik = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),, '"+ kategori + "']")));
        actions.moveToElement(elektronik).perform();
        homePage.waitForSeconds(10000);

        WebElement bilgisayar = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='" + altKategori + "']")));
        actions.moveToElement(bilgisayar).perform();
        homePage.waitForSeconds(10000);

        WebElement apple = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(), '" + altaltKategori + "')]")));
        actions.moveToElement(apple).perform();
        apple.click();
        homePage.waitForSeconds(10000);


    }


    @When("Filtrelerden {string} seçer")
    public void filtrelerden_seçer() {
        WebElement button = driver.findElement(By.xpath("//*[@id='HeroFilter_2311f3f4-4702-4778-0fbf-11dc4a688194']//a[@class='moria-PillButton-BNQ0U shbxLs8byorrdfvi heroContent-IuUq0v8_wAueO8ELIHZ5']//span[contains(text(),'13,2')]"));
        button.click();
        homePage.waitForSeconds(10000);
        driver.navigate().refresh();

    }

    @When("Kullanıcı çıkan sonuçlardan en yüksek fiyatlı ürüne tıklar")
    public void kullanıcı_en_yüksek_fiyatlı_ürüne_tıklar() {
        // create a list with the prices of the products that are listed
        List<WebElement> prices = driver.findElements(By.cssSelector("div[data-test-id='price-current-price']"));
        // here find the highest price of the product
        WebElement highestPriceElement = prices.getFirst();
        highestPriceElement.click();
        productPrice = driver.findElement(By.cssSelector("div[data-test-id='price-current-price']")).getText();


    }

    @When("Kullanıcı açılan ürün detay sayfasındaki Sepete Ekle butonuna tıklar")
    public void kullanıcı_butonuna_tıklar() {
        driver.findElement(By.cssSelector("button[data-test-id='add-to-cart']")).click();


    }

    @Then("Ürünün sepete eklendiğini ve fiyatının ürün detay sayfasıyla aynı olduğunu doğrular")
    public void ürünü_sepeti_doğrular() {
        CartPage card = new CartPage(driver);
        card.GoToCart();
        //save the price of the product
        String basketPrice = driver.findElement(By.className("product_price_uXU6Qb")).getText();
        //compare the prices of the same product on the product page & on the card page
        assert productPrice.equals(basketPrice) : "Fiyatlar eşleşmiyor!";
        driver.quit();

    }

    @After
    public void tearDown() {
        System.out.println("Bye Bye!");
        if (driver != null) {
            driver.quit();
        }
    }
}
