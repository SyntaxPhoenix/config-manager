package com.syntaxphoenix.configmanager.config.configurations;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.representer.Representer;

import com.syntaxphoenix.configmanager.config.ConfigurationSection;

public class YamlConfiguration extends FileConfiguration {
	
	private Yaml yaml;
    private DumperOptions yamlOptions;
    private Representer yamlRepresenter;
	
	public YamlConfiguration() {
		this.yamlOptions = new DumperOptions();
		this.yamlRepresenter = new Representer();
		this.yaml = new Yaml(new SafeConstructor(), yamlRepresenter, yamlOptions);
	}

	@Override
	public void load(InputStream stream) {
		String newLine = System.getProperty("line.separator");
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		try {
			for (String line; (line = reader.readLine()) != null; ) {
			    result.append(flag? newLine: "").append(line);
			    flag = true;
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		this.loadFromString(result.toString());
	}

	@Override
	public String saveToString() {
		yamlOptions.setIndent(2);
        yamlOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        yamlOptions.setAllowUnicode(true);
        yamlRepresenter.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        String dump = yaml.dump(this.getRoot().getValues(true));

        return dump;
	}

    @Override
    public void loadFromString(String contents) {
        Map<?, ?> input = yaml.load(contents);

        if (input != null) {
            convertMapsToSections(input, this);
        }
    }

    private void convertMapsToSections(Map<?, ?> input, ConfigurationSection section) {
        for (Map.Entry<?, ?> entry : input.entrySet()) {
            String key = entry.getKey().toString();
            Object value = entry.getValue();

            if (value instanceof Map) {
                convertMapsToSections((Map<?, ?>) value, section.getConfigurationSection(key));
            } else {
                section.set(key, value);
            }
        }
    }
	
	public static YamlConfiguration loadConfiguration(File file) {
		YamlConfiguration configuration = new YamlConfiguration();
		configuration.load(file);
		return configuration;
	}
}
