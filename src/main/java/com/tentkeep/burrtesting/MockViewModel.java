package com.tentkeep.burrtesting;

import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.Map;

public class MockViewModel {

    private Map<String, Object> attributes = new HashMap<>();

    public void addAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public void populateModel(Model model) {
        for (Map.Entry<String, Object> attribute : attributes.entrySet()) {
            model.addAttribute(attribute.getKey(), attribute.getValue());
        }
        attributes = new HashMap<>();
    }

    @Override
    public String toString() {
        return "MockViewModel{" +
                "attributes=" + attributes +
                '}';
    }
}
