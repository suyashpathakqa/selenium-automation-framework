package tests;

import org.testng.Assert;
import org.testng.annotations.*;

import base.BaseTest;
import pages.*;

public class CheckoutTestE2E extends BaseTest {

    LoginPage lp;
    HomePage hp;
    CartPage cp;
    CheckoutPage ch;

    @BeforeMethod
    public void start() {
        setup();

        lp = new LoginPage(driver);
        hp = new HomePage(driver);
        cp = new CartPage(driver);
        ch = new CheckoutPage(driver);
    }

    @Test
    public void completeOrderTest() {

        lp.login("standard_user", "secret_sauce");

        hp.addProductToCart();
        hp.clickCart();

        cp.clickCheckout();

        ch.enterDetails("John", "Doe", "560001");
        ch.clickContinue();
        ch.clickFinish();

        Assert.assertTrue(ch.isOrderSuccessful(), "Order not completed");
    }

    @AfterMethod
    public void end() {
        tearDown();
    }
}