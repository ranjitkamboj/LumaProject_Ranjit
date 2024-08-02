package login_Module;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class login {
	WebDriver driver;
	public login(WebDriver idriver) {
		driver=idriver;
		PageFactory.initElements(idriver, this);
	}
// Repositories
	@FindBy(linkText="Sign In") WebElement signin;
	@FindBy (id="email")WebElement email;
	@FindBy (id="pass")WebElement pwd;
	@FindBy (id="send2")WebElement submit;
	@FindBy (linkText="Forgot Your Password?") WebElement forgot;
	@FindBy (id="email_address") WebElement reEnteremail;

    @FindBy (xpath="//div[@class='primary']/child::button/child::span") WebElement resetpass;
	@FindBy (xpath="//div[@class='messages']/child::div/child::div") WebElement Errormsg;
	
	public void clickonSignin() {
		signin.click();
	}
	public void enterEmail(String userID) {
		email.clear();
		email.sendKeys(userID);
	}
	public void enterPassword(String pass) {
		pwd.clear();
		pwd.sendKeys(pass);
	}
	
	public void clickonsigninbtn() {
		submit.click();
	}
	public void clickonForgot() {
		forgot.click();
		driver.get("https://magento.softwaretestingboard.com/customer/account/forgotpassword/");
	}
	public void resetPassword_enteremail(String resetPwd) {
		reEnteremail.clear();
		reEnteremail.sendKeys(resetPwd);
	}
	public void clickonResetButton() {
		resetpass.click();
	}
	public void errorDisplayTxt() {
	Errormsg.getText();				
	}
}
	
	
	
	
	
	
	
	
	
	
	
	

