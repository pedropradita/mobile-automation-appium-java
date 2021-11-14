package ObjectRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import resources.Base;

public class Global {
	AndroidDriver<AndroidElement>  driver;
	public static Logger log = LogManager.getLogger(Base.class.getName());

	public Global(AndroidDriver<AndroidElement> driver)
	{
		this.driver=driver;
	}

	public void scrollToText(String text)
	{
	     driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\""+text+"\"));");
	}
	
	public double CheckAmount(double actual,  double expected ) {
	if (actual == expected) {
		log.info("Amount price is right");
	} else {
		log.info("Amount price is different");
	}
	return actual;
	}
	
}
