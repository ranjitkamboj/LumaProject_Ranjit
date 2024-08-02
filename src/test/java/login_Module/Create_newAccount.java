package login_Module;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Create_newAccount {
	WebDriver driver;
	public Create_newAccount(WebDriver idriver) {
		driver=idriver;
		PageFactory.initElements(idriver, this);
	}
// Repositories
	@FindBy(linkText="Create an Account") WebElement CreateAccLink;
	@FindBy(name="firstname") WebElement firstname;
	@FindBy (name="lastname")WebElement lastname;
	@FindBy (name="email")WebElement Useremail;
	@FindBy (id="password")WebElement Userpwd;
	@FindBy (name="password_confirmation") WebElement ConfirmPwd;

	@FindBy (linkText="Create an Account") WebElement createAccButton;
	
	public void clickonCreateAccount() {
		CreateAccLink.click();
	}
	public void enterFname(String first) {
		firstname.clear();
		firstname.sendKeys(first);
	}
	public void enterLname(String last) {
		lastname.clear();
		lastname.sendKeys(last);
	}
	
	public void enterUserEmail(String email) {
		Useremail.clear();
		Useremail.sendKeys(email);
	}
	public void enterUserpassword(String password) {
		Userpwd.clear();
		Userpwd.sendKeys(password);
	}
	public void enterConfirmationPwd(String Confirmpassword) {
		ConfirmPwd.clear();
		ConfirmPwd.sendKeys(Confirmpassword);
	}
	
	public void clickonCreateButton() {
		createAccButton.click();
		driver.get("https://magento.softwaretestingboard.com/customer/account/");
	}
}
