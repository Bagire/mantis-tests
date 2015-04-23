package com.example.fw;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class ApplicationManager {
	
	private WebDriver driver;

	private HibernateHelper hibernateHelper;
	private AccountHelper accountHelper;
	private MailHelper mailHelper;
	private JamesHelper jamesHelper;
	private UserHelper userHelper;

	public Properties properties;

	public ApplicationManager(Properties properties) {
		this.properties = properties;
	}

	public void stop() {
	    driver.quit();
	}
	
	public String getProperty(String key) {
		return properties.getProperty(key);
	}
	
	public WebDriver getDriver() {
		if (driver == null) {
			String browser = properties.getProperty("browser");
			if ("firefox".equals(browser)) {
				driver = new FirefoxDriver();
			} else if ("ie".equals(browser)) {
				driver = new InternetExplorerDriver();
			} else {
				throw new Error("Unsupported browser: " + browser);
			}
		    long timeOut = Long.parseLong(properties.getProperty("timeOut"));
		    driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
			driver.get(properties.getProperty("baseUrl") + properties.getProperty("addrUrl"));
		}
		return driver;
	}

	public HibernateHelper getHibernateHelper() {
		if (hibernateHelper == null) {
			hibernateHelper = new HibernateHelper(this);
		}
		return hibernateHelper;
	}

	public AccountHelper getAccountHelper() {
		if (accountHelper == null) {
			accountHelper = new AccountHelper(this);
		}
		return accountHelper;
	}

	public MailHelper getMailHelper() {
		if (mailHelper == null) {
			mailHelper = new MailHelper(this);
		}
		return mailHelper;
	}

	public JamesHelper getJamesHelper() {
		if (jamesHelper == null) {
			jamesHelper = new JamesHelper(this);
		}
		return jamesHelper;
	}

	public UserHelper getUserHelper() {
		if (userHelper == null) {
			userHelper = new UserHelper(this);
		}
		return userHelper;
	}

}
