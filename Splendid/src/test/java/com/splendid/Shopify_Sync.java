package com.splendid;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.javafaker.Faker;
import com.github.javafaker.Number;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Shopify_Sync {

	public static void main(String[] args) throws InterruptedException, IllegalAccessException, URISyntaxException {

		//WebDriverManager.chromedriver().setup();
		
		//WebDriverManager.firefoxdriver().setup();
		
		WebDriverManager.edgedriver().setup();
		
		//WebDriver driver = new ChromeDriver();
		
		//WebDriver driver = new FirefoxDriver();
		
		WebDriver driver = new EdgeDriver();
		
		driver.manage().window().maximize();
		
		Faker faker = new Faker();
		
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
		
		// Arraylist for windows handle

		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		//Splendid Admin URL
		
		String admin_base_url = "https://dev-admin.fromaround.com/#";
		
		//Shopify Signup URL
		
		String shopify_signup_url = "https://admin.shopify.com/";
		
		//Temp Mail URL
		
		driver.get("https://generator.email/");

		WebElement mailtext = driver.findElement(By.xpath("//span[@id='email_ch_text']"));

		String copiedmailname = mailtext.getText();
		
		js.executeScript("window.open()");
		
		ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
		
		driver.switchTo().window(tabs.get(1));
		
		driver.get(shopify_signup_url);
		
		driver.findElement(By.xpath("//a[contains(text(),'Get started')]")).click();
		
		driver.findElement(By.xpath("(//div[@class='account-picker__action-icon'])[1]")).click();
		
		String faker_email_address = faker.internet().emailAddress();
		
		driver.findElement(By.id("account_email")).sendKeys(faker_email_address);
		
		String first_name = faker.name().firstName();
		
		driver.findElement(By.id("account_first_name")).sendKeys(first_name);
		
		String last_name = faker.name().lastName();
		
		driver.findElement(By.id("account_last_name")).sendKeys(last_name);
		
		driver.findElement(By.id("account_password")).sendKeys("/*Lend@123*/");
		
		driver.findElement(By.id("password-confirmation")).sendKeys("/*Lend@123*/");
		
		if (isFrameAvailable(driver, "0psvwzkjvj6")) {
			
			driver.switchTo().frame("0psvwzkjvj6");
			
			driver.findElement(By.xpath("//div[@id='checkbox']")).click();
			
			driver.switchTo().defaultContent();
			
		}
		
		WebDriverWait button_wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		WebElement signup_button = button_wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='commit']")));
		
		js.executeScript("arguments[0].click()", signup_button);
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Create store')]"))).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Skip All')]"))).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[@id='navigation-next-button'])[2]"))).click();
		
        WebElement shopify_logo = driver.findElement(By.xpath("(//img[@class='_ShopifyWordmark_1yrt4_52'])[1]"));
		
		wait.until(ExpectedConditions.elementToBeClickable((shopify_logo)));
		
		shopify_logo.click();
		
		driver.navigate().refresh();
		
		String shopify_admin_store_login_url = driver.getCurrentUrl();
		
		String store_domain_name = shopify_admin_store_login_url.split("/store/")[1];
		
		System.out.println(store_domain_name);
		
		driver.navigate().to(shopify_admin_store_login_url + "/products");
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Import')]"))).click();
		
		URL resource = Shopify_Sync.class.getClassLoader().getResource("Testing Files/products_export.csv");
		
		if (resource == null) {
			
			throw new IllegalAccessException("File not found!.......");
			
		}
		
		String import_file = Paths.get(resource.toURI()).toFile().getAbsolutePath();
		
		WebElement add_file_element = driver.findElement(By.xpath("//input[@type='file']"));
		
		wait.until(ExpectedConditions.elementToBeClickable(add_file_element));
		
		add_file_element.sendKeys(import_file);
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Upload and preview')]"))).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Import products')]"))).click();
		
		// Move to third tab
		
		js.executeScript("window.open()");
		
		tabs = new ArrayList<>(driver.getWindowHandles());
		
		driver.switchTo().window(tabs.get(2));
		
		//Admin URL

		driver.get(admin_base_url + "/login");
		
		//Login Email
		
		driver.findElement(By.id("email")).sendKeys("superadmin@splendid.com");
		
		//Login Password
		
		driver.findElement(By.id("password")).sendKeys("12345678");
		
		//Submit Button
		
		driver.findElement(By.xpath("//button[contains(text(), 'Submit')]")).click();
		
		//Navigation to Brand page
		
		wait.until(ExpectedConditions.urlContains(admin_base_url + "/country"));
		
		driver.navigate().to(admin_base_url+"/brands");
		
		//Add Brand Button
		
		driver.findElement(By.xpath("//button[contains(text(), 'Add Brands')]")).click();
		
		String random_name = faker.company().name();
		
		//Brand Name
		
		driver.findElement(By.id("brand_name")).sendKeys(random_name);
		
		//Brand Domain Name
		
		driver.findElement(By.id("brand_domain")).sendKeys(store_domain_name);
		
		//Business Email
		
		driver.findElement(By.id("business_email")).sendKeys(copiedmailname);
		
		//Business Mobile number
		
		String fake_mobile_number = faker.numerify("#######");
		
		driver.findElement(By.id("business_mobile_number")).sendKeys(fake_mobile_number);
		
		//Default Mark Down
		
		driver.findElement(By.id("default_mark_down")).sendKeys("10");
		
		//Business Website URL
		
		String faker_url = faker.internet().url();
		
		driver.findElement(By.id("website_url")).sendKeys(faker_url);
		
		//Shopify URL
		
		driver.findElement(By.id("shopify_url")).sendKeys(shopify_admin_store_login_url);
		
		//Brand Platform Type
		
		WebElement brand_type = driver.findElement(By.id("brand_platform_type"));
		
		Select brand_select_type = new Select(brand_type);
		
		brand_select_type.selectByVisibleText("Shopify");
		
		//Plugin Status
		
		WebElement plugin_status = driver.findElement(By.id("plugin"));
		
		Select plugin_select_type = new Select(plugin_status);
		
		plugin_select_type.selectByVisibleText("Yes");
		
		//Address Line1
		
		String faker_line1 = faker.address().streetAddress();
		
		driver.findElement(By.id("line_1")).sendKeys(faker_line1);
		
		//Address Line2
		
		String faker_line2 = faker.address().streetAddress();
		
		driver.findElement(By.id("line_2")).sendKeys(faker_line2);
		
		//Country
		
		driver.findElement(By.id("country_id")).sendKeys("India");
		
		WebElement country_element = driver.findElement(By.xpath("//div[@class=' css-d7l1ni-option']"));
		
		wait.until(ExpectedConditions.visibilityOf(country_element));
		
		country_element.click();
		
		//State
		
		driver.findElement(By.id("state_id")).sendKeys("Tamil Nadu");
		
		WebElement state_element = driver.findElement(By.xpath("//div[@class=' css-d7l1ni-option']"));
		
		wait.until(ExpectedConditions.visibilityOf(state_element));
		
		state_element.click();
		
		//City
		
		driver.findElement(By.id("town_city")).sendKeys("Chennai");
		
		WebElement city_element = driver.findElement(By.xpath("//div[@class=' css-d7l1ni-option']"));
		
		wait.until(ExpectedConditions.visibilityOf(city_element));
		
		city_element.click();
		
		//Pincode
		
		String fake_pincode_number = faker.bothify("######");
				
		driver.findElement(By.id("postcode")).sendKeys(fake_pincode_number);
		
		//Confirm Button
		
		driver.findElement(By.xpath("//button[contains(text(),'Confirm Address')]")).click();
		
		//Create Button
		
		driver.findElement(By.xpath("//button[contains(text(),'Create')]")).click();
		
		//GST Number
		
		WebElement gst_number_field = driver.findElement(By.id("gst_number"));
		
		wait.until(ExpectedConditions.visibilityOf(gst_number_field));

		String fake_gst_number = faker.bothify("##?????####?#?#");
		
		gst_number_field.sendKeys(fake_gst_number);
		
		//PAN Number
		
		String fake_pan_number = faker.bothify("?????####?");
		
		driver.findElement(By.id("pan_number")).sendKeys(fake_pan_number);
		
		//Bank Account Number
		
		String fake_bank_number = faker.bothify("################");
		
		driver.findElement(By.id("account_number")).sendKeys(fake_bank_number);
		
		//IFSC Code
		
		String fake_isfc_number = faker.bothify("????#######");
		
		driver.findElement(By.id("bank_ifsc")).sendKeys(fake_isfc_number);
		
		//Bank Name
		
		String fake_bank_name = faker.bothify("??? bank");
				
		driver.findElement(By.id("bank_name")).sendKeys(fake_bank_name);
				
		//Account Name
				
		String fake_account_name = faker.name().firstName();
				
		driver.findElement(By.id("account_name")).sendKeys(fake_account_name);
		
		//Create Button
		
		driver.findElement(By.xpath("//button[contains(text(),'Create')]")).click();
		
		
		
		
	}

	private static boolean isFrameAvailable(WebDriver driver, String frameId) {
		
        try {
            driver.switchTo().frame(frameId);
            
            driver.switchTo().defaultContent();
            
            return true;
            
        } catch (Exception e) {
        	
            return false;
            
        }
    }
}
