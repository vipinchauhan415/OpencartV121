package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

	//DataProviders 1
	@DataProvider(name="LoginData")
	public String [][] getData() throws IOException
	{
		String path=".\\testData\\Opencart_LoginData.xlsx";//taking xl file from testData
		
		ExcelUtility xlUtil=new ExcelUtility(path);//creating an object for XLutility
		
		int totalrows=xlUtil.getRowCount("Sheet1");
		int totalcols=xlUtil.getCellCount("Sheet1",1);
		
		String logindata[][]=new String [totalrows][totalcols];//created for two dimension array which can store
		
		for(int i=1;i<=totalrows;i++) //read the data from x1 storing in two dim array
		{
			for(int j=0;j<totalcols;j++)
			{
				logindata[i-1][j]=xlUtil.getCellData("Sheet1", i, j);//1,0
			}
		}
		return logindata;//returning two dime array
	}
	
	//DataProvider 2
}
