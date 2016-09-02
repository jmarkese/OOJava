import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.johnmarkese.se450.dataloader.DataLoaderException;
import com.johnmarkese.se450.facilitysvc.FacilityDTO;
import com.johnmarkese.se450.facilitysvc.FacilityException;
import com.johnmarkese.se450.facilitysvc.FacilitySvc;
import com.johnmarkese.se450.facilitysvc.ScheduleException;
import com.johnmarkese.se450.inventorysvc.InventoryException;
import com.johnmarkese.se450.networksvc.NetworkException;
import com.johnmarkese.se450.networksvc.shortestpath.ShortestPathException;
import com.johnmarkese.se450.orderprocesssvc.OrderProcessedDTO;
import com.johnmarkese.se450.orderprocesssvc.OrderProcessorException;
import com.johnmarkese.se450.orderprocesssvc.OrderProcessorSvc;
import com.johnmarkese.se450.ordersvc.OrderDTO;
import com.johnmarkese.se450.ordersvc.OrderException;
import com.johnmarkese.se450.ordersvc.OrderSvc;
import com.johnmarkese.se450.reportsvc.ReportException;
import com.johnmarkese.se450.reportsvc.Reports;
import com.johnmarkese.se450.utils.ParameterValidationException;

public class Main {

	public static void main (String args[]) {
		try {
			
			System.out.println("------------------------------------------------------------------------------------");
			System.out.println("------------------------------------------------------------------------------------");
			System.out.println("-                                   FACILITIES 1                                   -");
			System.out.println("------------------------------------------------------------------------------------");
			System.out.println("------------------------------------------------------------------------------------");
			
			FacilitySvc fs = FacilitySvc.getInstance();
			ArrayList<FacilityDTO> fList = fs.getFacilities();			
			for(FacilityDTO fDTO : fList) {
				System.out.print(Reports.faciltyStatusReport(fDTO));
				System.out.print("\n\n");
			}
			
			//System.out.print("\n\n");
			//System.out.print(Reports.catalogReport(ItemSvc.getCatalog()));

			System.out.println("------------------------------------------------------------------------------------");
			System.out.println("------------------------------------------------------------------------------------");
			System.out.println("-                                      ORDERS                                      -");
			System.out.println("------------------------------------------------------------------------------------");
			System.out.println("------------------------------------------------------------------------------------");
			
			int i = 0;
			ArrayList<OrderProcessedDTO> orders = OrderProcessorSvc.getInstance().processedOrders();
			for(OrderProcessedDTO order : orders){
				System.out.print(Reports.orderReport(order, ++i));
			}

			System.out.println("------------------------------------------------------------------------------------");
			System.out.println("------------------------------------------------------------------------------------");
			System.out.println("-                                   FACILITIES 2                                   -");
			System.out.println("------------------------------------------------------------------------------------");
			System.out.println("------------------------------------------------------------------------------------");
			
			for(FacilityDTO fDTO : fList) {
				System.out.print(Reports.faciltyStatusReport(fDTO));
				System.out.print("\n\n");				
			}
			System.out.print("\n\n");

			//LinkedHashMap<String, String> pathMap = new LinkedHashMap<String, String>();
			/*pathMap.put("Santa Fe, NM", "Chicago, IL");
			pathMap.put("Atlanta, GA", "St. Louis, MO");
			pathMap.put("Seattle, WA", "Nashville, TN");
			pathMap.put("New York City, NY", "Phoenix, AZ");
			pathMap.put("Fargo, ND", "Austin, TX");
			pathMap.put("Denver, CO", "Miami, FL");
			pathMap.put("Austin, TX", "Norfolk, VA");
			pathMap.put("Miami, FL", "Seattle, WA");
			pathMap.put("San Francisco, CA", "Los Angeles, CA");
			System.out.print(Reports.shortestPathReport(pathMap));
			pathMap.put("San Francisco, CA", "Phoenix, AZ");
			System.out.print(Reports.shortestPathReport(pathMap));
			pathMap.put("San Francisco, CA", "Santa Fe, NM");
			System.out.print(Reports.shortestPathReport(pathMap));
			pathMap.put("San Francisco, CA", "Austin, TX");
			pathMap.put("Austin, TX", "San Francisco, CA");
			System.out.print(Reports.shortestPathReport(pathMap));*/
			//System.out.print(Reports.shortestPathReport(pathMap));
			
		} catch (DataLoaderException | InventoryException | IOException | FacilityException | NetworkException | DOMException | ParameterValidationException | ParserConfigurationException | SAXException | ShortestPathException | ReportException | ScheduleException | OrderProcessorException | OrderException e) {
			e.printStackTrace();
			e.getMessage();
		}
	}
}
