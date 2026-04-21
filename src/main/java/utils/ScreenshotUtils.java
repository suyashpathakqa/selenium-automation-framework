package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.openqa.selenium.*;

public class ScreenshotUtils {

    public static String captureScreenshot(WebDriver driver, String testName) {

        String path = System.getProperty("user.dir") + "/screenshots/" + testName + ".png";

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        try {
            Files.copy(src.toPath(), Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }
}