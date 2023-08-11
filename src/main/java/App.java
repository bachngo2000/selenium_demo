import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.net.http.WebSocket;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class App {
    public static void main(String[] args) throws Exception {
        // instantiate a webdriver object of type ChromeDriver
        WebDriver driver = new ChromeDriver();
        // define where the chrome driver sits on our hard drive
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        // set implicit wait
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        // instruct the driver to open the wikipedia website
        driver.get("https://www.wikipedia.org/");

        //locators
        Long start = System.currentTimeMillis();
        //id
        driver.findElement(By.id("js-link-box-en"));
        Long end = System.currentTimeMillis();

        System.out.println("The time needed to get a locator by ID is: " + (end - start));

        start = System.currentTimeMillis();
        //xpath
        driver.findElement(By.xpath("//*[@id=\"js-link-box-en\"]"));
        end = System.currentTimeMillis();
        System.out.println("The time needed to get a locator by xpath is: " + (end - start));

        start = System.currentTimeMillis();
        //cssSelector
        driver.findElement(By.cssSelector("#js-link-box-en"));
        end = System.currentTimeMillis();
        System.out.println("The time needed to get a locator by cssSelector is: " + (end - start));

        //retrieving text from a webpage
        WebElement titleOfWebPage = driver.findElement(By.cssSelector("#www-wikipedia-org > div.central-textlogo > h1 > span"));

        String title = titleOfWebPage.getText();

        String expectedText = "Wikipedia";

        if (title.equals(expectedText)) {
            System.out.println("Test has passed!");
        }
        else {
            System.out.println("Test did not pass!");
            driver.close();
            throw new Exception();
        }

        // clicking on a webpage
        WebElement englishButton =  driver.findElement(By.id("js-link-box-en"));
        englishButton.click();
        String expectedTitle = "Welcome to Wikipedia,";
        WebElement titleOfEnglishPage = driver.findElement(By.id("mp-welcome"));
        if (titleOfEnglishPage.getText().equals(expectedTitle)) {
            System.out.println("Test has passed! Wikipedia English has been opened");
        }
        else {
            System.out.println("Test did not pass! Wikipedia English was not opened");
            driver.close();
            throw new Exception();
        }

        //send text to a webpage
        WebElement searchBar = driver.findElement(By.id("searchInput"));
        String searchStr = "Stanford University";
        searchBar.sendKeys(searchStr);

        WebElement searchButton = driver.findElement(By.cssSelector("#search-form > fieldset > button"));
        searchButton.click();

        driver.close();

        //Working with tables
        driver.get("file:///C:/Users/bachn/Downloads/tablePage.html");
        WebElement firstColumnVal = driver.findElement(By.xpath("/html/body/table/tbody[1]/tr[2]/td[1]"));
        String firstColumnStr = firstColumnVal.getText();
//        System.out.println(firstColumnStr);
//        System.out.println(driver.findElement(By.xpath("/html/body/table/tbody[1]/tr[1]/th[2]")).getText());

        List<WebElement> listOfWebElements = driver.findElements(By.xpath("/html/body/table/tbody[1]/tr"));
        for (WebElement element: listOfWebElements) {
            System.out.println(element.getText());
        }

        //Explicit wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("js-link-box-en"))));

        //Fluent wait
        Wait fluentWait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);

        fluentWait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.id("js-link-box-en"));
            }
        });

        driver.close();
        // Using the JavaScript Executor
        driver.get("https://www.coursera.org/specializations/programming-python-java");

        WebElement signUpButton = driver.findElement(By.cssSelector("#c-ph-right-nav > ul > li.c-ph-right-nav-button.c-ph-sign-up.isLohpRebrand.c-ph-right-nav-no-border > a"));

        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].click();", signUpButton);

        //Clicking the sign-up button with JavaScript
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", signUpButton);
        // filling in signup information
        WebElement fullName = driver.findElement(By.cssSelector("#name"));
        String fillInFullName = "Bach Nguyen-Ngo";
        fullName.sendKeys(fillInFullName);

        WebElement email = driver.findElement(By.cssSelector("#email"));
        email.sendKeys("bachngo2000@gmail.com");

        WebElement password = driver.findElement(By.cssSelector("#password"));
        password.sendKeys("Hahahoho@2023");

        //Setting up timeouts
        ((JavascriptExecutor)driver).executeAsyncScript("window.setTimeout(arguments[arguments.length-1], 2000);");
//        driver.close();

        //Changing the webpage from Coursera to Stanford's
        ((JavascriptExecutor)driver).executeScript("window.location = 'https://www.stanford.edu/'");

        //Scroll the webpage
        ((JavascriptExecutor)driver).executeScript("window.scrollBy(0,1500)");

        //Alerts in Selenium
        driver.get("file:///C:/Users/bachn/Downloads/seleniumDemo%20(2)/alerts.html");
        // Getting all the alert buttons
        WebElement displayAlertButton = driver.findElement(By.cssSelector("body > button:nth-child(2)"));
        WebElement confirmationAlertButton = driver.findElement(By.cssSelector("body > button:nth-child(5)"));
        WebElement promptAlertButton = driver.findElement(By.cssSelector("body > button:nth-child(8)"));

        //click the display alert button
        displayAlertButton.click();
        // before returning the active alert on the screen, wait for 15 seconds until an alert is present
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        webDriverWait.until(ExpectedConditions.alertIsPresent());
        // return the active alert on the screen
        Alert displayAlert = driver.switchTo().alert();

        try {
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        //dismiss or accept the alert
        displayAlert.accept();

        //click the confirmation alert button
        confirmationAlertButton.click();
        wait.until(ExpectedConditions.alertIsPresent());
        Alert confirmationAlert = driver.switchTo().alert();

        try {
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        confirmationAlert.dismiss();

        //click the prompt alert button
        promptAlertButton.click();
        wait.until(ExpectedConditions.alertIsPresent());
        Alert promptAlert = driver.switchTo().alert();
        System.out.println(promptAlert.getText());
        promptAlert.sendKeys("Ambrose");
        try {
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        promptAlert.accept();

        //Working with iframes
        driver.get("https://www.w3schools.com/html/html_iframe.asp");

        String pageTitle = driver.switchTo().frame(0).findElement(By.cssSelector("#main > h1")).getText();
        System.out.println(pageTitle);

        WebElement iframe = driver.findElement(By.cssSelector("#main > div:nth-child(7) > iframe"));

        driver.switchTo().frame(iframe).findElement(By.cssSelector("#topnav > div > div.w3-bar.w3-left > a:nth-child(5)")).click();

        //Handle drop down select in Selenium WebDriver
        driver.get("https://www.w3schools.com/tags/tryit.asp?filename=tryhtml_select");
        //since the dropdown is located in the iframe, we have to switch to the iframe first before selecting the dropdown
        driver.switchTo().frame(driver.findElement(By.id("iframeResult")));
        //select the dropdown
        WebElement selectable = driver.findElement(By.id("cars"));
        //created a select object
        Select select = new Select(selectable);
        select.selectByIndex(1);


        //Performing special actions
        // drag and drop
        driver.get("https://jqueryui.com/droppable/");
        //the draggable and droppable divs sit in the iframe, so we need to switch to the iframe whenever to want to target
        // a specific element within this iframe
        driver.switchTo().frame(driver.findElement(By.cssSelector("#content > iframe")));
        // identify web elements
        WebElement draggable = driver.findElement(By.id("draggable"));
        WebElement droppable = driver.findElement(By.id("droppable"));
        //define actions
        Actions dragAndDrop = new Actions(driver);
        dragAndDrop.dragAndDrop(draggable, droppable).build().perform();

        //right click
        Actions contextClick = new Actions(driver);
        contextClick.contextClick().build().perform();

        //
        Actions actionObj = new Actions(driver);
        actionObj.keyDown(Keys.F1);

        driver.close();

    }
}
