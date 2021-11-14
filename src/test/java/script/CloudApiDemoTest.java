package script;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ObjectRepository.HomePage;
import ObjectRepository.PreferenceDepenPage;
import ObjectRepository.PreferencePage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import resources.Base;

public class CloudApiDemoTest extends Base {

	public AndroidDriver<AndroidElement> driver;
	public static Logger log = LogManager.getLogger(Base.class.getName());
	HomePage h;
	PreferencePage p;
	PreferenceDepenPage pd;

	@BeforeMethod
	public void BefMet() throws IOException, InterruptedException {
		driver = RunCapabilities("apiDemo",true);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		h = new HomePage(driver);
		p = new PreferencePage(driver);
		pd =new PreferenceDepenPage(driver);
	}

	@AfterMethod
	public void AfMet() {
//		driver.closeApp();
	}
	
	@AfterClass
	public void AfCla() {
//		driver.pressKey(new KeyEvent(AndroidKey.HOME));
	}

	@Test(groups = "ApiDemo")
	public void WifiSetting() {
		h.preferenceMenu.click();
		p.preferenceDepenMenu.click();
		pd.checkBox.click();		
		pd.wifiSetButton.click();
		pd.wifiSetField.sendKeys("test");
		pd.button.get(1).click();
		log.info("Wifi setting success");
	}

	@Test(groups = "ApiDemo")
	public void Views() {
		h.viewMenu.click();
		log.info("Views menu success");
	}
}