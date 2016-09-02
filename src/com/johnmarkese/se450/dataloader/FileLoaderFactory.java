package com.johnmarkese.se450.dataloader;

public class FileLoaderFactory {
	
	private FileLoaderFactory(){}
	
	public static FileLoader createDataLoader(String pathArg) throws DataLoaderException {
		//System.out.println(pathArg);
		if (pathArg.length() < 3) {
			throw new DataLoaderException("**** The path (" + pathArg + ") is too short.");
		}
		
		String ext;
		int i = pathArg.lastIndexOf('.');
		if (i > 0) {
			ext = pathArg.substring(i);
		} else {
			throw new DataLoaderException("**** File extension does not exist");
		}
				
		if (ext.equals(".xml")) {
			return new LocalXmlFileLoaderImpl(pathArg);
		} else {
			throw new DataLoaderException("**** Unexpected file extension (" + ext + ")");
		}
	}
}