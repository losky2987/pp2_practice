package losky2987.pp2_practice.helper;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is design for make controller code cleaner.
 * */


public class ModelAttributeManager {
    private final Map<String, Object> attributes = new HashMap<String, Object>();

    public ModelAttributeManager() {}

    public ModelAttributeManager(Map<String, Object> attributes) {
        this.attributes.putAll(attributes);
    }

    public void initMap(Map<String, Object> attributes) {
        this.attributes.putAll(attributes);
    }

    public void addAttribute(String attribute) {
        this.attributes.put(attribute, null);
    }

    public Object getAttribute(String attribute) {
        return this.attributes.get(attribute);
    }

    public void updateAttribute(String attribute, Object value) {
        this.attributes.put(attribute, value);
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public boolean isAttributeExists(String attribute) {
        return this.attributes.containsKey(attribute);
    }
}
