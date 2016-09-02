package com.johnmarkese.se450.orderprocesssvc;

import java.time.LocalDate;

import com.johnmarkese.se450.itemsvc.ItemCode;

public class ItemSummaryDTO implements ItemCode{
	public String itemCode;
	public double cost;
	public int backordered;
	public int committed;
	public int sources;
	public int firstDay;
	public int lastDay;
	
	public ItemSummaryDTO(String itemCode, int committed, int backordered, int sources, double cost,
			int firstDay, int lastDay) {
		this.itemCode = itemCode;
		this.backordered = backordered;
		this.committed = committed;
		this.sources = sources;
		this.firstDay = firstDay;
		this.lastDay = lastDay;
		this.cost = cost;
	}

	@Override
	public String getCode() {
		return itemCode;
	}

}
