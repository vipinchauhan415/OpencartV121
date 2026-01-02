package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.myAccountPage;
import testBase.BaseClass;

public class TC002_Login_Test extends BaseClass{

	@Test(groups = {"Sanity","Master"})
	public void verify_login()
	{
		logger.info("****STARTING TC002_Login_Test******");
		
		try 
		{
		//Home page
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		//Login page
		LoginPage lp=new LoginPage(driver);
		lp.setEmail(p.getProperty("email"));
		lp.setPassword(p.getProperty("password"));
		lp.clickLogin();
		
		//My account page
		myAccountPage macc=new myAccountPage(driver);
		boolean targetPage= macc.isMyAccountPageExists();
		
		//Assert.assertEquals(targetPage, true,"Login Failed");
		Assert.assertTrue(targetPage);
		}
		catch (Exception e) {
			Assert.fail();
		}
		
		logger.info("****FINISHED TC002_Login_Test******");
	
	}
	
}
