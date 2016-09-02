package com.johnmarkese.se450.facilitysvc;

import java.util.Collection;

import com.johnmarkese.se450.dataloader.DataLoader;
import com.johnmarkese.se450.dataloader.DataLoaderException;

public interface FacilityLoader extends DataLoader{
	public Collection<Facility> load() throws DataLoaderException;
}
