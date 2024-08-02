package login_Module;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class logout {
	WebDriver driver;
	public logout(WebDriver idriver) {
		driver=idriver;
		PageFactory.initElements(idriver, this);
	}
//Repositories
 @FindBy(xpath="//div[@class='panel header']/ul/child::li[2]/span/button") WebElement WelcomeUserName;
 @FindBy(linkText="Sign Out") WebElement signout;
 
 public void clickonWelcome() {
	 WelcomeUserName.click();
 }
 public void clicksignout() {
	 signout.click();
 }
}

