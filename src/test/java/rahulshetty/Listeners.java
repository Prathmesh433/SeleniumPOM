package rahulshetty;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class Listeners implements ITestListener {
	public ExtentSparkReporter spark;
	public ExtentReports extent;
	public ExtentTest test;
	public ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>(); 

	// ExtentReports extent = ExtentRepoterNG.getReportObject();

	public void onStart(ITestContext context) {
		String path = System.getProperty("user.dir") + "/reports/index.html";
		spark = new ExtentSparkReporter(path);
		spark.config().setReportName("Web Automation Report");
		spark.config().setDocumentTitle("Test Results");

		extent = new ExtentReports();
		extent.attachReporter(spark);
		extent.setSystemInfo("Tester", "Prathmesh Nayak");

	}

	public void onTestStart(ITestResult result) {
		test = extent.createTest(result.getMethod().getMethodName());
	}

	public void onTestSuccess(ITestResult result) {
		test = extent.createTest(result.getName());
		test.log(Status.PASS, "Test Case PASSED is:" + result.getName());
	}

	public void onTestFailure(ITestResult result) {
		extentTest.get().fail(result.getThrowable());

//		test = extent.createTest(result.getName());
//		test.log(Status.FAIL, "Test case FAILED is:" + result.getName());
//		test.log(Status.FAIL, "Test case FAILED cause is: " + result.getThrowable());

		// Take a screenshot on failure

		WebDriver driver = null;
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (driver != null) {
			try {
				String filepath = TestLogin.takeScreenshot(driver, result.getMethod().getMethodName());
				test.addScreenCaptureFromPath(filepath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getName());
		test.log(Status.SKIP, "Test case SKIPPED is " + result.getName());
	}

	public void onFinish(ITestContext context) {
		extent.flush();
	}

}
