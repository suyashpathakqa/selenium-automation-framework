package pages;

import org.openqa.selenium.*;
import utils.WaitUtils;

public class HomePage {

    WebDriver driver;
    WaitUtils wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }

    By addToCartBtn = By.id("add-to-cart-sauce-labs-backpack");
    By cartIcon = By.className("shopping_cart_link");

    public void addProductToCart() {
        wait.waitForClickable(addToCartBtn).click();
    }

    public void clickCart() {
        wait.waitForClickable(cartIcon).click();
        wait.waitForUrlContains("cart.html");
    }
}