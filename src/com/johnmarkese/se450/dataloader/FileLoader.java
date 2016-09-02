package com.johnmarkese.se450.dataloader;


public abstract interface FileLoader extends DataLoader{
	
	public Object load () throws DataLoaderException;

}