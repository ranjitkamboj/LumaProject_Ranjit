package login_Module;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class testCase {

	WebDriver driver;
	// ......create the excel file path............
	String filepath = "D:\\lumaProject.xlsx";
	public FileInputStream fis;
	public XSSFWorkbook workbook;
	public Create_newAccount signup;
	public login signin;
	public logout signout;
	public editUserdetails edit;
	public ShippingDetails shipping;
	public JavascriptExecutor js;
	public Select sc;

	public testCase() throws IOException {
		fis = new FileInputStream(filepath);
		workbook = new XSSFWorkbook(fis);
	}

	@BeforeSuite
	public void launchBrowser() throws IOException, InterruptedException {
		this.driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://magento.softwaretestingboard.com/ ");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}

	@Test(priority = 1)
	public void VerifyURL() throws InterruptedException {
		String curURL = driver.getCurrentUrl();
		String acttitle = driver.getTitle();
		String exptitle = "home page";

		if (acttitle.length() < curURL.length() || acttitle.equals(exptitle))
			System.out.println("application launched successfully");
		else
			System.out.println("application  not launched successfully ");

		System.out.println("title of page" + driver.getTitle());
	}

	@Test(priority = 2)
	public void Verifylogoimage() throws InterruptedException {
		WebElement logo = driver.findElement(By.className("logo"));
		if (logo.isDisplayed()) {
			System.out.println("Logo image is Displayed");
			System.out.println("The Image text is " + logo.getAttribute("src"));
		} else {
			System.out.println("Logo image is not displayed & Test case 3 is failed");
			Thread.sleep(2000);
		}
	}

	@Test(priority = 3)
	public void VerifyCreateAcc() throws InterruptedException {

		WebElement Create_account = driver.findElement(By.linkText("Create an Account"));
		if (Create_account.isDisplayed() && Create_account.isEnabled()) {
			Create_account.click();
			System.out.println(" Test case 2 is passed");
		} else {
			System.out.println("Test case 2 is failed");
			Thread.sleep(2000);
		}
	}

	@Test(priority = 4)
	public void Verifysignin() {
		// identify element
		try {
			WebElement verifySigninLink = driver.findElement(By.linkText("Sign In"));
			// check visibility with isDisplayed()
			if (verifySigninLink.isDisplayed())
				System.out.println("Sigin link is visible");
		} catch (Exception n) {
			System.out.println("Signin link is invisible");
		}
	}

	@Test(priority = 5)
	public void VerifySupportlink() throws InterruptedException {
		WebElement Support = driver.findElement(By.xpath("//span[@class='not-logged-in']/child::a"));
		if (Support.isDisplayed() && Support.isEnabled()) {
			Support.click();
			driver.navigate().back();
			Thread.sleep(2000);
			System.out.println(" test case 5 is passed");
		} else {
			System.out.println("Test case 5 is failed");
			Thread.sleep(2000);
		}
	}

	@Test(priority = 6)
	public void VerifyloginFunctionality() throws InterruptedException {
		signin = new login(driver);
		signout = new logout(driver);
		signin.clickonSignin();
		signin.enterEmail("harinders.kamboj@gmail.com");
		signin.enterPassword("Ordnance1#");
		Thread.sleep(2000);
		signin.clickonsigninbtn();
		Thread.sleep(2000);
		signout.clickonWelcome();
		Thread.sleep(2000);
		signout.clicksignout();
	}

	@Test(priority = 7)
	public void VerifyForgotYourPwd() throws InterruptedException {
		signin = new login(driver);
		signin.clickonSignin();
		signin.clickonForgot();
		signin.resetPassword_enteremail("ryankamboj@gmail.com");
		Thread.sleep(2000);
		signin.clickonResetButton();
		signin.errorDisplayTxt();
		Thread.sleep(2000);
		driver.navigate().back();
	}

	@Test(priority = 8)
	public void CreateAccount() throws InterruptedException, IOException {
		signup = new Create_newAccount(driver);
		signout = new logout(driver);
		XSSFSheet sheet = workbook.getSheet("data");
		int rows = sheet.getLastRowNum();
		System.out.println("total number of rows: " + rows);

		for (int i = 1; i <= rows; i++) {
			XSSFRow row = sheet.getRow(i);
			XSSFCell fname = row.getCell(0);
			XSSFCell lname = row.getCell(1);
			XSSFCell email = row.getCell(2);
			XSSFCell pwd = row.getCell(3);
			XSSFCell cpass = row.getCell(4);
			XSSFCell result = row.createCell(5);
			try {
				signup.clickonCreateAccount();
				signup.enterFname(fname.toString());
				signup.enterLname(lname.toString());
				signup.enterUserEmail(email.toString());
				signup.enterUserpassword(pwd.toString());
				signup.enterConfirmationPwd(cpass.toString());

				System.out.println(".......UserCredentials..." + i + "..........");
				System.out.println("First name" + fname + "\t" + "lastname " + lname + "\t" + "Email" + email + "\n"
						+ "Password" + pwd + "\n" + "Confirm password" + cpass);
				signup.clickonCreateButton();
				Thread.sleep(2000);
				signout.clickonWelcome();
				Thread.sleep(2000);
				signout.clicksignout();
				Thread.sleep(2000);
				System.out.println("valid data");
				result.setCellValue("valid data");
				Thread.sleep(2000);
			} catch (Exception e) {
				System.out.println("Invalid data");
				result.setCellValue("invalid data");
			}
		}
		fis.close();
		FileOutputStream fos = new FileOutputStream(filepath);
		workbook.write(fos);
	}

	@Test(priority = 9)
	// To Validate login functionality by Valid and invalid Credentials...
	public void login() throws InterruptedException, IOException {
		signin = new login(driver);
		signout = new logout(driver);
		XSSFSheet sheet = workbook.getSheet("loginData");
		int rows = sheet.getLastRowNum();
		System.out.println("total number of rows: " + rows);

		for (int i = 1; i <= rows; i++) {
			XSSFRow row = sheet.getRow(i);
			XSSFCell email = row.getCell(0);
			XSSFCell pass = row.getCell(1);
			XSSFCell res = row.createCell(2);

			try {
				signin.clickonSignin();
				signin.enterEmail(email.toString());
				signin.enterPassword(pass.toString());
				signin.clickonsigninbtn();
				System.out.println(".......UserCredentials..." + i + "..........");
				System.out.println("Email ID:" + email + "\t" + "Password: " + pass);
				Thread.sleep(2000);
				signout.clickonWelcome();
				Thread.sleep(2000);
				signout.clicksignout();
				System.out.println("valid data");
				res.setCellValue("valid data");
				Thread.sleep(2000);
			} catch (Exception e) {
				System.out.println("Invalid data");
				res.setCellValue("invalid data");
			}
		}
		fis.close();
		FileOutputStream fos = new FileOutputStream(filepath);
		workbook.write(fos);
	}

	@Test(priority = 10)
	public void editContactInfo() throws InterruptedException, IOException {
		signin = new login(driver);
		signout = new logout(driver);
		edit = new editUserdetails(driver);

		XSSFSheet sheet = workbook.getSheet("editUser");
		int rows = sheet.getLastRowNum();
		System.out.println("total number of rows: " + rows);

		for (int i = 1; i <= rows; i++) {
			XSSFRow row = sheet.getRow(i);
			XSSFCell editfname = row.getCell(0);
			XSSFCell editlname = row.getCell(1);
			XSSFCell res = row.createCell(2);
			try {
				// sign in with Valid Credentials
				signin.clickonSignin();
				signin.enterEmail("Harinders.kamboj@gmail.com");
				signin.enterPassword("Ordnance1#");
				signin.clickonsigninbtn();
				signout.clickonWelcome();
				driver.findElement(By.linkText("My Account")).click();
				Thread.sleep(2000);
				driver.findElement(By.linkText("Edit")).click();
				Thread.sleep(2000);

				// Editing User's First name , Last name & save it...
				edit.clickonEditname(editfname.toString());
				edit.clickonEditlastname(editlname.toString());
				Thread.sleep(2000);
				edit.clickonSavebutton();
				Thread.sleep(2000);
				signout.clickonWelcome();
				Thread.sleep(2000);
				signout.clicksignout();
				Thread.sleep(2000);
				driver.navigate().back();

				System.out.println("User details are updated successfully");
				res.setCellValue("Updated details");
				Thread.sleep(2000);
			} catch (Exception e) {
				System.out.println("User details are not updated sucessfully");
				res.setCellValue("Not updated");
				driver.get("https://magento.softwaretestingboard.com/ ");
			}
		}
		fis.close();
		FileOutputStream fos = new FileOutputStream(filepath);
		workbook.write(fos);
	}

	@Test(priority = 11)
	public void editUserEmail() throws IOException {
		signin = new login(driver);
		signout = new logout(driver);
		edit = new editUserdetails(driver);

		XSSFSheet sheet = workbook.getSheet("editUseremail");
		int rows = sheet.getLastRowNum();
		System.out.println("total number of rows: " + rows);

		for (int i = 1; i <= rows; i++) {
			XSSFRow row = sheet.getRow(i);
			XSSFCell editemail = row.getCell(0);
			XSSFCell currentpwd = row.getCell(1);
			XSSFCell res = row.createCell(2);
			try {
				// sign in with Valid Credentials
				signin.clickonSignin();
				signin.enterEmail("Harinders.kamboj@gmail.com");
				signin.enterPassword("Ordnance1#");
				signin.clickonsigninbtn();
				signout.clickonWelcome();
				driver.findElement(By.linkText("My Account")).click();
				Thread.sleep(2000);
				driver.findElement(By.linkText("Edit")).click();
				Thread.sleep(2000);

				// select the checkbox1..
				edit.clickonCheckbox1();
				WebElement changeEmail = driver.findElement(By.id("change-email"));
				if (changeEmail.isEnabled()) {
					changeEmail.click();
				} else {
					System.out.println("change email is not enabled");
				}
				// edit the User email
				edit.changeEmailid(editemail.toString());
				edit.enterCurrentpwd(currentpwd.toString());
				Thread.sleep(2000);
				edit.clickonSavebutton();
				Thread.sleep(2000);
				driver.navigate().back();

				System.out.println("User details are updated successfully");
				res.setCellValue("Updated details");
				Thread.sleep(2000);
			} catch (Exception e) {
				System.out.println("User details are not updated sucessfully");
				res.setCellValue("Not updated");
				driver.get("https://magento.softwaretestingboard.com/ ");
			}
		}
		fis.close();
		FileOutputStream fos = new FileOutputStream(filepath);
		workbook.write(fos);
	}

	@Test(priority = 12)
	public void editUserpassword() throws IOException {
		signin = new login(driver);
		signout = new logout(driver);
		edit = new editUserdetails(driver);

		XSSFSheet sheet = workbook.getSheet("editUserPassword");
		int rows = sheet.getLastRowNum();
		System.out.println("total number of rows: " + rows);

		for (int i = 1; i <= rows; i++) {
			XSSFRow row = sheet.getRow(i);
			XSSFCell newpassword = row.getCell(0);
			XSSFCell confirmpassword = row.getCell(1);
			XSSFCell res = row.createCell(2);
			try {
				// sign in with Valid Credentials
				signin.clickonSignin();
				signin.enterEmail("Harinders.kamboj@gmail.com");
				signin.enterPassword("Ordnance1#");
				signin.clickonsigninbtn();
				signout.clickonWelcome();
				driver.findElement(By.linkText("My Account")).click();
				Thread.sleep(2000);
				driver.findElement(By.linkText("Edit")).click();
				Thread.sleep(2000);

				// select the checkbox1..
				edit.clickonCheckbox2();
				WebElement changepassword = driver.findElement(By.name("change_password"));
				if (changepassword.isEnabled()) {
					changepassword.click();
				} else {
					System.out.println("change password is not enabled");
				}
				// edit the User password
				edit.enterNewpassword(newpassword.toString());
				edit.enterConfirmpassword(confirmpassword.toString());
				Thread.sleep(2000);
				edit.clickonSavebutton();
				Thread.sleep(2000);
				driver.navigate().back();

				System.out.println("User details are updated successfully");
				res.setCellValue("Updated details");
				Thread.sleep(2000);
			} catch (Exception e) {
				System.out.println("User details are not updated sucessfully");
				res.setCellValue("Not updated");
				driver.get("https://magento.softwaretestingboard.com/ ");
			}
		}
		fis.close();
		FileOutputStream fos = new FileOutputStream(filepath);
		workbook.write(fos);
	}
	
	@Test (priority=13)
	public void testNavLinks() throws InterruptedException {
		String titles[] = { "What's New", "Women", "Men", "Gear", "Training", "Sale" };
		int elements = driver.findElements(By.xpath("//nav[@class='navigation']/ul/li")).size();
		for (int i = 0; i < elements; i++) {
			List<WebElement> navLinks = driver.findElements(By.xpath("//nav[@class='navigation']/ul/li"));
			WebElement link = navLinks.get(i);
			Thread.sleep(1000);
			link.click();
			Thread.sleep(1500);
			if (driver.getTitle().equals(titles[i])) {
				System.out.println("Apropriate Link. Test Case Pass");
			} else {
				System.out.println("Inapropriate Link. Test Case Fail");
			}
		}
	}

	@Test(priority = 14)
	public void VerifySearchbarisActive() {
		try {
			WebElement Searchbar = driver.findElement(By.xpath("//input[@id='search']"));
			// check visibility with isDisplayed()
			if (Searchbar.isDisplayed() && Searchbar.isEnabled())
				System.out.println("Search bar is visible" + Searchbar.getText());
		} catch (Exception n) {
			System.out.println("Search bar is invisible");
		}

	}

	@Test(priority = 15)
	public void VerifyAddtocartImage() throws InterruptedException {
		WebElement shoppingCart = driver.findElement(By.xpath("//div[@class='header content']/div[1]/a"));
		if (shoppingCart.isDisplayed() && shoppingCart.isEnabled()) {
			shoppingCart.click();
			System.out.println("Shopping cart image is Displayed");
			// Print the Empty Cart Message ...
			System.out.println("You have no items in your shopping cart.");
			System.out.println("The Image text is " + shoppingCart.getText());
		} else {
			System.out.println("Shopping Cart image is not displayed & Test case 14 is failed");
			Thread.sleep(2000);
		}

	}

	@Test(priority = 16)
	public void VerifySortDropdownitem() {
		// Locate dropdown element on web page by By.xpath.
		driver.get("https://magento.softwaretestingboard.com/men/tops-men.html");
		WebElement dropdown = driver.findElement(By.xpath("//*[@id='sorter']"));
		dropdown.click();
		Select select = new Select(dropdown);

		List<WebElement> getOptions = select.getOptions();
		List<String> list = new ArrayList<String>();
		for (WebElement element : getOptions) {
			list.add(element.getText());
		}
		System.out.println(list);

		// Verify the dropdown is enabled and visible.
		if (dropdown.isEnabled() && dropdown.isDisplayed()) {
			System.out.println("Dropdown is enabled and visible Testcase 15 is passed");
		} else {
			System.out.println("Dropdown is not visible Test case 15 is passed");
		}
		// Call getOptions() method to get all options of list. size() method is used to
		// get size of list.
		int listSize = select.getOptions().size();
		System.out.println("List size: " + listSize);

	}

	@Test(priority = 17)
	// To Add item to cart
	public void addtocartItem() throws InterruptedException {

		// To test Men dropdown is working properly
		// hover on men dropdown and navigate to Tanks section
		Actions act = new Actions(driver);
		WebElement menDrpdwn = driver
				.findElement(By.xpath("//div[@class='page-wrapper']/descendant::ul[3]/child::li[3]"));
		act.moveToElement(menDrpdwn).perform();
		Thread.sleep(1000);

		WebElement tops = driver.findElement(
				By.xpath("//div[@class='page-wrapper']/descendant::ul[3]/child::li[3]/child::ul/child::li[1]"));
		act.moveToElement(tops).perform();
		Thread.sleep(1000);

		WebElement tanks = driver.findElement(By.xpath(
				"//div[@class='page-wrapper']/descendant::ul[3]/child::li[3]/child::ul/child::li[1]/child::ul/child::li[4]"));
		act.moveToElement(tanks).perform();
		tanks.click();
		Thread.sleep(1000);

		WebElement product = driver
				.findElement(By.xpath("//*[@id=\"maincontent\"]/div[3]/div[1]/div[3]/ol/li[3]/div/a/span/span/img"));
		product.click();

		WebElement productsize = driver
				.findElement(By.xpath("//div[@class='product-info-main']//form/descendant::div[5]//div[2]"));
		productsize.click();

		WebElement productcolor = driver
				.findElement(By.xpath("//div[@class='product-info-main']//form/descendant::div[3]/div[2]/div[1]/div[2]"));
		productcolor.click();

		WebElement quantity = driver.findElement(By.name("qty"));

		WebElement addtoCartbutton = driver.findElement(By.xpath("//div[@class='fieldset']//descendant::button[1]"));
		String num[] = { "0", "100001", "3" };
		try {
			for (int i = 0; i < 3; i++) {
				quantity.clear();
				quantity.sendKeys(num[i]);
				Thread.sleep(1000);
				addtoCartbutton.click();

				WebElement Errormsgqty = driver.findElement(By.xpath("//input[@name='qty']/following-sibling::div"));
				if (Errormsgqty.isDisplayed()) {
					System.out.println("Test case 16 add item to cart failed.." + Errormsgqty.getText());
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		WebElement additemMsg = driver.findElement(By.xpath("//div[@class='page messages']//descendant::div[5]"));
		if (additemMsg.isDisplayed()) {
			System.out.println(" Test case 16 add item is passed..." + additemMsg.getText());
		}

	}

	@Test(priority = 18)
	// .....update cart....
	public void updateAddtoCart() throws InterruptedException {
		// view the cart by simple click
		WebElement cart = driver.findElement(By.xpath("//div[@class='header content']/div[1]/a"));
		cart.click();
		Thread.sleep(2000);
		// ..verify Proceed to by navigation
		WebElement proceedtobuy = driver.findElement(By.xpath("//body/descendant::div[10]/descendant::div[7]//button"));
		proceedtobuy.click();
		driver.navigate().back();
		// ..update quantity of items
		System.out.println("Total amount before updating the Quantity "
				+ driver.findElement(By.xpath("//div[@class='subtotal']//descendant::span[4]")).getText());
		WebElement updatequantity = driver.findElement(By.xpath("//body//descendant::div[19]/ol/li[1]//input"));

		js = ((JavascriptExecutor) driver);
		js.executeScript("argument[0].value=' ';", updatequantity);
		updatequantity.sendKeys("4");
		WebElement updatebtn = driver.findElement(By.xpath("//body//descendant::div[19]/ol/li[1]//button"));
		updatebtn.click();
		Thread.sleep(2000);

		System.out.println("Total amount after updating the Quantity "
				+ driver.findElement(By.xpath("//div[@class='subtotal']//descendant::span[4]")).getText());
	}

	@Test(priority = 19)
	public void DeleteItemfromCart() throws InterruptedException {
		// view the cart by simple click
		WebElement cart = driver.findElement(By.xpath("//div[@class='header content']/div[1]/a"));
		cart.click();
		Thread.sleep(2000);
		// delete an item from cart
		System.out.println("before deleting an item "
				+ driver.findElement(By.xpath("//span[@class='counter qty']/span[1]")).getText());
		WebElement deletefromCart = driver
				.findElement(By.xpath("//body//descendant::div[19]/ol/li[1]//descendant::div[10]/a"));
		deletefromCart.click();
		Thread.sleep(1000);
		// ..accepting the alert

		WebElement acceptalert = driver.findElement(By.xpath("//body//descendant::footer[2]/button[2]"));
		js = ((JavascriptExecutor) driver);
		js.executeScript("argument[0].click();", acceptalert);
		Thread.sleep(2000);
		System.out.println("After deleting an Item"
				+ driver.findElement(By.xpath("//span[@class='counter qty']/span[1]")).getText());
	}
	
	@Test(priority = 20)
	// To test that item can be added to wishlist or Shared via email
	public void addWishlist1() throws InterruptedException {
		// to Add item in wishlist
		WebElement wishlist = driver.findElement(By.xpath("//div[@class='column main']/div[1]//descendant::a[3]"));
		wishlist.click();
		if (driver.getTitle().equals("My Wish List")) {
			System.out.println("Added to wishlist. TestCase Pass");
		}
		// Share Wishlist
		WebElement shareButton = driver.findElement(By.name("save_and_share"));
		shareButton.click();

		// Enter emails in two different ways
		WebElement emailsTextArea = driver.findElement(By.name("emails"));
		try {
			String email1[] = { "kaurranjit1012@gmail.com", "ranjit5552@gmail.com", "ryankamboj123@gmail.com" };
			String email2[] = { "kauranjit1012@gmail.com,", "ranjit5552@gmail.com,",
					"ryankamboj123@gmail.com" };
			List<String[]> allemails = new ArrayList<>();
			allemails.add(email1);
			allemails.add(email2);
			int j = 0;
			while (j < 2) {
				emailsTextArea.clear();
				emailsTextArea.sendKeys(allemails.get(j));
				Thread.sleep(1000);

				// Write a message while sharing
				WebElement message = driver.findElement(By.id("message"));
				message.sendKeys("This the Tee I have added to wishlist!!");
				WebElement shareBtn1 = driver.findElement(By.xpath("//div[@class='column main']//button"));
				shareBtn1.click();
				Thread.sleep(1000);

				// Asks to enter valid email if not added as instructed or,
				// Displays a message if item is already shared
				try {
					WebElement emailErrormsg = driver
							.findElement(By.xpath("//fieldset[@class='fieldset']/descendant::div[3]"));
					WebElement moreErrors = driver.findElement(By.xpath("//main[@id='maincontent']/descendant::div[6]"));
					if (emailErrormsg.isDisplayed()) {
						String errorTxt = emailErrormsg.getText();
						System.out.println("Test Case Fail. " + errorTxt);
					} else if (moreErrors.isDisplayed()) {
						String errorTxt = moreErrors.getText();
						System.out.println("Test Case Fail. " + errorTxt);
					}
				} catch (NoSuchElementException e) {
					System.out.println(e);
				}
				j++;
			}
			// Displays a message if Shared successfully
			try {
				WebElement shredmsg = driver.findElement(By.xpath("//div[@class='page messages']//descendant::div[5]"));
				if (shredmsg.isDisplayed()) {
					String successmsg = shredmsg.getText();
					System.out.println("Test Case pass. " + successmsg);
					driver.navigate().back();
					driver.navigate().back();
				}
			} catch (NoSuchElementException e) {
				System.out.println(e);
			}
		} catch (StaleElementReferenceException e) {
			System.out.println(e);
		}
	}
	

	@Test(priority = 21)
	public void Testeditbutton() throws InterruptedException {
		// view the cart by simple click
		WebElement cart = driver.findElement(By.xpath("//div[@class='header content']/div[1]/a"));
		cart.click();
		Thread.sleep(2000);
		// ..click on edit button...
		WebElement editbtn = driver.findElement(By.xpath("//body//descendant::div[19]/ol/li[2]//descendant::div[9]/a"));
		editbtn.click();

		// ...change color and size ,click on Update cart..
		WebElement size = driver
				.findElement(By.xpath("//div[@class='product-info-main']//form/descendant::div[5]//div[2]"));
		size.click();
		WebElement color = driver.findElement(
				By.xpath("//div[@class='product-info-main']//form/descendant::div[3]/div[2]/div[1]/div[2]"));
		color.click();
		WebElement updateCartbutton = driver.findElement(By.xpath("//fieldset[@class='fieldset']//button"));
		updateCartbutton.click();
	}

	@Test(priority = 22)
	public void VerifyViewandEditlink() throws InterruptedException {
		// view the cart by simple click
		WebElement cart = driver.findElement(By.xpath("//div[@class='header content']/div[1]/a"));
		cart.click();
		Thread.sleep(2000);
		// ...verify the navigation of View and Edit link
		WebElement viewAndedit = driver.findElement(By.linkText("View and Edit Cart"));
		js = ((JavascriptExecutor) driver);
		js.executeScript("argument[0].click();", viewAndedit);
		Thread.sleep(2000);
		driver.get("https://magento.softwaretestingboard.com/checkout/cart/");
		WebElement checkout = driver.findElement(By.linkText("Proceed to Checkout"));
		checkout.click();
	}

	@Test(priority = 23)
	public void VerifyShippingpage() throws InterruptedException {
		// adding shipping details after click on proceed to checkout..
		shipping = new ShippingDetails(driver);
		driver.get("https://magento.softwaretestingboard.com/checkout/#shipping");
		shipping.details();
	}

	@Test(priority = 24)

	public void reviewANDpayment() {
		shipping = new ShippingDetails(driver);

		// To check Review progress bar
		WebElement Reviewtick = driver
				.findElement(By.xpath("//li[@class=\"opc-progress-bar-item _active\"]//child::span"));
		if (Reviewtick.isDisplayed()) {
			Reviewtick.getText();
			System.out.println("Review and payment progress bar is visible");
		} else {
			System.out.println("Review and payment progress bar is not visible");
		}
		// verify the Review address checkbox
		WebElement chkbox = driver.findElement(By.id("billing-address-same-as-shipping-checkmo"));
		if (chkbox.isDisplayed() && chkbox.isEnabled()) {
			System.out.println("checkbox is displayed and enabled");
		} else {
			System.out.println("checkbox isnot  displayed and enabled");
		}

		// check if the checkbox is selected :
		// use getAttribute method to check the checked attribute
		if (chkbox.isSelected()) {
			System.out.println("checkbox is autochecked" + chkbox.getAttribute("checked"));
		} else {
			try {
				shipping.details();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Test(priority = 25)
	public void applyCoupen() throws InterruptedException {
		// Apply Discount code
		WebElement Discount = driver.findElement(By.xpath("//div[@class='cart-discount']/div/div[1]"));
		Discount.click();
		Thread.sleep(1000);
		WebElement code = driver.findElement(By.xpath("//div[@class='cart-discount']/div//form//descendant::input[2]"));
		code.sendKeys("LUMA30FF");
		Thread.sleep(1000);
		WebElement applyBtn = driver
				.findElement(By.xpath("//div[@class='cart-discount']/div//form//descendant::button"));
		applyBtn.click();
		Thread.sleep(1000);
		WebElement codeMsg = driver.findElement(By.xpath("//div[@class='page messages']//descendant::div[5]"));
		if (codeMsg.isDisplayed()) {
			System.out.println(codeMsg.getText());
		}

	}

	@Test(priority = 26)
	public void placeorder() {
		WebElement placeOrder = driver.findElement(
				By.xpath("//div[@class='payment-group']/descendant::div[4]/descendant::div[44]/button"));
		if (placeOrder.isEnabled() && placeOrder.isDisplayed()) {
			placeOrder.click();
			System.out.println("Order is placed successfully");
		} else {
			System.out.println("Order is not placed successfully ");
		}
		// Thank you for shopping Verify Order confirmation number
		WebElement OrderNumber = driver.findElement(By.xpath("//div[@class='checkout-success']//child::a"));
		if (OrderNumber != null) {
			System.out.println("Thank you for your purchase");
		} else {
			System.out.println("Try again Please");
		}
		// click on continue shopping button
		WebElement Continue = driver.findElement(By.linkText("Continue Shopping"));
		Continue.click();
	}

	@AfterSuite
	public void closeBrowser() {
		driver.close();
	}
}
