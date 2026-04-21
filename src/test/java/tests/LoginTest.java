package tests;

import org.testng.Assert;
import org.testng.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import base.BaseTest;
import pages.LoginPage;
import utils.ExcelUtils;
import utils.TestListener;

@Listeners(TestListener.class)
public class LoginTest extends BaseTest {

    LoginPage lp;

    private static final Logger log = LogManager.getLogger(LoginTest.class);

    @BeforeMethod
    public void start() {
        setup();
        lp = new LoginPage(driver);
        log.info("Browser launched and LoginPage initialized");
    }

    // DataProvider (Excel)
    @DataProvider(name = "loginData")
    public Object[][] getData() throws Exception {

        String path = System.getProperty("user.dir")
                + "/src/test/resources/testdata/loginData.xlsx";

        log.info("Reading test data from Excel: " + path);

        return ExcelUtils.getExcelData(path, "Sheet1");
    }

    @Test(dataProvider = "loginData")
    public void loginTest(String username, String password, String type) {

        log.info("Running test for user: " + username);

        lp.login(username, password);

        // VALID LOGIN
        if (type.equalsIgnoreCase("valid")) {

            boolean isLoggedIn = driver.getCurrentUrl().contains("inventory");

            log.info("Current URL: " + driver.getCurrentUrl());

            Assert.assertTrue(isLoggedIn, "Valid login failed");

            log.info("✅ Valid login passed");
        }

        // INVALID LOGIN
        else {

            boolean isErrorDisplayed = lp.isErrorDisplayed();

            log.info("Error displayed: " + isErrorDisplayed);

            Assert.assertTrue(isErrorDisplayed, "Invalid login should fail");

            log.info("✅ Invalid login handled correctly");
        }
    }

    @AfterMethod
    public void end() {
        tearDown();
        log.info("Browser closed");
    }
}