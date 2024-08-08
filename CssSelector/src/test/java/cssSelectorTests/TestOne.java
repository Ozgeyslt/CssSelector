package cssSelectorTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class TestOne {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp(){

        System.setProperty("webdriver.chrome.driver",
                System.getProperty("user.dir") + "\\src\\main\\java\\web\\test\\drivers\\chromedriver.exe");

        ChromeOptions options=new ChromeOptions();

        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--no-sandbox"); //
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--start-maximized");

        driver=new ChromeDriver(options);

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        wait=new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void fillInTheBlank(WebElement element, String value){
        new Actions(driver).click(element).sendKeys(value).perform();
    }

    public void clickButton(WebElement element){
        new Actions(driver).click(element).perform();
    }

    public void scrollThePage(int number1, int number2){
        new Actions(driver).scrollByAmount(0,200).perform();
    }



    @Test(priority = 1)
    public void clickButton(){
        driver.get("https://demoqa.com/elements");

        //Butona tıklanır
        WebElement buttons=driver.findElement(By.cssSelector("#item-4"));
        clickButton(buttons);

        scrollThePage(0,300);

        //Buttonların görünürlüğü kontrol edilir
        WebElement waitButtons=wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.cssSelector("div[class='mt-4']:nth-child(4)>[type='button']")));
        waitButtons.isDisplayed();

        //ClickMe butonuna tıklanir
        WebElement clickMe=driver.findElement
                (By.cssSelector("div[class='mt-4']:nth-child(4)>[type='button']"));
        clickButton(clickMe);

        //ClickMe butonunun, dinamik mesajinin doğrulamasi yapilir
        WebElement result=wait.until
                (ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p#dynamicClickMessage")));
        result.isDisplayed();
        Assert.assertEquals(result.getText(), "You have done a dynamic click");

    }


    @Test (priority = 2)
    public void addRecord() throws InterruptedException {
        driver.get("https://demoqa.com/webtables");

        //WebTables sayfasinda add butonu seçilir
        WebElement add=driver.findElement(By.cssSelector("#addNewRecordButton"));
        clickButton(add);

        scrollThePage(0,300);

        //Form sayfasinda elementlerin css Locaterlari bulunur
        WebElement firstName=driver.findElement(By.cssSelector("input#firstName"));
        WebElement lastName=driver.findElement(By.cssSelector("input#lastName"));
        WebElement email=driver.findElement(By.cssSelector("div [class=\"col-md-9 col-sm-12\"]>[placeholder=\"name@example.com\"]"));
        WebElement age=driver.findElement(By.cssSelector("input[id=\"age\"][placeholder=\"Age\"]"));
        WebElement salary=driver.findElement(By.cssSelector("div[class=\"col-md-9 col-sm-12\"]>[id=\"salary\"]"));
        WebElement department=driver.findElement(By.cssSelector("#department"));
        WebElement submit=driver.findElement(By.cssSelector("#submit"));

        //FillInTheBlank metodu ile veriler girilir
        fillInTheBlank(firstName, "ayse");
        fillInTheBlank(lastName, "yilmaz");
        fillInTheBlank(email, "xxxx@xxx.com");
        fillInTheBlank(age, "25");
        fillInTheBlank(salary, "30000");
        fillInTheBlank(department, "IT");
        clickButton(submit);

        scrollThePage(0,300);

        //Form verilerini güncellemek için Edit butonu seçilir
        WebElement editButton=wait.until(ExpectedConditions.elementToBeClickable
                (By.cssSelector("div[class=\"action-buttons\"]>span[id=\"edit-record-4\"]")));
        clickButton(editButton);

        //Formda First Name alani güncellenir
        WebElement edittedFirstName=wait.until(ExpectedConditions.elementToBeClickable
                (By.cssSelector("form[id=\"userForm\"]>div input[id=\"firstName\"][placeholder=\"First Name\"]")));
        edittedFirstName.clear();
        fillInTheBlank(edittedFirstName, "Zeynep");

        //Form alani güncellenip submit edilir
        WebElement edittedSubmitButton=wait.until(ExpectedConditions.elementToBeClickable
                (By.cssSelector("form[id=\"userForm\"]>div button[id=\"submit\"][type=\"submit\"]")));
        clickButton(edittedSubmitButton);

        //Assertion ile guncel first Name alaninin dogrulamasi yapilir
        WebElement edittedRow=wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.cssSelector("div[class=\"rt-tr-group\"]:nth-child(4) div[class=\"rt-td\"]:nth-child(1)")));
        Assert.assertEquals(edittedRow.getText(), "Zeynep");
    }

    @AfterTest
    public void tearDown(){
        if (driver!=null){
            driver.quit();
        }
    }

}
