package login_Module;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class editUserdetails {
	WebDriver driver;
	public editUserdetails(WebDriver idriver) {
		driver=idriver;
		PageFactory.initElements(idriver, this);
	}
// Repositories
	@FindBy (linkText="My Account") WebElement myaccount;
	@FindBy (linkText="Edit") WebElement edit;
	@FindBy (name="firstname") WebElement editName;
	@FindBy (name="lastname")WebElement editlastname;
	@FindBy (linkText="Save")WebElement savebutton;
	
	//checkbox1 edit User email
	@FindBy (name="change_email")WebElement chgEmail;
	@FindBy (id="email")WebElement enterChgEmail;
	@FindBy (name="current_password")WebElement Currentpass;
	
	//checkbox2 edit User password
	@FindBy (name="change_password")WebElement chgPass;
	@FindBy (id="password")WebElement newpassword;
	@FindBy (id="password-confirmation")WebElement confirmpassword;
	
	public void clickonEditname(String editfname) {
		editName.clear();
		editName.sendKeys(editfname);
	}
	public void clickonEditlastname(String editlname) {
		editlastname.clear();
		editlastname.sendKeys(editlname);
	}
	public void clickonSavebutton() {
		savebutton.click();
	}
	public void clickonCheckbox1() {
		chgEmail.click();
	}
	public void changeEmailid(String editemail) {
		enterChgEmail.clear();
		enterChgEmail.sendKeys(editemail);
	}
	
	public void enterCurrentpwd(String cpass) {
		Currentpass.clear();
		Currentpass.sendKeys(cpass);
	}
	
	public void clickonCheckbox2() {
		chgPass.click();
	}
	public void enterNewpassword(String newpass) {
		newpassword.clear();
		newpassword.sendKeys(newpass);
	}
	public void enterConfirmpassword(String cpass) {
		confirmpassword.clear();
		confirmpassword.sendKeys(cpass);
	}
	}
	


