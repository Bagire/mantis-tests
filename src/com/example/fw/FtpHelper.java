package com.example.fw;


import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FtpHelper {

	public static Logger log = Logger.getLogger(FtpHelper.class.getName());

	private ApplicationManager app;

	private FTPClient ftp;

	public FtpHelper(ApplicationManager app) {
		this.app = app;
	}

	private void initFtpConnection() {
		String ftpserver = app.getProperty("ftp.host");
		String login = app.getProperty("ftp.login");
		String password = app.getProperty("ftp.password");
		String appPath = app.getProperty("ftp.appPath");

		if (ftp == null) {
			ftp = new FTPClient();
		}
		if (ftp.isConnected()) {
			return;
		}

		try {
			ftp.connect(ftpserver);
		    ftp.login(login, password);
			ftp.changeWorkingDirectory(appPath);

	    } catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void backupFile(String file, String fileBackup) {
		initFtpConnection();
		try {
			// Check if there is backup of config file
			boolean backupExists = false;
			FTPFile[] files = ftp.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].getName().equals(fileBackup)) {
					backupExists = true;
					break;
				}
			}
			if (!backupExists) {
				ftp.rename(file, fileBackup);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void restoreFile(String fileBackup, String file) {
		initFtpConnection();
		try {
			// Check if there is backup of config file
			boolean backupExists = false;
			FTPFile[] files = ftp.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].getName().equals(fileBackup)) {
					backupExists = true;
					break;
				}
			}
			if (backupExists) {
				ftp.deleteFile(file);
				ftp.rename(fileBackup, file);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void uploadFile(InputStream in, String targetFile) {
		initFtpConnection();
		try {
			ftp.storeFile(targetFile, in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeFtpConnection() {
		try {
			ftp.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
