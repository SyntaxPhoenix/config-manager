package com.syntaxphoenix.configmanager.config;

import java.util.Map;

public interface ConfigurationSection {
	
	ConfigurationSection getRoot();
	
	ConfigurationSection getConfigurationSection(String path);
	
	boolean isConfigurationSection(String path);
	
	boolean contains(String path);
	
	Object get(String path);
	
	void set(String path, Object value);
	
	Map<String, Object> getValues(boolean deep);

}
