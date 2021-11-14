
package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class Base {

	public static AppiumDriverLocalService service;
	public static AndroidDriver<AndroidElement> driver;
	public Properties prop;

	public AppiumDriverLocalService startServer() {
		boolean flag = checkIfServerIsRunnning(4723);
		if (!flag) {
			service = AppiumDriverLocalService.buildDefaultService();
			service.start();
		}
		return service;
	}

	public static boolean checkIfServerIsRunnning(int port) {
		boolean isServerRunning = false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.close();
		} catch (IOException e) {
			isServerRunning = true;
		} finally {
			serverSocket = null;
		}
		return isServerRunning;
	}

	public static void StartEmulator() throws IOException, InterruptedException {
		Runtime.getRuntime().exec(System.getProperty("user.dir") + "\\src\\main\\java\\resources\\StartEmulator.bat");
		Thread.sleep(6000);
	}
	
	public static void EndEmulator() throws IOException, InterruptedException {
		Runtime.getRuntime().exec(System.getProperty("user.dir") + "\\src\\main\\java\\resources\\EndEmulator.bat");
		Thread.sleep(6000);
	}

	public AndroidDriver<AndroidElement> RunCapabilities(String appName, Boolean cloud)
			throws IOException, InterruptedException {
		if (cloud) {
			return CloudCapabilities(appName);
		}else
		{
			return Capabilities(appName);
		}
	}

	public static AndroidDriver<AndroidElement> Capabilities(String appName) throws IOException, InterruptedException {
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\resources\\global.properties");
		Properties prop = new Properties();
		prop.load(fis);
		File appDir = new File("src");
		File app = new File(appDir, (String) prop.get(appName));

		DesiredCapabilities cap = new DesiredCapabilities();
// 		String device = (String) prop.get("device");
		String device = System.getProperty("device");
		if (device.contains("Pixel_2_API_30_x64")) {
			StartEmulator();
		}
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, device);
		cap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UIAutomator2");
		cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 14);
		driver = new AndroidDriver<>(new URL("http://0.0.0.0:4723/wd/hub"), cap);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}

	public static AndroidDriver<AndroidElement> CloudCapabilities(String appName)
			throws IOException, InterruptedException {
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\resources\\global.properties");
		Properties prop = new Properties();
		prop.load(fis);
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability("browserstack.user", "pedropradita_EvuCe7");
		cap.setCapability("browserstack.key", "secret");
		if (appName.equalsIgnoreCase("apiDemo")) {
			cap.setCapability("app", "bs://d84e9619f981931a9dd1ab8c645d9d145dfdcfc0");
		}else
		{
			cap.setCapability("app", "bs://c364390a2e0c4d7f76601982d120e1325d416baf");
		}
		cap.setCapability("device", "Google Pixel 4");
		cap.setCapability("os_version", "11.0");
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UIAutomator2");
		cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 14);
		driver = new AndroidDriver<>(new URL("http://hub.browserstack.com/wd/hub"), cap);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}

	public String getScreenShotPath(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destinationFile = System.getProperty("user.dir") + "/reports/" + testCaseName + ".png";
		FileUtils.copyFile(source, new File(destinationFile));
		return destinationFile;
	}
}
