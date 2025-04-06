package com.example.poc.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class VisionPage {
	Page page;

	public VisionPage(Page page) {
		this.page = page;
	}

	public Locator getLivInPovHeading() {
		return page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Percentage of population"));
	}

	public Locator getChart6_85() {
		return page.locator("g:nth-child(3) > .highcharts-tracker-line");
	}

// 
	public Locator getChart2_15() {
		// return page.locator("//h3[contains(text(),'Percentage of population living in
		// poverty')]/../following-sibling::div//*[name()='g' and
		// @class='highcharts-series highcharts-series-0 highcharts-spline-series']");
		return page.locator(".highcharts-tracker-line");
	}

//	public Locator getChart() {
//		return page.locator("g:nth-child(3) > .highcharts-tracker-line");
//	}

	public Locator getTooltip() {
		return page.locator("//div[@class='highcharts-label highcharts-tooltip highcharts-color-undefined']//div/span")
				.last();
	}

	public Locator getAvgIncomeHeading() {
		return page.getByText("Average factor by which incomes ");
	}

	public Locator getAvgIncChart() {
		return page.locator(
				"//h3[contains(text(),'Average income shortfall ')]/../following-sibling::div//*[name()='svg']");
	}

	public Locator getHighIneqHeading() {
		return page.getByText("Number of countries/economies ");
	}

	public Locator getIneqLineChartButton() {
		return page.locator(
				"//h3[text()='Economies with high inequality ']/../following-sibling::div//button[@id='Line Chart']");
	}

	public void clickIneqChartButton() {
		getIneqLineChartButton().click();
	}

	public Locator getHighIneqChart() {
//		return page.locator("//h3[contains(text(),'Economies with high ')]/../following-sibling::div//*[name()='svg']");
//		return page.locator("//h3[contains(text(),'Economies with high ')]/../following-sibling::div//*");
//		return page.locator("g:nth-child(1) > .highcharts-tracker-line").last();
//		return page.locator("#highcharts-n6687s9-159 rect").nth(1);
//		return page.locator("#highcharts-n6687s9-159 > .highcharts-root > .highcharts-series-group > .highcharts-series > .highcharts-tracker-line");
//		int count = page.locator("//*[name()='path' and @class='highcharts-tracker-line']").count();
//		if (count >= 2) {
////			System.out.println("Text: " + page.locator(".your-selector").nth(count - 2).textContent());
//		     return page.locator(".your-selector").nth(count - 2);
//		    // Now interact with secondToLastElement, e.g.:
//		    
//		} else {
//		    System.out.println("Not enough elements to select the second-to-last one.");
//		}
//		return  null;
//		return page.locator(
//				"//h3[contains(text(),'Economies with high ')]/../following-sibling::div//*[name()='svg']");
		return page.locator("#highcharts-gj1se4u-187 rect").nth(1);
	}
	
	public Locator getMoreData()
	{
		return page.getByText(" More Data ").first();
	}
}
