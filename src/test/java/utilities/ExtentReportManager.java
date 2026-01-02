package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener {
	public ExtentSparkReporter spartReporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	String repName;
	
	public void onStart(ITestContext testContext)
	{
		String timeStamp=new SimpleDateFormat("yyyy.MM.dd.HH.MM.ss").format(new Date());
		repName="Test-Report-" + timeStamp + ".html";
		new File(System.getProperty("user.dir") + "\\reports").mkdirs();
		spartReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "\\reports\\" + repName);

		//spartReporter=new ExtentSparkReporter(".\\reports\\"+ repName);//specify location of the report
		
		spartReporter.config().setDocumentTitle("opecart automation report");//title of report
		spartReporter.config().setReportName("Opencart functional testing"); //name of the report
		spartReporter.config().setTheme(Theme.DARK);
		
		
		extent=new ExtentReports();
		extent.attachReporter(spartReporter);
		extent.setSystemInfo("Application", "Opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
		
		String os=testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);
		
		String browser=testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);
		
		List<String> includedGroups=testContext.getCurrentXmlTest().getIncludedGroups();
		if(!includedGroups.isEmpty())
		{
			extent.setSystemInfo("Groups", includedGroups.toString());
		}		
		
	}
	public void onTestSuccess(ITestResult result)
	{
		test=extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.PASS, result.getName() + " got successfully executed");
	}
	
	public void onTestFailure(ITestResult result)
	{
		test=extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		
		test.log(Status.FAIL, result.getName() + " got failed");
		test.log(Status.INFO, result.getThrowable().getMessage());
		
		try {
			String imgPath=new BaseClass().captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);
		} catch(Exception e1){
			e1.printStackTrace();
		}
		
		
	}
	
	public void onTestSkipped(ITestResult result)
	{
		test=extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		
		test.log(Status.SKIP, result.getName() + " got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}
	
	public void onFinish(ITestContext testContext)
	{
		extent.flush();
		
		String pathofExtentReport=System.getProperty("user.dir")+"\\reports\\"+repName;
		File extentReport= new File(pathofExtentReport);
		try {
			//Desktop.getDesktop().browse(extentReport.toURI());
			if (extentReport.exists()) {
	            Desktop.getDesktop().browse(extentReport.toURI());
	        } else {
	            System.out.println("Extent report not found at: " + pathofExtentReport);
	        }

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			URL url=new URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);
			
			/*
			//create the email message
			
			ImageHtmlEmail email=new ImageHtmlEmail();
			email.setDataSourceResolver(new DataSourceUrlResolver(url));
			email.setHostName("smtp.email.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("vipinchauhan415@yopmail.com","password"));
			email.setSSLOnConnect(true);
			email.setFrom("vipinchauhan415@yopmail.com");//sender
			email.setSubject("Test reports");
			email.setMsg("Please find attached report...");
			email.addTo("vipinchauhan415@yopmail.com");//receiver
			email.attach(url,"extent report","please check report");
			email.send();//send the email
			*/
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
