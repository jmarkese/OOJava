package com.johnmarkese.se450.itemsvc;

import java.util.Collection;

import com.johnmarkese.se450.dataloader.DataLoader;
import com.johnmarkese.se450.dataloader.DataLoaderException;

public interface ItemLoader extends DataLoader{
	public Collection<Item> load() throws DataLoaderException;
}
