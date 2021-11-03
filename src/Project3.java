import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.testng.Assert.*;
import java.lang.*;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class Project3 {


    public static void main(String[] args) throws InterruptedException {


        System.setProperty("webdriver.chrome.driver", "C:\\Users\\vlada\\Downloads\\Selenium\\Drivers\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.carfax.com/");
        //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver,10);

        //driver.findElement(By.cssSelector("[href='/cars-for-sale'")).click();
        driver.findElement(By.xpath("//a[.='Find a Used Car']")).click();
        assertTrue(driver.getPageSource().contains("Used Cars"));
        Select chosenMake = new Select(driver.findElement(By.name("make")));
        chosenMake.selectByValue("Tesla");
        Select models = new Select(driver.findElement(By.name("model")));

        List <WebElement> options = models.getOptions();
        List <String> chosenOptions = new ArrayList<>(Arrays.asList("Model 3", "Model S", "Model X", "Model Y"));
        int count = 0;
        for (int i = 0; i < options.size(); i++) {

            for (int j = 0; j < chosenOptions.size(); j++) {
                if(options.get(i).getText().contains(chosenOptions.get(j))) {
                    count++;

                }
            }

        }

        assertEquals(count, 4);

        models.selectByValue("Model S");

        driver.findElement(By.name("zip")).sendKeys("22182");
        driver.findElement(By.id("make-model-form-submit")).click();
        assertTrue(driver.getPageSource().contains("Step 2 - Show me cars with"));
        driver.findElement(By.className("noAccident")).click();
        driver.findElement(By.className("oneOwner")).click();
        driver.findElement(By.className("personal")).click();
        driver.findElement(By.className("service")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement noOfCars = driver.findElement(By.xpath("//button[@type='button']//span[@class='totalRecordsText']"));
        String str = noOfCars.getText();
        int expectedNoOfCars = Integer.parseInt(str);
        driver.findElement(By.className("button")).click();

        List <WebElement> listings = driver.findElements(By.xpath("//div[@class='srp-list-container  srp-list-container--srp']//article"));

        int actualNoOfCars = listings.size();
        assertEquals(actualNoOfCars, expectedNoOfCars);

        for(WebElement listing: listings) {

            assertTrue(listing.getText().contains("Tesla Model S"));

        }

        List <WebElement> listings1 = driver.findElements(By.xpath("//div[@class='srp-list-container  srp-list-container--srp']//span[@class='srp-list-item-price']"));
        List <String> list = new ArrayList<>();

        for (WebElement listing: listings1) {


                list.add(listing.getText().replaceAll("[Price: $,]", ""));

        }

            System.out.println(list);
         Select sortBy = new Select(driver.findElement(By.xpath("//select[@aria-label='SelectSort']")));
         sortBy.selectByVisibleText("Price - High to Low");

         Thread.sleep(2000);

//        List <WebElement> listings2 = driver.findElements(By.xpath("//div[@class='srp-list-container  srp-list-container--srp']//span[@class='srp-list-item-price']"));
//         List <String> list2 = new ArrayList<>();
//
//        for (WebElement listing: listings2) {
//
//
//            list2.add(listing.getText().replaceAll("[Price: $,]", ""));
//
//        }
//         Collections.sort(list);
//         System.out.println(list);
//         System.out.println(list2);

        sortBy.selectByVisibleText("Mileage - Low to High");

        Thread.sleep(2000);

        sortBy.selectByVisibleText("Year - New to Old");

        driver.quit();

    }

    }
