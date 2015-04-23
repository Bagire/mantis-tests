package com.example.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import com.example.fw.AccountHelper;
import com.example.fw.User;
import com.example.fw.UserHelper;
import com.example.utils.SortedListOf;

public class DeleteUserTests extends TestBase {

	public User user = new User()
	.setLogin("testuser1").setPassword("123456")
	.setEmail("testuser1@localhost");
	public User admin = new User()
	.setLogin("administrator").setPassword("root");
	private AccountHelper accHelper;
	private UserHelper userHelper;

	@Test
	public void deleteExistingUser() {
		accHelper = app.getAccountHelper();
		userHelper = app.getUserHelper();
		
		accHelper.login(admin);

		userHelper.gotoUsersList();
		SortedListOf<User> oldList = userHelper.getUsersList();
		
		userHelper.deleteUserBylogin(user.login);
		
		SortedListOf<User> newList = userHelper.getUsersList();
	    
		assertThat(newList, equalTo(oldList.without(user)));
	}

	@Test
	public void deleteExistingUserWithIndex() {
		accHelper = app.getAccountHelper();
		userHelper = app.getUserHelper();
		
		accHelper.login(admin);

		userHelper.gotoUsersList();
		SortedListOf<User> oldList = userHelper.getUsersList();
		
		int index = userHelper.deleteUserWithIndex(oldList, user);
		
		SortedListOf<User> newList = userHelper.getUsersList();
	    
		assertThat(newList, equalTo(oldList.without(index)));
	}

}
