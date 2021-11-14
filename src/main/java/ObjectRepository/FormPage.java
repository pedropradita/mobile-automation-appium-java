package ObjectRepository;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class FormPage {
	
	public FormPage(AndroidDriver<AndroidElement> driver) {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@AndroidFindBy(id = "com.androidsample.generalstore:id/nameField")
	public WebElement nameField;
	@AndroidFindBy(xpath = "//*[@text='Female']")
	public WebElement femaleOption;
	@AndroidFindBy(id = "android:id/text1")
	public WebElement countrySelection;
	@AndroidFindBy(xpath="//*[@text='Brazil']")
	public WebElement country;
	@AndroidFindBy(id = "com.androidsample.generalstore:id/btnLetsShop")
	public WebElement letsShopButton;

}
