
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;


import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.johnmarkese.se450.dataloader.DataLoaderException;
import com.johnmarkese.se450.facilitysvc.FacilityDTO;
import com.johnmarkese.se450.facilitysvc.FacilityException;
import com.johnmarkese.se450.facilitysvc.FacilitySvc;
import com.johnmarkese.se450.facilitysvc.ScheduleException;
import com.johnmarkese.se450.inventorysvc.InventoryDepletedException;
import com.johnmarkese.se450.inventorysvc.InventoryException;
import com.johnmarkese.se450.networksvc.NetworkSvc;
import com.johnmarkese.se450.networksvc.NetworkSvc.Round;
import com.johnmarkese.se450.networksvc.shortestpath.ShortestPathException;
import com.johnmarkese.se450.orderprocesssvc.OrderProcessorException;
import com.johnmarkese.se450.orderprocesssvc.OrderProcessorSvc;
import com.johnmarkese.se450.ordersvc.OrderDTO;
import com.johnmarkese.se450.ordersvc.OrderException;
import com.johnmarkese.se450.ordersvc.OrderLoaderXMLImpl;
import com.johnmarkese.se450.ordersvc.OrderSvc;
import com.johnmarkese.se450.reportsvc.Reports;
import com.johnmarkese.se450.utils.ParameterValidationException;

public class test {
	public static void main(String[] args) throws OrderException, DataLoaderException, ParameterValidationException, DOMException, FacilityException, InventoryException, ParserConfigurationException, SAXException, IOException, ShortestPathException, ScheduleException, InventoryDepletedException, OrderProcessorException {
		/*try {
			FileLoader fl = FileLoaderFactory.createDataLoader("data/config.xml");
			NodeList nl = (NodeList) fl.load();
			System.out.println(nl.item(0).getFirstChild());
			for (int k = 0; k < nl.getLength(); k++) {
				printTags((Node) nl.item(k));
			}
		} catch (DataLoaderException e) {
			e.printStackTrace();
		}
		try {
			Vertex A = VertexFactory.vertex("Angela");
			Vertex B = VertexFactory.vertex("Bill");
			Vertex C = VertexFactory.vertex("Cathy");
			Vertex D = VertexFactory.vertex("Donald");
			Vertex E = VertexFactory.vertex("Eli");
			Vertex F = VertexFactory.vertex("Frank");

			ArrayList<Vertex> vertices = new ArrayList<Vertex>();
			vertices.add(A);
			vertices.add(B);
			vertices.add(C);
			vertices.add(D);
			vertices.add(E);
			vertices.add(F);

			A.addEdge(EdgeFactory.edge(B, 10));
			B.addEdge(EdgeFactory.edge(D, 10));
			C.addEdge(EdgeFactory.edge(E, 10));
			C.addEdge(EdgeFactory.edge(B, 10));
			D.addEdge(EdgeFactory.edge(F, 10));
			E.addEdge(EdgeFactory.edge(D, 10));
			F.addEdge(EdgeFactory.edge(C, 10));
			F.addEdge(EdgeFactory.edge(E, 10));

			ShortestPath test = new ShortestPath(A, F, vertices);
			System.out.println("Distance from " + A + " to " + F + ": " + test.getDistance());
			System.out.println("Path: " + test.getPath());

			NetworkLoaderXMLImpl nl = new NetworkLoaderXMLImpl("data/facilities.xml");
			HashMap<String, Vertex> network = (HashMap<String, Vertex>) nl.load();

			Vertex a = network.get("St. Louis, MO");
			Vertex b = network.get("New York City, NY");
			ShortestPath sp = new ShortestPath(a, b, network.values());
			ArrayList<String> path = (ArrayList<String>) sp.getPath();
			System.out.println("Distance from " + a + " to " + b + ": " + sp.getDistance());
			System.out.println("Path: " + path);

			Vertex a1 = network.get("Santa Fe, NM");
			Vertex b1 = network.get("Chicago, IL");
			ArrayList<String> path1 = (ArrayList<String>) sp.getPath();
			WeightModifier wm = WeightModifierFactory.getInstance().modifier("standard");
			System.out.println(
					"Distance from " + a1 + " to " + b1 + ": " + sp.getDistanceModified() + " in " + sp.getDistance());
			System.out.println("Path: " + path1);
		} catch (ParameterValidationException | DataLoaderException | ShortestPathException | DOMException | ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		try {
			NetworkSvc ns = NetworkSvc.getInstance();
			System.out.println(ns.getNeighbors("New York City, NY"));
		} catch (DataLoaderException | NetworkException | DOMException | ParameterValidationException | ParserConfigurationException | SAXException | IOException | ShortestPathException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			ItemLoaderXMLImpl itemLoader = new ItemLoaderXMLImpl("data/items.xml");
			ArrayList<Item> items = (ArrayList<Item>) itemLoader.load();
			System.out.println("Items: " + items);
		} catch (DataLoaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			ItemSvc is = ItemSvc.getInstance();
			ItemLoader itemLoader = new ItemLoaderFactory().dataLoader();
			ArrayList<Item> items = (ArrayList<Item>) itemLoader.load();
			System.out.println("Items: " + items);
			System.out.println(is.getInstance().getItem("ABC123"));
			System.out.println(is.getInstance().getCatalog());
		} catch (DataLoaderException e) {
			e.printStackTrace();
		}

		try {
			InventoryLoaderXMLImpl itemLoader = new InventoryLoaderXMLImpl("data/inventories.xml");

			HashMap<ItemDTO, Integer> items = (HashMap<ItemDTO, Integer>) itemLoader.load("San Francisco, CA");
			System.out.println(items);
			Set<ItemDTO> set = items.keySet();
			for (ItemDTO i : set) {
				System.out.println(items.get(i));
			}
		} catch (DataLoaderException e) {
			e.printStackTrace();
		}

		try {
			InventorySvc invs = InventorySvc.getInstance();
			InventoryLoaderXMLImpl itemLoader = new InventoryLoaderXMLImpl("data/inventories.xml");

			HashMap<ItemDTO, Integer> items = (HashMap<ItemDTO, Integer>) itemLoader.load("San Francisco, CA");
			System.out.println(items);
			Set<ItemDTO> set = items.keySet();
			for (ItemDTO i : set) {
				System.out.println(items.get(i));
			}
			System.out.println(invs.getInstance().getInventory("New York City, NY").getInventory());
			System.out.println(invs.getInstance().getInventory("Chicago, IL").getInventory());
			System.out.println(invs.getInstance().getInventory("Miami, FL").getInventory());
			System.out.println(invs.getInstance().getInventory("Seattle, WA").getInventory());
			System.out.println(invs.getInstance().getInventory("Miami, FL").getInventory());
			System.out.println(invs.getInstance().getInventory("Denver, CO").getInventory());

		} catch (InventoryException | ParameterValidationException | DataLoaderException e) {
			e.printStackTrace();
		}

		Schedule365Impl cal;
		try {
			cal = new Schedule365Impl(10);

			// System.out.println(cal.getOpenSlots(LocalDate.now()));
			cal.bookSlots(32);
			System.out.println(cal.getSchedule());
			// System.out.println(cal.getOpenSlots(LocalDate.now()));
			System.out.println(cal.getDaysSlots(20));
		} catch (ParameterValidationException | ScheduleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			FacilitySvc fl = FacilitySvc.getInstance();
			System.out.println(fl.getInstance().getFacilities());
			System.out.println(fl.getInstance().getNeighbors("Miami, FL"));
			FacilityDTO chicago = fl.getInstance().getFacility("Chicago, IL");
			FacilityDTO miami = fl.getInstance().getFacility("Miami, FL");
			System.out.println(chicago.name+" "+chicago.rate+" "+chicago.cost);
			System.out.println(fl.getInstance().getNeighbors(chicago.name));
			//System.out.println(fl.getInstance().network.shortestPath(chicago.name, miami.name));
			//System.out.println(fl.getInstance().network.shortestPath(chicago.name, "Denver, CO"));
			System.out.println(fl.getInstance().getInventory(chicago.name));	
			System.out.println(fl.getInstance().getInventory("Seattle, WA"));		

		} catch (FacilityException | DataLoaderException | InventoryException | NetworkException | DOMException | ParameterValidationException | ParserConfigurationException | SAXException | IOException | ShortestPathException e){
			e.printStackTrace();
		}
		
		try {
			FacilityLoaderXMLImpl fl = new FacilityLoaderXMLImpl("data/facilities.xml");
			Collection<Facility> facilities = (Collection<Facility>) fl.load();
			System.out.println("Facilities:" + facilities);
		} catch (DataLoaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		/*
		try {
			OrderLoaderXMLImpl ol = new OrderLoaderXMLImpl("data/orders.xml");
			
			System.out.println("Orders: " + ol.load());
		} catch (DataLoaderException | ParameterValidationException | OrderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
			/*FacilitySvc fs = FacilitySvc.getInstance();
			ArrayList<FacilityDTO> fList = fs.getFacilities();
			
			for(FacilityDTO fDTO : fList) {
				System.out.print(Reports.faciltyStatusReport(fDTO));
				System.out.print("\n\n");				
			}*/
			
			//HashMap<FacilityDTO, Integer> fi = FacilitySvc.getInstance().findFacilityItem("ABC123");
			//System.out.println(fi);
			NetworkSvc ns = NetworkSvc.getInstance();
			FacilitySvc fs = FacilitySvc.getInstance();			
			OrderSvc os = OrderSvc.getInstance();
			
			ArrayList<OrderDTO> orders = (ArrayList<OrderDTO>) OrderSvc.getInstance().getOrders();
			for(OrderDTO order : orders){
				System.out.println("\nORDER : \n======================================\n" + order);
				//OrderProcessorSvc.getInstance().processOrder(order);
				//break;
				/*System.out.println("\nORDER : \n======================================\n" + order);
				
				//<order processing>
				for(String item : order.items.keySet()){
					int itemQty = order.items.get(item);
					System.out.print(String.format("%10s : %3d ", item, itemQty));

					FacilityDTO myDTO = fs.getFacility(order.facility);
					HashMap<FacilityDTO, Integer> fItems = fs.findFacilityItem(item);
					fItems.remove(myDTO);
					
					System.out.println(fItems);
					for(FacilityDTO fac : fItems.keySet()){
						ArrayList<String> path = (ArrayList<String>)ns.shortestPath(fac.name, order.facility);
						int itemAvail = fItems.get(fac);
						int itemsProcess = (itemAvail <= itemQty) ? itemAvail : itemQty;
						long processDays = ChronoUnit.DAYS.between(order.day, fs.getEarliestCompleteDate(fac.name, order.day, itemsProcess));
						System.out.println(String.format("[ %18s : %2.0f (mod%f, dist%f) travel + %2d process days for %3d units] %s", fac.name, ns.getDistanceModified(Round.UP), ns.getDistance(), ns.getDistanceModified(), processDays, itemsProcess, path));
					}
				}
				//</order processing>
				*/
			}
			
 		
	}

	public static void printTags(Node nodes) {
		if (nodes.hasChildNodes()) {
			System.out.println(nodes.getNodeName() + " : " + nodes.getTextContent());
			NodeList nl = nodes.getChildNodes();
			for (int j = 0; j < nl.getLength(); j++)
				printTags(nl.item(j));
		}
	}
}
