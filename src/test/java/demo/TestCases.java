package demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.data.Score;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor jse;
    Wrappers wrappers;


    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wrappers = new Wrappers(driver, wait);

        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();

    }


    @Test
    public void testCase01() {
        wrappers.openWebPage();
        wrappers.clickOnItem("Hockey Teams: Forms, Searching and Pagination");

        wrappers.goToNextPage();

        List<Score> allScores = new ArrayList<>();

        for (int i = 1; i < 4; i++) {
            List<WebElement> tableRows = wrappers.getTableRowByClassName("team");

            for (WebElement row : tableRows) {

                double winPercentage = Double.parseDouble(wrappers.getColumnTextByClassName(row, "pct"));

                if (winPercentage < 0.40) {
                    Score s = new Score(System.currentTimeMillis(), wrappers.getColumnTextByClassName(row, "name"), wrappers.getColumnTextByClassName(row, "year"), winPercentage);

                    allScores.add(s);
                }


            }
            wrappers.goToNextPage();
        }

        wrappers.writeToFile("hockey-team-data.json", allScores);
    }

    @Test
    public void testCase02() {
        wrappers.openWebPage();
        wrappers.clickOnItem("Oscar Winning Films");
        List<WebElement> yearAnchors = wrappers.getYearItems();

        List<Object> out = new ArrayList<>();


        for (WebElement yearAnchor : yearAnchors) {
            yearAnchor.click();

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loading")));


            List<WebElement> tableRows = wrappers.getTableRowByClassName("film");

            for (int i = 0; i < 5; i++) {
                WebElement row = tableRows.get(i);

                Map<String, Object> flim = new HashMap<>();

                flim.put("epoch_time", System.currentTimeMillis());
                flim.put("year", yearAnchor.getText());
                flim.put("title", wrappers.getColumnTextByClassName(row, "film-title"));
                flim.put("nomination", Integer.parseInt(wrappers.getColumnTextByClassName(row, "film-nominations")));
                flim.put("awards", Integer.parseInt(wrappers.getColumnTextByClassName(row, "film-awards")));
                WebElement bestPicEle = row.findElement(By.className("film-best-picture"));
                boolean hasChildren = !bestPicEle.findElements(By.xpath("./*")).isEmpty();
                flim.put("isWinner", hasChildren);

                out.add(flim);


//                System.out.println("===========================================================");
//
//                System.out.println(wrappers.getColumnTextByClassName(row, "film-title"));
//                System.out.println(wrappers.getColumnTextByClassName(row, "film-nominations"));
//                System.out.println(wrappers.getColumnTextByClassName(row, "film-awards"));
//                System.out.println(wrappers.getColumnTextByClassName(row, "film-best-picture"));
//
//                System.out.println("===========================================================");

            }


        }

        wrappers.writeToFile("oscar-winner-data.json", out);

    }


}