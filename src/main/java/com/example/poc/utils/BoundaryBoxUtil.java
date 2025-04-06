package com.example.poc.utils;

import static com.example.poc.utils.ReporterLogUtil.log;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;

public class BoundaryBoxUtil {
	
	private Locator locator;

	public BoundaryBoxUtil(Locator loc)
	{
		this.locator = loc;
	}


	/**
	 *  Calculate desired hover coordinates relative to the element's top-left 
	 *  corner to hover at the center of the element:
	 * @return
	 */

	public  Position getPosition()
	  {
		  BoundingBox boundingBox = locator.boundingBox();
		  int hoverX=0;
		  int hoverY=0;
	      if (boundingBox != null) {
	         hoverX = (int)(boundingBox.width / 3);
	         hoverY = (int)(boundingBox.height / 3);
	        log("Postion :- x: " + hoverX + " y: "+hoverY);
	    }
	      return new Position(hoverX, hoverY);
	  }

	      
	    
	  
	}