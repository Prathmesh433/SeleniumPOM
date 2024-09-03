//package resources;
//
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.reporter.ExtentSparkReporter;
//
//public class ExtentRepoterNG {
//
//    public static ExtentReports getReportObject() {
//	{
//		String path=System.getProperty("user.dir")+"/reports/index.html";
//		ExtentSparkReporter spark = new ExtentSparkReporter(path);
//		spark.config().setReportName("Web Automation Report");
//		spark.config().setDocumentTitle("Test Results");
//		
//		ExtentReports extent = new ExtentReports();
//		extent.attachReporter(spark);
//		extent.setSystemInfo("Tester", "Prathmesh Nayak");
//		return extent;
//				
//		}
//    }
//}
