package com.zachary_moore.objects;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class JSONArray extends org.json.JSONArray implements WrappedObject {

    private final String originatingKey;
    private final WrappedObject parentObject;

    private List<Object> contents;

    JSONArray(String originatingKey,
              WrappedObject parentObject,
              org.json.JSONArray clone) {
        super(clone != null ? clone.toList() : null);
        if (originatingKey == null && parentObject != null) {
            this.originatingKey = parentObject.getOriginatingKey();
        } else {
            this.originatingKey = originatingKey;
        }
        this.parentObject = parentObject;
    }

    @Override
    public String getOriginatingKey() {
        return originatingKey;
    }

    @Override
    public WrappedObject getParentObject() {
        return parentObject;
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public void parseAndReplaceWithWrappers() {
        contents = StreamSupport.stream(this.spliterator(), false)
                        .map(obj ->
                            WrappedObjectHelper.getWrappedObject(this.originatingKey,
                                    this, org.json.JSONObject.wrap(obj)
                            ))
                        .collect(Collectors.toList());
    }

    @Override
    public JSONObject toJSONObject(org.json.JSONArray names) throws JSONException {
        if(names != null && !names.isEmpty() && !this.isEmpty()) {
            org.json.JSONObject jo = new org.json.JSONObject();

            for(int i = 0; i < names.length(); ++i) {
                jo.put(names.getString(i), this.opt(i));
            }

            com.zachary_moore.objects.JSONObject retObj = new com.zachary_moore.objects.JSONObject(originatingKey, parentObject, jo);
            retObj.parseAndReplaceWithWrappers();
            return retObj;
        } else {
            return null;
        }
    }

    @Override
    public List<Object> toList() {
        if (contents == null) {
            parseAndReplaceWithWrappers();
        }
        return contents;
    }

    @Override
    public Object get(int index) {
        if (contents == null) {
            parseAndReplaceWithWrappers();
        }
        if (index < 0 || index > contents.size()) {
            throw new IndexOutOfBoundsException();
        }
        return contents.get(index);
    }
}
