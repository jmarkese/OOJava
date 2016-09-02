package com.johnmarkese.se450.orderprocesssvc;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;

import com.johnmarkese.se450.itemsvc.ItemDTO;
import com.johnmarkese.se450.itemsvc.ItemCodeComparator;
import com.johnmarkese.se450.ordersvc.OrderDTO;
import com.johnmarkese.se450.utils.ParameterValidationException;

public class OrderProcessedDTO {
	public OrderDTO order;
	public ArrayList<ItemSummaryDTO> itemSummaries;
	public double totalCost = 0.0;
	public int firstDay = 0;
	public int lastDay = 0;

	public OrderProcessedDTO(OrderDTO order, ArrayList<ItemSummaryDTO> itemSummaries, double totalCost, int firstDay, int lastDay) {
		this.order = order;
		this.itemSummaries = itemSummaries;
		this.totalCost = totalCost;
		this.firstDay = firstDay;
		this.lastDay = lastDay;
	}
}
