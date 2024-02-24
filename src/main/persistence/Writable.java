package persistence;

import org.json.JSONObject;

public interface Writable {
    // CITATION: code taken from provided sample, JasonSerializationDemo.

    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
