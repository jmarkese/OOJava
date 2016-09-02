package com.johnmarkese.se450.ordersvc;

import java.time.LocalDate;
import java.util.Comparator;

public class ComparatorOrderDay implements Comparator<Order> {

	@Override
	public int compare(Order o1, Order o2) {
		LocalDate d1 = o1.getDay();
		LocalDate d2 = o2.getDay();

		if (d1.isBefore(d2)) {
			return -1;
		} else if (d1.isAfter(d2)) {
			return 1;
		} else {
			return 0;
		} 
	}
}
