package com.example.fw;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class AccountHelper extends WebDriverHelperBase {

	public AccountHelper(ApplicationManager manager) {
		super(manager);
	}

	public void signup(User user) {
//		openUrl("");
		gotoSignup();
		fillSignupForm(user);
		if (findErrMsg() != null) {
			throw new RuntimeException(findErrMsg().getText());
		}
		pause(25000);
		String msg = manager.getMailHelper().getNewMail(user.login, user.password);
		String confirmLink = getConfirmationLink(msg);
		openAbsoluteUrl(confirmLink);
		setPassword(user);
		pause(5000);
	}

	public void login(User user) {
		openUrl("");
		fillLoginForm(user);
	}

	public String loggedUser(User user) {
		return readLogin();
	}

/*----------------------------*/
	
	private void gotoSignup() {
		click(By.xpath(".//a[@href='signup_page.php']"));
	}

	private void fillSignupForm(User user) {
		type(By.name("username"), user.login);
		type(By.name("email"), user.email);
		click(By.cssSelector("input.button"));
	}

	private void setPassword(User user) {
		type(By.name("password"), user.password);
		type(By.name("password_confirm"), user.password);
		click(By.cssSelector("input.button"));
	}

	private void fillLoginForm(User user) {
		type(By.name("username"), user.login);
		type(By.name("password"), user.password);
		click(By.cssSelector("input.button"));
	}

	private String readLogin() {
		WebElement elem = findElement(By.cssSelector("td.login-info-left span"));
//		WebElement elem = findElement(By.xpath(".//td[@class='login-info-left']/span[1]"));
		return elem.getText();
	}

	private WebElement findErrMsg() {
//		return findElement(By.cssSelector("table.width50 tbody tr td p"));
		return findElement(By.xpath(".//*[contains(text(),'APPLICATION ERROR')]/following::p[1]"));
	}

	public String getConfirmationLink(String text) {
		Pattern regex = Pattern.compile("http\\S*");
		Matcher matcher = regex.matcher(text);
		if (matcher.find()) {
			return matcher.group();
		} else {
			return "";
		}
	}

}
