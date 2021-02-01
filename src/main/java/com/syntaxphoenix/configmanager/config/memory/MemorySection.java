package com.syntaxphoenix.configmanager.config.memory;

import java.util.HashMap;
import java.util.Map;

import com.syntaxphoenix.configmanager.config.ConfigurationSection;

public class MemorySection implements ConfigurationSection {
	
    private Map<String, Object> map;
    private ConfigurationSection parent;
    
    public MemorySection(ConfigurationSection parent) {
    	this.map = new HashMap<String, Object>();
    	
    	this.parent = parent;
    }

	public ConfigurationSection getConfigurationSection(String path) {
		String localPath = path;
		boolean deep = false;
		if (path.contains(".")) {
			localPath = path.split("\\.")[0];
			deep = true;
		}
		
		if (!map.containsKey(localPath) || !(map.get(localPath) instanceof ConfigurationSection)) {
			ConfigurationSection section = new MemorySection(parent);
			this.map.put(localPath, section);
			return section;
		}
		ConfigurationSection section = (ConfigurationSection) map.get(localPath);
		if (deep) {
			return section.getConfigurationSection(path.substring(localPath.length() + 1));
		}
		return section;
	}

	public boolean isConfigurationSection(String path) {
		String localPath = path;
		boolean deep = false;
		if (path.contains(".")) {
			localPath = path.split("\\.")[0];
			deep = true;
		}
		
		if (!map.containsKey(localPath) || !(map.get(localPath) instanceof ConfigurationSection)) {
			return false;
		}
		if (deep) {
			ConfigurationSection section = (ConfigurationSection) map.get(localPath);
			return section.isConfigurationSection(path.substring(localPath.length() + 1));
		}
		return true;
	}

	public Object get(String path) {
		String localPath = path;
		boolean deep = false;
		if (path.contains(".")) {
			localPath = path.split("\\.")[0];
			deep = true;
		}
		
		if (!map.containsKey(localPath)) {
			return null;
		}
		if (deep) {
			if (!(map.get(localPath) instanceof ConfigurationSection)) {
				return null;
			}
			ConfigurationSection section = (ConfigurationSection) map.get(localPath);
			return section.get(path.substring(localPath.length() + 1));
		}
		return map.get(localPath);
	}

	public void set(String path, Object value) {
		String localPath = path;
		boolean deep = false;
		if (path.contains(".")) {
			localPath = path.split("\\.")[0];
			deep = true;
		}
		
		if (deep) {
			if (!(map.get(localPath) instanceof ConfigurationSection)) {
				ConfigurationSection section = new MemorySection(parent);
				this.map.put(localPath, section);
				section.set(path.substring(localPath.length() + 1), value);
				return;
			}
			
			ConfigurationSection section = (ConfigurationSection) map.get(localPath);
			section.set(path.substring(localPath.length() + 1), value);		
			return;
		}
		
		map.put(localPath, value);
	}

	public Map<String, Object> getValues(boolean deep) {
        Map<String, Object> result = new HashMap<String, Object>();

        for (String path : this.map.keySet()) {
        	if (this.map.get(path) instanceof ConfigurationSection && deep) {
        		ConfigurationSection section = (ConfigurationSection) this.map.get(path);
        		result.put(path, section.getValues(deep));
        	} else {
        		result.put(path, this.map.get(path));
        	}
        }
        return result;
	}

	public ConfigurationSection getRoot() {
		return this.parent != null ? this.parent.getRoot() : this;
	}

	public boolean contains(String path) {
		String localPath = path;
		boolean deep = false;
		if (path.contains(".")) {
			localPath = path.split("\\.")[0];
			deep = true;
		}
		
		if (!map.containsKey(path)) {
			return false;
		}
		
		if (deep) {
			if (!(map.get(localPath) instanceof ConfigurationSection)) {
				return false;
			}
			
			ConfigurationSection section = (ConfigurationSection) map.get(localPath);	
			return section.contains(path.substring(localPath.length() + 1));
		}
		
		return true;
	}

}
