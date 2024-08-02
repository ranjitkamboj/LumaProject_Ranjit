package login_Module;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class ShippingDetails {
	WebDriver driver;
	Select sc;
	public ShippingDetails(WebDriver idriver) {
		driver=idriver;
		PageFactory.initElements(idriver, this);
	}

	public void details() throws InterruptedException {
	WebElement company = driver.findElement(By.name("company"));
	company.sendKeys("H&R");
	Thread.sleep(2000);

	WebElement Street1 = driver.findElement(By.name("street[0]"));
	Street1.sendKeys("41 Arleta Avenue");
	Thread.sleep(2000);
	WebElement Street2 = driver.findElement(By.name("street[1]"));
	Street2.sendKeys("Lanebrook Drive");
	Thread.sleep(2000);

	WebElement City = driver.findElement(By.name("city"));
	City.sendKeys("fairfield");
	Thread.sleep(2000);

	WebElement Zipcode = driver.findElement(By.name("postcode"));
	Zipcode.sendKeys("43010");
	Thread.sleep(2000);

	WebElement Country = driver.findElement(By.id("country_id"));
    sc = new Select(Country);
	sc.selectByVisibleText("United States");
	Thread.sleep(2000);

	WebElement State = driver.findElement(By.id("region_id"));
	sc = new Select(State);
	sc.selectByVisibleText("Ohio");
	Thread.sleep(2000);

	WebElement phone = driver.findElement(By.name("telephone"));
	phone.sendKeys("705-388-1100");
	Thread.sleep(2000);

	driver.findElement(By.xpath("//*[@id='checkout-shipping-method-load']/table/tbody/tr[2]/td[1]/input")).click();
	Thread.sleep(2000);

	driver.findElement(By.className("button action continue primary")).click();

}}
