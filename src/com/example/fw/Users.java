package com.example.fw;

import java.util.ArrayList;
import java.util.List;

public class Users {

	private List<String> storedUsers = null;
	
	public Users(List<String> users) {
		this.storedUsers = new ArrayList<String>(users);
	}

	public int findUser(String login) {
		Users newList = new Users(storedUsers);
		int row = newList.storedUsers.indexOf(login);
		return row;
	}

}
