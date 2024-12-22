package stepdefinitions;

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
    HomePage homePage; // HomePage instance


    @Given("Kullanıcı {string} adresine gider")
    public void kullanıcı_adresine_gider(String url) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        driver.get(url);
        driver.manage().window().maximize();


    }


    @Given("Kullanıcı çerezleri kabul eder")
    public void kullanıcı_çerezleri_kabul_eder() {
        HomePage homePage = new HomePage(driver); // HomePage nesnesi oluşturulur
        homePage.acceptCookies();


    }

    @When("Kullanıcı {string} -> {string} -> {string} kategorisine gider")
    public void kullanıcı_kategorisine_gider(String kategori, String altKategori, String altaltKategori) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions actions = new Actions(driver);


        WebElement elektronik = driver.findElement(By.xpath("//span[contains(text(), '" + kategori + "')]"));
        actions.moveToElement(elektronik).perform();

        //homePage.waitForElementToBeVisible(By.xpath("//a[text()='" + altKategori + "']"));
        WebElement bilgisayar = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='" + altKategori + "']")));
        actions.moveToElement(bilgisayar).perform();

        WebElement apple = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(), '" + altaltKategori + "')]")));
        actions.moveToElement(apple).perform();
        apple.click();
        homePage.waitForSeconds(10000);
    }

    @When("Filtrelerden {string} seçer")
    public void filtrelerden_seçer() {
        //WebElement button = driver.findElement(By.xpath("//a[contains(@class, 'moria-PillButton') and contains(text(), '13,2 inç')]"));
        //button.click();
        homePage.waitForSeconds(10000);
        driver.navigate().refresh();

    }

    @When("Kullanıcı çıkan sonuçlardan en yüksek fiyatlı ürüne tıklar")
    public void kullanıcı_en_yüksek_fiyatlı_ürüne_tıklar() {
        List<WebElement> prices = driver.findElements(By.cssSelector("div[data-test-id='price-current-price']"));
        WebElement highestPriceElement = prices.get(0);
        highestPriceElement.click();
        productPrice = driver.findElement(By.cssSelector("div[data-test-id='price-current-price']")).getText();
    }

    @When("Kullanıcı açılan ürün detay sayfasındaki Sepete Ekle butonuna tıklar")
    public void kullanıcı_butonuna_tıklar() {
        driver.findElement(By.cssSelector("button[data-test-id='add-to-cart']")).click();


    }

    @Then("Ürünün sepete eklendiğini ve fiyatının ürün detay sayfasıyla aynı olduğunu doğrular")
    public void ürünü_sepeti_doğrular() {
        CartPage card = new CartPage(driver); // Sayfa nesnesini oluştur
        card.GoToCart();
        String basketPrice = driver.findElement(By.className("product_price_uXU6Qb")).getText();
        assert productPrice.equals(basketPrice) : "Fiyatlar eşleşmiyor!";
        driver.quit();
    }
}
