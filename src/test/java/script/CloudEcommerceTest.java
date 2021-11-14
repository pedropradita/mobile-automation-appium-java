package script;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ObjectRepository.CartPage;
import ObjectRepository.CheckoutPage;
import ObjectRepository.FormPage;
import ObjectRepository.Global;
import ObjectRepository.ProductPage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import resources.Base;

public class CloudEcommerceTest extends Base {

	public AndroidDriver<AndroidElement> driver;
	public static Logger log = LogManager.getLogger(Base.class.getName());
	Global g;
	FormPage f;
	CheckoutPage co;
	ProductPage p;
	CartPage c;

	@BeforeMethod
	public void BefMet() throws IOException, InterruptedException {
		driver = RunCapabilities("generalStore", true);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		g = new Global(driver);
		f = new FormPage(driver);
		co = new CheckoutPage(driver);
		p = new ProductPage(driver);
		c = new CartPage(driver);
	}

	@AfterMethod
	public void AfMet() {
//		driver.closeApp();
	}

	@AfterClass
	public void AfCla() throws IOException, InterruptedException {
//		driver.pressKey(new KeyEvent(AndroidKey.HOME));
	}

	@Test (groups = "Ecommerce")
	public void TotalValidation() throws IOException, InterruptedException {

		f.nameField.sendKeys("mobile automation");
		driver.hideKeyboard();
		f.femaleOption.click();
		f.countrySelection.click();
		g.scrollToText("Brazil");
		f.country.click();
		f.letsShopButton.click();

		p.listAddToCartButton.get(0).click();
		p.listAddToCartButton.get(0).click();
		p.cartButton.click();
		Thread.sleep(4000);

		int count = c.priceList.size();
		double sum = 0;

		for (int i = 0; i < count; i++) {
			String amount1 = co.productPriceList.get(i).getText();
			double amount = GetAmount(amount1);
			sum = sum + amount;
		}

		log.info(sum + " Sum of products");
		String total = co.totalAmount.getText();
		total = total.substring(1);
		double totalValue = Double.parseDouble(total);
		log.info(totalValue + " Total value of products");

		Assert.assertEquals(g.CheckAmount(sum, totalValue), totalValue);
	}

	public static double GetAmount(String value) {
		value = value.substring(1);
		double amount2value = Double.parseDouble(value);
		return amount2value;
	}
}
