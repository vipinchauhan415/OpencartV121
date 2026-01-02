package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.myAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass{

	@Test(dataProvider = "LoginData",dataProviderClass = DataProviders.class, groups = "Datadriven")//getting data providers from different class
	public void verify_loginDDT( String email, String pwd, String exp) throws InterruptedException
	{
		logger.info("**********STARTING TC_003_LoginDDT******");
		
		try {
		//Home page
				HomePage hp=new HomePage(driver);
				hp.clickMyAccount();
				hp.clickLogin();
				
				//Login page
				LoginPage lp=new LoginPage(driver);
				lp.setEmail(email);
				lp.setPassword(pwd);
				lp.clickLogin();
				
				//My account page
				myAccountPage macc=new myAccountPage(driver);
				boolean targetPage= macc.isMyAccountPageExists();
				
				/*Data is valid = login success-- test pass -logout
				 * 				= Login failed - test fail	
				 * 
				 * Data is Invalid = login success-- test fail -logout
				 * 				= Login failed - test pass
				 */
				
				if(exp.equalsIgnoreCase("Valid"))
				{
					if(targetPage==true)
					{
						Assert.assertTrue(true);
						macc.Logout();
					}
					else 
					{
						Assert.assertTrue(false);
					}
				}
				if(exp.equalsIgnoreCase("Invalid"))
				{
					if(targetPage==true)
					{
						macc.Logout();
						Assert.assertTrue(false);
						
					}
					else 
					{
						Assert.assertTrue(true);
					}
				}
		}
		catch (Exception e) {
			Assert.fail();
		}
		Thread.sleep(3000);
				logger.info("**********FINISHED TC_003_LoginDDT******");
	}
}
