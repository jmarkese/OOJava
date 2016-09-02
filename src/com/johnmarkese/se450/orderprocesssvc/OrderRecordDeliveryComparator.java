package com.johnmarkese.se450.orderprocesssvc;

import java.time.LocalDate;
import java.util.Comparator;

public class OrderRecordDeliveryComparator  implements Comparator<OrderRecord>{

	@Override
	public int compare(OrderRecord o1, OrderRecord o2) {
		LocalDate d1 = o1.getDeliveryDay();
		LocalDate d2 = o2.getDeliveryDay();

		if (d1.isBefore(d2)) {
			return -1;
		} else if (d1.isAfter(d2)) {
			return 1;
		} else {
			return 0;
		} 
	}
}
