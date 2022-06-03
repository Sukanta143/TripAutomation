import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.google.common.io.Files;

public class tripAutomation {
	static WebDriver driver;
	public WebDriver invokeBrowser() {
		int num = 0;
		System.out.println("Enter Number\n1. Chrome\n2. Firefox");
		System.out.println("Enter your choice: ");
		Scanner sc = new Scanner(System.in);
		if (sc.hasNextLine()) {
			num = sc.nextInt();
		}
		switch (num) {
		case 1:
			// Chrome browser
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\Drivers\\chromedriver.exe");
			driver = new ChromeDriver();
			break;
		case 2:
			// FirefoxDriver
			System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"\\Drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
			break;
		default:
			System.out.println("Invalid Choice");
			System.exit(0);
			break;
		}
		sc.close();
		return driver;
	}

	@SuppressWarnings({ "deprecation" })
	public void verifyPageTitle(WebDriver driver) throws IOException {
		// Maximizing the current window
		driver.manage().window().maximize();

		// Navigating to Yatra.com
		driver.get("https://www.yatra.com/");

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		// Navigating to home page of the application and clicking “Offers” link.

		driver.findElement(By.linkText("Offers")).click();

		// Validating the page by checking the title "Domestic Flights Offers | Deals on Domestic Flight Booking | Yatra.com".

		String pageTitle = driver.getTitle();
		if (pageTitle.equals("Domestic Flights Offers | Deals on Domestic Flight Booking | Yatra.com")) {
			System.out.println("Offers page title is true");
		} else {
			System.out.println("Offers page title is false");
		}

		// Checking the banner text "Great Offers & Amazing Deals".

		WebElement banner = driver
				.findElement(By.xpath("//*[@id=\"collapsibleSection\"]/section[1]/div[1]/div/div/h2"));

		String bannerText = banner.getText();
		if (bannerText.equals("Great Offers & Amazing Deals")) {
			System.out.println("Banner text is true");
		} else {
			System.out.println("Banner text is false");
		}

		// Capturing the screen shot of the browser window.
		TakesScreenshot capture = (TakesScreenshot) driver;
		File srcFile = capture.getScreenshotAs(OutputType.FILE);
		File destFile = new File(System.getProperty("user.dir")+ "\\screenshots\\ss.png");
		Files.copy(srcFile, destFile);

		// Navigating to “Holidays” and Listing first five holiday packages with its
		// price.

		driver.findElement(By.linkText("Holidays")).click();
		List<WebElement> holidayPackages = driver
				.findElements(By.xpath("//*[@id=\"collapsibleSection\"]/section[1]/div[2]/div/section/div/div/ul/li"));
		System.out.println("Number of packages available is " + holidayPackages.size());
		System.out.println("Five holiday packages are: ");
		for (int i = 0; i < 5; i++) {
			String name = holidayPackages.get(i).getText();
			System.out.println(name);
		}

		// Closing all browsers.

		driver.quit();
	}

	public static void main(String[] args) throws IOException {
		tripAutomation tA = new tripAutomation();
		WebDriver driver=tA.invokeBrowser();
		tA.verifyPageTitle(driver);
	}

}
