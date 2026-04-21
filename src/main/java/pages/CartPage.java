package pages;

import org.openqa.selenium.*;
import utils.WaitUtils;

public class CartPage {

    WebDriver driver;
    WaitUtils wait;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }

    By checkoutBtn = By.id("checkout");

    public void clickCheckout() {
        wait.waitForClickable(checkoutBtn).click();
        wait.waitForUrlContains("checkout-step-one");
    }
}