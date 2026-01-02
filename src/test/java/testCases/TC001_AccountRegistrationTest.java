package testCases;


import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass{
	
	@Test(groups = {"Regression","Master"})
	public void verify_account_registration()
	{
		logger.info("*********Starting TC001_AccountRegistrationTest   *********");
		
		try
		{
			
		
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		logger.info("*** Clicked on Register Link *****");
		
		hp.clickregister();
		
		AccountRegistrationPage regpage=new AccountRegistrationPage(driver);
		
		logger.info("***** Providing Customer Details *******");
		regpage.setFirstName(randomeString().toUpperCase());
		regpage.setLastName(randomeString().toUpperCase());
		regpage.setEmail(randomeString()+"@gmail.com");
		//regpage.setTelephone("1278881818");
		regpage.setTelephone(randomeNumber());
		
		String password=randomAlphaNumeric();//store value in string, because calling method one time
		
		regpage.setPassword(password);
		regpage.setConfirmPassword(password);
		
		regpage.setPrivacyPolicy();
		regpage.ClickContinue();
		
		logger.info("***Validating expected message..*****");
		String confmsg=regpage.getConfirmationMsg();
		
		if (confmsg.equals("Your Account Has Been Created!")) 
		{
			Assert.assertTrue(true);
		}
		else
		{
			logger.error("Test Failed..");
			logger.debug("Debug logs..");
			Assert.assertTrue(false);
		}
		
		//Assert.assertEquals(confmsg, "Your Account Has Been Created!");
		
	}
		catch(Exception e)
		{
			//logger.error("Test Failed..");
			//logger.debug("Debug logs..");
			Assert.fail();
		}
		logger.info("***Finished TC001*****");
	}
		

}
