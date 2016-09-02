package com.johnmarkese.se450.reportsvc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.johnmarkese.se450.dataloader.DataLoaderException;
import com.johnmarkese.se450.facilitysvc.FacilityDTO;
import com.johnmarkese.se450.facilitysvc.FacilityException;
import com.johnmarkese.se450.facilitysvc.FacilitySvc;
import com.johnmarkese.se450.inventorysvc.InventoryException;
import com.johnmarkese.se450.itemsvc.ItemDTO;
import com.johnmarkese.se450.networksvc.NetworkSvc;
import com.johnmarkese.se450.networksvc.NetworkException;
import com.johnmarkese.se450.networksvc.shortestpath.ShortestPathException;
import com.johnmarkese.se450.orderprocesssvc.ItemSummaryDTO;
import com.johnmarkese.se450.orderprocesssvc.OrderProcessedDTO;
import com.johnmarkese.se450.orderprocesssvc.OrderProcessorException;

import com.johnmarkese.se450.utils.ParameterValidationException;

public class Reports {
	
	public static String orderReport(OrderProcessedDTO record, int i)
			throws ReportException, IOException, DOMException, DataLoaderException, InventoryException, ParameterValidationException, ParserConfigurationException, SAXException, ShortestPathException, NetworkException, FacilityException, OrderProcessorException {
		String path = "templates/order_report.txt";
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		String template = new String(encoded, StandardCharsets.UTF_8);
	
		ArrayList<String> args = new ArrayList<String>();
		args.add(Integer.toString(i));
		args.add(record.order.orderId);
		args.add(date2days(record.order.day));
		args.add(record.order.facility);
		args.add(itemsList(record.order.items));
		args.add(String.format("$%,.0f",record.totalCost));
		args.add(Integer.toString(record.firstDay));
		args.add(Integer.toString(record.lastDay));
		args.add(itemSummaries(record.itemSummaries));
		
		return String.format(template, args.toArray());
	}
	
	private static String itemSummaries(ArrayList<ItemSummaryDTO> itemSummaries) {
		StringBuilder sb = new StringBuilder();
		int n = 0;
		for(ItemSummaryDTO i : itemSummaries){
			sb.append(String.format("\t    %-3s", ++n + ")"));
			boolean committed = false;
			if(i.committed > 0){
				committed = true;
				sb.append(String.format("%-10s %-10d $%,-9.0f %-15d %-10d %-10d\n", i.itemCode, i.committed, i.cost, i.sources, i.firstDay, i.lastDay));
			}
			if(i.backordered > 0){
				if (committed) { sb.append("\t       "); };
				sb.append(String.format("%-10s %-10d [ - - - - - - -  BACK-ORDERED  - - - - - - - ]\n", i.itemCode, i.backordered));
			}
		}
		return sb.toString();
	}

	private static String itemsList(LinkedHashMap<ItemDTO, Integer> items){
		StringBuilder sb = new StringBuilder();
		int n = 0;
		for(ItemDTO i : items.keySet()){
			sb.append(String.format("\t%d) Item ID: %9s,   Quantity: %d\n", ++n, i.code, items.get(i)));
		}
		return sb.toString();
	}
	
	
	public static String faciltyStatusReport(FacilityDTO facilty)
			throws ReportException, IOException, DOMException, DataLoaderException, InventoryException, ParameterValidationException, ParserConfigurationException, SAXException, ShortestPathException, NetworkException, FacilityException {
		String path = "templates/facility_status_report.txt";
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		FacilitySvc fs = FacilitySvc.getInstance();

		// setup input strings
		int days = 20;
		StringBuilder day = new StringBuilder();
		StringBuilder avail = new StringBuilder();
		StringBuilder inventory = new StringBuilder();
		StringBuilder depleted = new StringBuilder();
		StringBuilder links = new StringBuilder();
		ArrayList<Integer> slots = fs.getDaysSlots(facilty.name, days, LocalDate.now());
		
		for (int i = 0; i < days; i++) {
			day.append(String.format("%3d", i + 1));
			avail.append(String.format("%3d", slots.get(i)));
		}

		Map<String, Integer> inv = fs.getInventory(facilty.name);
		ArrayList<String> keys = new ArrayList<String>(inv.keySet());
		keys.sort(null);
		for (String s : keys) {
			int i = inv.get(s);
			if (i != 0) {
				inventory.append(String.format("	%-8s	%d\n", s, i));
			} else {
				if (depleted.length() == 0) {
					depleted.append(s);
				} else {
					depleted.append(", " + s);
				}
			}
		}
		Map<String, Double> nei = fs.getNeighbors(facilty.name);
		keys = new ArrayList<String>(nei.keySet());
		keys.sort(null);
		for (String s : keys) {
			double d = nei.get(s);
			links.append(String.format("%s (%.1fd); ", s, d));
		}
		links = new StringBuilder(lineBreaker(links.toString(), 77));

		// insert input strings to the template
		String template = new String(encoded, StandardCharsets.UTF_8);
		ArrayList<String> args = new ArrayList<String>();
		args.add(facilty.name);
		args.add(String.valueOf(facilty.rate));
		args.add(String.valueOf(facilty.cost));
		args.add(String.valueOf(links));
		args.add(inventory.toString());
		args.add((depleted.length() == 0) ? "None" : depleted.toString());
		args.add(day.toString());
		args.add(avail.toString());

		return String.format(template, args.toArray());
	}
	
	public static String catalogReport(List<ItemDTO> catalog) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("Item Catalog:\n");
		int n = 1;
		for (ItemDTO i : catalog) {
			String item = String.format("%-8s", i.code) + String.format(": $%,-6.0f", i.price);
			sb.append(String.format("%-17s ", item));
			if (n++ % 4 == 0) {
				sb.append("\n");
			}
		}
		return sb.toString();
	}
	
	public static String shortestPathReport(LinkedHashMap<String, String> pathMap) throws ParameterValidationException, ShortestPathException, DOMException, DataLoaderException, ParserConfigurationException, SAXException, IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("Shortest Path Tests:\n");
		NetworkSvc ns = NetworkSvc.getInstance();
		ArrayList<String> path = new ArrayList<String>();
		char c = 'a';
		for (String v : pathMap.keySet()) {
			String w = pathMap.get(v);
			path = (ArrayList<String>) ns.shortestPath(v, w);
			
			sb.append(c++ + ") " + v +" to " + w + "\n    ");
			boolean hyphen = true;
			for (String s : path) {
				if (hyphen) {
					sb.append("- " + s);
					hyphen = false;
				} else {
					sb.append("->" + s);
				}
			}
			sb.append(String.format(" = %,.0f mi\n    - %,.0f mi / (8 hours per day * 50 mph) =  %,.2f days\n\n", ns.getDistance(), ns.getDistance(), ns.getDistanceModified()));
		}
		return sb.toString();
	}

	private static String lineBreaker(String s, int chars) {
		StringBuilder sb = new StringBuilder(s);
		int i = 0;
		while ((i = sb.indexOf(" ", i + chars)) != -1) {
			sb.replace(i, i + 1, "\n");
		}
		return sb.toString();
	}
	
	private static String date2days(LocalDate day){
		return Long.toString(1 + LocalDate.now().until(day, ChronoUnit.DAYS));
	}

}
