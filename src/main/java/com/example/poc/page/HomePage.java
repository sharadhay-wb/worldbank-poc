package com.example.poc.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class HomePage {
	Page page;

	public HomePage(Page page) {
		this.page = page;
	}

	public VisionPage clickVision() {
		getvisionLoc().click();
		return new VisionPage(page);
	}

	public boolean isVisible() {
		return getvisionLoc().isVisible();
	}

	public Locator getvisionLoc() {
		return page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("VISION").setExact(true));
	}

}
