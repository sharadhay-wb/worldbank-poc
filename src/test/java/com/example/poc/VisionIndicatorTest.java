package com.example.poc;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static com.example.poc.utils.ReporterLogUtil.log;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.example.poc.utils.PlaywrightStarter;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class VisionIndicatorTest {

	Page page;
	
	public VisionIndicatorTest()
	{
		page = PlaywrightStarter.getPage();
	}
	
	@Test(enabled=true)
	public void verifyVision()
	{
		// Step 1
		log("Open the URL.:- https://scorecard.worldbank.org/en/home ");
		page.navigate("https://scorecard.worldbank.org/en/home");
		Locator visionLoc =page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("VISION").setExact(true));
		log("Assert that the VISION link is present.");
		assertThat(visionLoc).isVisible();
		
		// Step 2
		log("Navigate to the Vision tab");
		visionLoc.click();
		page.waitForTimeout(1000);
		
		//Step 3
		Locator heading =page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Percentage of population"));
		heading.scrollIntoViewIfNeeded();
		log("Verify whether the line chart is displayed by default on the page for the Percentage of population living in poverty (at $2.15/day and $6.85/day)vision indicator");
		assertThat(page.locator("section")).containsText("Percentage of population living in poverty (at $2.15/day and $6.85/day)");
		Locator lineChart = page.getByRole(AriaRole.LISTITEM).filter(new Locator.FilterOptions().setHasText("Line Chart")).getByLabel("Line Chart");
		assertThat(lineChart).isEnabled();
		
		
		 
	}
	
}