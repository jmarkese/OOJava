package com.johnmarkese.se450.ordersvc;

import java.util.Collection;

import com.johnmarkese.se450.dataloader.DataLoader;
import com.johnmarkese.se450.dataloader.DataLoaderException;
import com.johnmarkese.se450.utils.ParameterValidationException;

public interface OrderLoader extends DataLoader {
	public Collection<Order> load() throws DataLoaderException, ParameterValidationException, OrderException;
}
