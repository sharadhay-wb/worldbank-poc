package com.example.poc.test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.poc.utils.ReporterLogUtil.log;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.example.poc.page.HomePage;
import com.example.poc.page.VisionPage;
import com.example.poc.utils.BoundaryBoxUtil;
import com.example.poc.utils.ExcelUtil;
import com.example.poc.utils.PlaywrightStarter;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator.ClickOptions;
import com.microsoft.playwright.Locator.HoverOptions;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.Position;
import com.microsoft.playwright.options.WaitForSelectorState;

public class VisionIndicatorTest {

	private Page page;
	private HomePage homePage;
	private VisionPage visionPage;
	private String url = "https://scorecard.worldbank.org/en/home";
	String avgIncomeHeading = "Average income shortfall from a prosperity standard of $25/day";
	private String highIneqHeading="Number of countries/economies with high inequality, defined as those with a Gini index greater than 40.";

	public VisionIndicatorTest() {
		page = PlaywrightStarter.getPage();
		homePage = new HomePage(page);

	}

	@Test(enabled = true)
	public void verifyVision() {
		// Step 1
		log("Open the URL.:- " + url);
		page.navigate(url);
		log("Assert that the VISION link is present.");
		assertTrue(homePage.isVisible(), "The element should be visible.");
		// Step 2
		log("Navigate to the Vision tab");
		visionPage = homePage.clickVision();
		page.waitForTimeout(1000);

	}

	@Test(enabled = true, dependsOnMethods = "verifyVision")
	public void verifyPovChart() throws IOException {
		// Step 3
		visionPage.getLivInPovHeading().scrollIntoViewIfNeeded();
		log("Verify whether the line chart is displayed by default on the page for the Percentage of population living in poverty (at $2.15/day and $6.85/day)vision indicator");
		assertThat(page.locator("section"))
				.containsText("Percentage of population living in poverty (at $2.15/day and $6.85/day)");
		page.waitForTimeout(3000);
		verifyLineChart(visionPage.getLivInPovHeading());
		// Step 4
		verifyData(visionPage.getChart6_85());
		compareExcelWithTooltip(getTooltip(), visionPage.getLivInPovHeading().textContent());
		
		//Step 5
		log("Navigate to Vision Indicator Detail Page");
		visionPage.getMoreData().click();
		

	}

	@Test(enabled = false, dependsOnMethods = "verifyPovChart")
	public void verifyAvgIncome() throws IOException {
		// Step 3
		page.waitForTimeout(3000);
		visionPage.getAvgIncomeHeading().scrollIntoViewIfNeeded();
		log("Assert Average income shortfall from a prosperity standard of $25/day");
		assertThat(page.locator("section"))
				.containsText("Average income shortfall from a prosperity standard of $25/day");
		page.waitForTimeout(3000);
		assertThat(visionPage.getAvgIncChart()).isVisible();
		// Step 4
		verifyData(visionPage.getAvgIncChart());
		compareExcelWithTooltip(getTooltip(),visionPage.getAvgIncomeHeading().textContent() );
	}
	
	@Test(enabled = false, dependsOnMethods = "verifyVision")
	public void verifyHighIneq() throws IOException {
		// Step 3
		page.waitForTimeout(3000);
		visionPage.getHighIneqHeading().scrollIntoViewIfNeeded();
		log("Assert Average income shortfall from a prosperity standard of $25/day");
		assertThat(page.locator("section"))
				.containsText(highIneqHeading);
		page.waitForTimeout(3000);
		visionPage.clickIneqChartButton();
		page.waitForLoadState(LoadState.NETWORKIDLE);
		page.waitForTimeout(4000);
//		visionPage.getHighIneqChart().scrollIntoViewIfNeeded();
//		page.waitForSelector("#highcharts-gj1se4u-187 rect", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
		assertThat(visionPage.getHighIneqChart()).isVisible();
		// Step 4
		page.waitForTimeout(2000);
		verifyData(visionPage.getHighIneqChart());
		compareExcelWithTooltip(getTooltip(),visionPage.getHighIneqHeading().textContent() );
	}
	

	private void verifyData(Locator locator) {
		log("Verify tooltip present with the Excel");
		log("Read the tooltip");
		assertThat(locator).isVisible();
		page.waitForTimeout(7000);
		Position position = new BoundaryBoxUtil(locator).getPosition();
		page.waitForTimeout(3000);
		locator.hover(new HoverOptions().setPosition(position));
		page.waitForTimeout(3000);
		locator.click(new ClickOptions().setPosition(position));
		page.waitForTimeout(3000);

	}

	private HashMap<Integer, Float> getTooltip() {
		System.err.println("tooltip : " + visionPage.getTooltip().textContent());
		return getYearPerMap(visionPage.getTooltip().textContent());

	}

	private void compareExcelWithTooltip(HashMap<Integer, Float> toolTip, String heading) throws IOException {
		ExcelUtil excel = new ExcelUtil();
		HashMap<Integer, Double> excelDataMap=  excel.getExcelData(heading);
		
		toolTip.keySet().forEach(t -> {
			if (excelDataMap.containsKey(t)) {
				System.err.println("coming now!!!" + excelDataMap.get(t).toString() + " and OG tool tip "
						+ toolTip.get(t).toString());
				assertEquals(toolTip.get(t).toString(), excelDataMap.get(t).toString(),
						"The actual text does not match the expected text.");
			}
		});
	}


	private HashMap<Integer, Float> getYearPerMap(String toolTip) {
		System.err.println("Here comes " + toolTip);
		if(!toolTip.contains("%"))
		{
			toolTip = toolTip+"%";
		}
		Pattern pattern = Pattern.compile("\\((\\d{4})\\)\\s*\\|\\s*([\\d\\.]+)%");
		Matcher matcher = pattern.matcher(toolTip);
		HashMap<Integer, Float> result = new HashMap<>();
		if (matcher.find()) {
			int key = Integer.parseInt(matcher.group(1)); // Captures "2011"
			float value = Float.parseFloat(matcher.group(2));
			System.err.println(" Key: " + key + " Value " + value);
			result.put(key, value);
		}
		return result;

	}

	// Step 3: Verify Line Chart for the Vision Indicator
	private static void verifyLineChart(Locator locator) {
		Locator chart = locator.locator("xpath=../following-sibling::div//*[name()='app-spline-chart']");
		log("Assert that the Line Chart is present.");
		assertThat(chart).isVisible();
	}

}