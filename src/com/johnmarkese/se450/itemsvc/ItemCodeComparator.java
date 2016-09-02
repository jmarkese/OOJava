package com.johnmarkese.se450.itemsvc;

import java.util.Comparator;

public class ItemCodeComparator implements Comparator<ItemCode> {
	@Override
	public int compare(ItemCode i1, ItemCode i2) {
	    if (i1 == i2) {
	        return 0;
	    }
	    if (i1 == null) {
	        return -1;
	    }
	    if (i2 == null) {
	        return 1;
	    }
		return i1.getCode().compareTo(i2.getCode());
	}

}
