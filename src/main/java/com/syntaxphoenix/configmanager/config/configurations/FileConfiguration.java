package com.syntaxphoenix.configmanager.config.configurations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import com.syntaxphoenix.configmanager.config.memory.MemoryConfiguration;

public abstract class FileConfiguration extends MemoryConfiguration {
	
	public void load(File file) {
		if (file.exists()) {
			try {
				this.load(new FileInputStream(file));
			} catch (FileNotFoundException exception) {
				exception.printStackTrace();
			}
		}
	}
	
	public abstract void load(InputStream stream);
	
	public void save(File file) throws IOException {
		if (!file.exists()) {
			if (file.getParentFile() != null && file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			file.createNewFile();
		}

        String data = saveToString();

        FileWriter writer = new FileWriter(file);

        try {
            writer.write(data);
        } finally {
            writer.close();
        }
	}
	
	public abstract void loadFromString(String contents);
	
	public abstract String saveToString();

}
