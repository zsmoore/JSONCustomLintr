package com.zachary_moore.objects;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.json.JSONException;

public class JSONObject extends org.json.JSONObject implements WrappedObject {

    @Getter
    private final String originatingKey;

    @Getter
    private final WrappedObject parentObject;

    private final org.json.JSONObject clonedObject;

    JSONObject(String originatingKey,
               WrappedObject parentObject,
               org.json.JSONObject clone) {
        super(clone != null ? clone.toMap() : null);
        if (originatingKey == null && parentObject != null) {
            this.originatingKey = parentObject.getOriginatingKey();
        } else {
            this.originatingKey = originatingKey;
        }
        this.parentObject = parentObject;
        this.clonedObject = clone;
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public void parseAndReplaceWithWrappers() {
        clonedObject.toMap().entrySet().forEach(entry ->
            this.put(entry.getKey(),
                    WrappedObjectHelper.getWrappedObject(entry.getKey(), this, wrap(entry.getValue())))
        );
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> results = new HashMap<>();
        for (Map.Entry<String, Object> entry : this.entrySet()) {
            Object value;
            if (entry.getValue() == null || NULL.equals(entry.getValue())) {
                value = null;
            } else if (entry.getValue() instanceof WrappedObject) {
                value = entry.getValue();
            } else {
                value = WrappedObjectHelper.getWrappedObject(entry.getKey(), this, org.json.JSONObject.wrap(entry.getValue()));
            }
            results.put(entry.getKey(), value);
        }
        return results;
    }

    @Override
    public Object get(String key) throws JSONException {
        Object object = super.get(key);
        if (!(object instanceof WrappedObject)) {
            return WrappedObjectHelper.getWrappedObject(key, this, object);
        }
        return object;
    }
}
