package pages;

import org.openqa.selenium.*;
import utils.WaitUtils;

public class CheckoutPage {

    WebDriver driver;
    WaitUtils wait;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
    }

    By firstName = By.id("first-name");
    By lastName = By.id("last-name");
    By zip = By.id("postal-code");
    By continueBtn = By.id("continue");
    By finishBtn = By.id("finish");

    public void enterDetails(String fn, String ln, String zipCode) {
        wait.waitForVisibility(firstName).sendKeys(fn);
        wait.waitForVisibility(lastName).sendKeys(ln);
        wait.waitForVisibility(zip).sendKeys(zipCode);
    }

    public void clickContinue() {
        wait.waitForClickable(continueBtn).click();
        wait.waitForUrlContains("checkout-step-two");
    }

    public void clickFinish() {
        wait.waitForClickable(finishBtn).click();
    }

    public boolean isOrderSuccessful() {
        return driver.getPageSource().contains("Thank you for your order");
    }
}