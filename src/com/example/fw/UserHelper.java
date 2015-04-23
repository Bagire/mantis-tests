package com.example.fw;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.example.utils.SortedListOf;


public class UserHelper extends WebDriverHelperBase {

	public UserHelper(ApplicationManager manager) {
		super(manager);
	}

	public SortedListOf<User> getUsersList() {
		SortedListOf<User> users = new SortedListOf<User>();
		List<WebElement> rows = getUserRows();
		for (WebElement row : rows) {
			User user = new User()
				.setLogin(getlogin(row))
				.setEmail(getEmail(row));
			users.add(user);
		}
		return users;
	}

	public int deleteUserWithIndex(SortedListOf<User> list, User user) {
		SortedListOf<User> copyList = new SortedListOf<User>(list);
		deleteUserBylogin(user.login);
		return copyList.indexOf(user);
	}

//----------------------------------------------------------------------
	
	public void deleteUserBylogin(String login) {
		click(By.xpath(".//table[3]//tr[count(td[@class='center'])=2]//a[text()='" + login + "']"));
		click(By.xpath(".//input[@value='Delete User']"));
		click(By.xpath(".//input[@value='Delete Account']"));
		pause(5000);
	}

	public void gotoUsersList() {
		click(By.xpath(".//span[1]/a"));
	}

	private List<WebElement> getUserRows() {
		return findElements(By.xpath(".//table[3]//tr[count(td[@class='center'])=2]"));
	}

	private String getlogin(WebElement row) {
		return row.findElement(By.xpath(".//a")).getText();
	}

	private String getEmail(WebElement row) {
		return row.findElement(By.xpath(".//td[3]")).getText();
	}

}
