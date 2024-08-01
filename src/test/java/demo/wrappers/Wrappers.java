package demo.wrappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.List;

public class Wrappers {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor jse;
    ObjectMapper mapper;


    public Wrappers(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.jse = (JavascriptExecutor) driver;
        mapper = new ObjectMapper();

    }

    public void openWebPage() {
        driver.get("https://www.scrapethissite.com/pages/");
    }

    public void clickOnItem(String title) {

        WebElement selectedItem = wait.until(
                ExpectedConditions.
                        presenceOfElementLocated(
                                By.xpath("//*[contains(text(),'"+title+"')]")));
        selectedItem.click();



    }
    public void goToNextPage() {
        WebElement nextBtn = wait.until(
                ExpectedConditions.
                        presenceOfElementLocated(
                                By.xpath("//a[@aria-label='Next']")));
        nextBtn.click();



    }

    public List<WebElement> getTableRowByClassName(String className){
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//tr[@class='"+className+"']")));
    }

    public String getColumnTextByClassName(WebElement row, String className){
        return row.findElement(By.className(className)).getText();
    }

    public List<WebElement> getYearItems(){
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("year-link")));
    }

    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception ignored) {

        }

    }

    public void writeToFile(String fileName , Object o){
        try {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), o);
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
