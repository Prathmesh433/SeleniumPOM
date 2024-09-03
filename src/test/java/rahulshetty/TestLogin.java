package rahulshetty;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import objects.CartPage;
import objects.CheckoutPage;
import objects.HomePage;
import objects.LandingPage;

public class TestLogin {

//	ExtentReports extent = new ExtentReports();
//	ExtentSparkReporter spark = new ExtentSparkReporter("extentreport.html");
//	ExtentTest test = extent.createTest("Launch Browser and Website");
	public WebDriver driver;
	private LandingPage loginPage;
	private HomePage homePage;
	private CartPage cartPage;
	private CheckoutPage checkoutPage;

	@BeforeClass
	public void setUp() {

		// extent.attachReporter(spark);
		// test.log(Status.PASS, "User lunch website");
		// test.pass("User lunch website");

		ChromeOptions options = new ChromeOptions();
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get("https://www.saucedemo.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // 10 seconds

		loginPage = new LandingPage(driver);
		homePage = new HomePage(driver);
		cartPage = new CartPage(driver);
		checkoutPage = new CheckoutPage(driver);
	}

	@Test(dataProvider = "data")
	public void Addtocartfuncationlity(Map<String, String> data) {
		// test = extent.createTest("Login and Add to Cart");
		try {

			String username = data.get("username");
			String password = data.get("password");
			String firstName = data.get("firstName");
			String lastName = data.get("lastName");
			String postalCode = data.get("postalCode");

			// test.log(Status.INFO, "Logging in with username: " + username);
			loginPage.login(username, password);
			// test.pass("User logged in successfully");
			homePage.addItemToCart(0); // Add the first item
			// test.pass("First item added to cart");
			homePage.addItemToCart(1); // Add the second item
			// test.pass("Second item added to cart");
			homePage.clickaddtocart();
			cartPage.proceedToCheckout();
			// test.pass("Proceeded to checkout");
			checkoutPage.enterCheckoutInformation(firstName, lastName, postalCode);
			// test.pass("Entered checkout information");
			// Assertion for the thank you message
			String expectedMessage = "Thank you for your order!";
			String actualMessage = checkoutPage.getThankYouMessageText();
			Assert.assertEquals(actualMessage, expectedMessage, "The thank you message is incorrect");
			// test.pass("Order placed successfully, thank you message verified");
		} catch (AssertionError e) {
			// test.fail("Test failed due to an assertion error: " + e.getMessage());
			throw e;
		} catch (Exception e) {
			// test.fail("Test failed due to an unexpected error: " + e.getMessage());
			throw e;
		}

	}

	@DataProvider(name = "data")
	public Object[][] dataProvider() throws IOException, ParseException {

		JSONParser jsonparser = new JSONParser();
		FileReader reader = new FileReader(".\\jsonFIles\\TestData.json");

		// Object obj = jsonparser.parse(reader); //Convert json FIle --> Java Object
		// JSONObject empjsonobj=(JSONObject)obj; // COnvert Java Object --> Json Object

		JSONObject empjsonobj = (JSONObject) jsonparser.parse(reader); // Combine above 2 steps --> Converting json file
																		// --> to Json Object
		JSONObject loginData = (JSONObject) empjsonobj.get("login");
		JSONObject checkoutData = (JSONObject) empjsonobj.get("checkout");

		Map<String, String> dataMap = new HashMap<>();
		dataMap.put("username", (String) loginData.get("username"));
		dataMap.put("password", (String) loginData.get("password"));
		dataMap.put("firstName", (String) checkoutData.get("firstName"));
		dataMap.put("lastName", (String) checkoutData.get("lastName"));
		dataMap.put("postalCode", (String) checkoutData.get("postalCode"));

		return new Object[][] { { dataMap } };

//    	String firstName= (String) empjsonobj.get("firstName");
//    	String lastName = (String) empjsonobj.get("lastName");
//    	int postalCode = (int) empjsonobj.get("postalCode");
//    	 return new Object[][] {
//    		 {firstName,lastName, postalCode}
//              };

	}

	@AfterClass
	public void tearDown() {
		// test = extent.createTest("Teardown and Close Browser");
		// test.log(Status.INFO, "Closing the browser and cleaning up");
		if (driver != null) {
			driver.quit();
			// test.pass("Browser closed successfully");
		}
		// extent.flush();
	}

	// Screenshot capture
	
	public static String takeScreenshot(WebDriver driver, String testName) throws IOException {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/reports/" + testName + "_" + dateName + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}
}
