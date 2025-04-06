package com.example.poc.utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PlaywrightStarter {
	static Browser browser;

	public PlaywrightStarter()
	{

	}

	public static Browser getChromiumBrowser()
	{
		Playwright playwright = Playwright.create();
		browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
				.setHeadless(false));
		return browser;
	}

	public static Page getPage()
	{
		BrowserContext context = getChromiumBrowser().newContext();
		return context.newPage();
	}

}
