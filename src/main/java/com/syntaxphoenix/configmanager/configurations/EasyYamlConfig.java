package com.syntaxphoenix.configmanager.configurations;

import java.io.File;
import java.io.IOException;

import com.syntaxphoenix.configmanager.config.configurations.FileConfiguration;
import com.syntaxphoenix.configmanager.config.configurations.YamlConfiguration;

public class EasyYamlConfig {
	
	protected File file;
	protected FileConfiguration fileConfiguration;
	
	public EasyYamlConfig(File file) {
		this.file = file;
		this.fileConfiguration = YamlConfiguration.loadConfiguration(file);
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T set(String path, T value) throws IOException {
		if (this.fileConfiguration.contains(path)) {
			return (T) this.fileConfiguration.get(path);
		}
		this.fileConfiguration.set(path, value);
		save();
		return value;
	}
	
	protected void save() throws IOException {
		this.fileConfiguration.save(this.file);
	}

}
