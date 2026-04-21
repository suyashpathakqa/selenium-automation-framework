package base;

import java.io.InputStream;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BaseTest {

    protected WebDriver driver;
    protected Properties config;

    // =========================
    // SETUP
    // =========================
    public void setup() {
        loadConfig();
        initDriver();

        String url = config.getProperty("baseUrl");
        if (url == null || url.isEmpty()) {
            throw new RuntimeException("❌ baseUrl is NULL in config.properties");
        }

        driver.get(url);
        driver.manage().window().maximize();
    }

    // =========================
    // LOAD CONFIG (CI SAFE)
    // =========================
    public void loadConfig() {
        try {
            config = new Properties();

            InputStream input = getClass()
                    .getClassLoader()
                    .getResourceAsStream("config.properties");

            if (input == null) {
                throw new RuntimeException("❌ config.properties NOT FOUND");
            }

            config.load(input);

            System.out.println("✅ Config loaded");
            System.out.println("🌐 URL: " + config.getProperty("baseUrl"));
            System.out.println("🧠 Browser: " + config.getProperty("browser"));

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("❌ Failed to load config");
        }
    }

    // =========================
    // INIT DRIVER (FINAL FIX)
    // =========================
    public void initDriver() {

        String browser = config.getProperty("browser", "chrome");

        // 🔥 IMPORTANT FIX: Maven override support
        boolean headless = Boolean.parseBoolean(
                System.getProperty("headless", config.getProperty("headless", "false"))
        );

        System.out.println("⚡ Headless: " + headless);

        if (browser.equalsIgnoreCase("chrome")) {

            ChromeOptions options = new ChromeOptions();

            // 🔥 Disable Chrome popups
            options.addArguments("--incognito");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-infobars");
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-save-password-bubble");
            options.addArguments("--disable-geolocation");
            options.addArguments("--disable-blink-features=AutomationControlled");

            // Disable password manager
            HashMap<String, Object> prefs = new HashMap<>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            options.setExperimentalOption("prefs", prefs);

            // 🔥 HEADLESS MODE (CI)
            if (headless) {
                options.addArguments("--headless=new");
                options.addArguments("--window-size=1920,1080");
                System.out.println("🚀 Running in HEADLESS mode");
            }

            driver = new ChromeDriver(options);

        } else {
            throw new RuntimeException("❌ Unsupported browser: " + browser);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    // =========================
    // TEARDOWN
    // =========================
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("🛑 Browser closed");
        }
    }
}