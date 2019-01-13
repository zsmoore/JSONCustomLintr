package objects;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class JSONArray extends org.json.JSONArray implements WrappedObject {

    private final String originatingKey;
    private final WrappedObject parentObject;

    private List<Object> contents;

    public JSONArray(String originatingKey,
                     WrappedObject parentObject,
                     org.json.JSONArray clone) {
        super(clone.toList());
        if (originatingKey == null) {
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
                                                                 org.json.JSONObject.wrap(obj),
                                                                 this))
                        .collect(Collectors.toList());
    }

    @Override
    public JSONObject toJSONObject(org.json.JSONArray names) throws JSONException {
        if(names != null && !names.isEmpty() && !this.isEmpty()) {
            objects.JSONObject jo = new objects.JSONObject(originatingKey, parentObject, null);

            for(int i = 0; i < names.length(); ++i) {
                jo.put(names.getString(i), this.opt(i));
            }
            jo.parseAndReplaceWithWrappers();
            return jo;
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

}
