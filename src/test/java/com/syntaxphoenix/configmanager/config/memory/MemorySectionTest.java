package com.syntaxphoenix.configmanager.config.memory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;

import com.syntaxphoenix.configmanager.config.ConfigurationSection;
import com.syntaxphoenix.configmanager.tests.WhiteBox;

public class MemorySectionTest {
	
	@Test
	public void testSetLocal() {
		MemorySection memorySection = new MemorySection(null);
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = (HashMap<String, Object>) WhiteBox.getInternalState(memorySection, "map");
		assertEquals(0, map.size());
		memorySection.set("test", 2);
		assertEquals(1, map.size());
		assertEquals(2, map.get("test"));
	}
	
	@Test
	public void testSetDeep() {
		MemorySection memorySection = new MemorySection(null);
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = (HashMap<String, Object>) WhiteBox.getInternalState(memorySection, "map");
		assertEquals(0, map.size());
		memorySection.set("test.test", 2);
		assertEquals(1, map.size());
		assertTrue(map.get("test") instanceof ConfigurationSection);
		ConfigurationSection section = (ConfigurationSection) map.get("test");
		@SuppressWarnings("unchecked")
		HashMap<String, Object> deepMap = (HashMap<String, Object>) WhiteBox.getInternalState(section, "map");
		assertEquals(2, deepMap.get("test"));
	}
	
	@Test
	public void testGet() {
		MemorySection memorySection = new MemorySection(null);
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = (HashMap<String, Object>) WhiteBox.getInternalState(memorySection, "map");
		assertEquals(0, map.size());
		memorySection.set("test", 2);
		assertEquals(1, map.size());
		assertEquals(2, memorySection.get("test"));
		memorySection.set("local.local", "Hello");
		assertTrue(map.get("local") instanceof ConfigurationSection);
		ConfigurationSection section = (ConfigurationSection) map.get("local");
		assertEquals("Hello", memorySection.get("local.local"));
		assertEquals("Hello", section.get("local"));	
	}

}
