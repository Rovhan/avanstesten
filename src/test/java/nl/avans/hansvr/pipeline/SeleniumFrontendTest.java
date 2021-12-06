package nl.avans.hansvr.pipeline;


import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Log4j2
public class SeleniumFrontendTest {
    private static WebDriver driver;
    private Actions builder;

    @BeforeAll
    public static void beforeAll(){
        driver = new ChromeDriver();
        goToHomepage();
    }

    @BeforeEach
    public void setUp() {
        builder = new Actions(driver);
        loginAndEmptyShoppingCartIfNeeded();
        goToHomepage();
    }

    private static void goToHomepage() {
        driver.get("http://demowebshop.tricentis.com/");
    }

    @AfterEach
    public void tearDown() {
        emptyShoppingCart();
        WebElement log_out = driver.findElement(By.linkText("Log out"));
        log_out.click();
    }

    @AfterAll
    public static void closeDown() {
        driver.quit();
    }

    @Test
    public void AddItemsToChartsAndCheckIfShoppingCartHasCorrectTotalPrice() {
        int randomNumberOfBooks = (int) (Math.random() * 9 + 2);
        int randomNumberOfComputers = (int) (Math.random() * 9 + 2);

        List<QuantityAndPrice> quantityAndPrices = new ArrayList<>();


        driver.findElement(By.cssSelector(".top-menu")).click();

        WebElement element = driver.findElement(By.cssSelector(".top-menu > li:nth-child(2)"));

        builder.moveToElement(element).perform();

        driver.findElement(By.linkText("Desktops")).click();
        driver.findElement(By.cssSelector(".item-box:nth-child(1) .button-2")).click();
        driver.findElement(By.cssSelector(".item-box:nth-child(1) img")).click();

        BigDecimal priceOfProduct1 = getPriceOfProduct(By.xpath("//span[@itemprop='price']"));
        driver.findElement(By.cssSelector("dd:nth-child(2) li:nth-child(1) > label")).click();
        fillInQtyInput(randomNumberOfComputers);
        quantityAndPrices.add(new QuantityAndPrice(randomNumberOfComputers, priceOfProduct1));

        clickOnElementWithClassName("add-to-cart-button");
        waitForSuccessMessage();
        clickOnElementWithClassName("cart-label");
        driver.findElement(By.name("continueshopping")).click();
        driver.findElement(By.linkText("Books")).click();
        driver.findElement(By.cssSelector(".item-box:nth-child(1) img")).click();

        fillInQtyInput(randomNumberOfBooks);
        BigDecimal priceOfProduct2 = getPriceOfProduct(By.xpath("//span[@itemprop='price']"));
        quantityAndPrices.add(new QuantityAndPrice(randomNumberOfBooks, priceOfProduct2));
        clickOnElementWithClassName("add-to-cart-button");
        waitForSuccessMessage();
        driver.findElement(By.cssSelector(".ico-cart > .cart-label")).click();
        String totalChartFinal = driver.findElement(By.className("cart-total-right")).getText();
        BigDecimal valueOfItems = expectedValueOfItems(quantityAndPrices);
        assertThat(totalChartFinal, is(valueOfItems.toString()));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/case.csv", numLinesToSkip = 1)
    public void givenTwoProductsAddedToShoppingCartThenCorrectPriceShouldBeCalculated(String category, String subCategory, String productTitle, int quantity, String secondCategory, String secondSubCategory, String secondProducttitle, int secondQuantity) {
        QuantityAndPrice quantityAndPriceFirst = addItemToShoppingCartFromCategoryWithProductTitle(category, subCategory, productTitle, quantity);
        QuantityAndPrice quantityAndPriceSecond = addItemToShoppingCartFromCategoryWithProductTitle(secondCategory, secondSubCategory, secondProducttitle, secondQuantity);

        BigDecimal valueOfItems = expectedValueOfItems(List.of(quantityAndPriceFirst, quantityAndPriceSecond));
        driver.findElement(By.cssSelector(".ico-cart > .cart-label")).click();
        String totalChartFinal = driver.findElement(By.className("cart-total-right")).getText();
        assertThat(totalChartFinal, is(valueOfItems.toString()));
    }

    private void loginAndEmptyShoppingCartIfNeeded() {
        try {
            driver.findElement(By.linkText("Log out")).click();
        } catch (Exception e) {
            // already logged out.
        }
        driver.findElement(By.linkText("Log in")).click();
        driver.findElement(By.id("Email")).sendKeys("hvroon92@gmail.com");
        driver.findElement(By.id("Password")).sendKeys("Zakkie45");
        driver.findElement(By.cssSelector(".login-button")).click();
        if(Integer.parseInt(driver.findElement(By.className("cart-qty")).getText().replaceAll("[^0-9]", "")) > 0) {
            emptyShoppingCart();
        }
    }

    private void clickOnElementWithClassName(String s) {
        driver.findElement(By.className(s)).click();
    }

    private BigDecimal expectedValueOfItems(List<QuantityAndPrice> quantityAndPrices) {
        return quantityAndPrices.stream().map(item -> item.price().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void fillInQtyInput(int numberOfItems) {
        WebElement element2 = driver.findElement(By.className("qty-input"));
        builder.doubleClick(element2).perform();

        element2.sendKeys(String.valueOf(numberOfItems));
    }

    private BigDecimal getPriceOfProduct(By by) {
        String text = driver.findElement(by).getText();
        return new BigDecimal(text);
    }


    private void emptyShoppingCart() {
        driver.findElement(By.cssSelector(".ico-cart > .cart-label")).click();
        List<WebElement> elements = driver.findElements(By.className("cart-item-row"));
        if(!elements.isEmpty()) {
            elements.forEach(this::removeItemFromCart);
            driver.findElement(By.name("updatecart")).click();
        }
        assertThat(driver.findElement(By.cssSelector(".order-summary-content")).getText(), is("Your Shopping Cart is empty!"));
    }

    private void removeItemFromCart(WebElement cartItem) {
        WebElement input = cartItem.findElement(By.cssSelector(".remove-from-cart > input"));
        if (!input.isSelected()) {
            input.click();
        }
    }

    private record QuantityAndPrice(int quantity, BigDecimal price) {
    }

    private QuantityAndPrice addItemToShoppingCartFromCategoryWithProductTitle(@Nonnull String category, @Nullable String subCategory, @Nonnull String productTitle, int quantity) {

        var elementToClick = driver.findElement(By.cssSelector(String.format(".top-menu a[href='/%s']", category)));
        if(subCategory != null) {
            builder.moveToElement(elementToClick).perform();
            elementToClick = driver.findElement(By.cssSelector(String.format(".top-menu a[href='/%s']", subCategory)));
        }
        elementToClick.click();
        driver.findElement(By.xpath(String.format("//*[text()='%s']", productTitle))).click();
        BigDecimal priceOfProduct = getPriceOfProduct(By.xpath("//span[@itemprop='price']"));
        fillInQtyInput(quantity);
        clickOnElementWithClassName("add-to-cart-button");
        waitForSuccessMessage();
        return new QuantityAndPrice(quantity, priceOfProduct);
    }

    private void waitForSuccessMessage() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("success")));
    }
}
