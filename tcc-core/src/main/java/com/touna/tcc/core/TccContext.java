package com.touna.tcc.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenchaojian on 17/6/7.
 * extension for future use
 */
public class TccContext implements Serializable{
    private final Map<String, Object> attachments = new HashMap<String, Object>();

    public void setAttachment(String key, Object value) {
        if (value == null) {
            attachments.remove(key);
        } else {
            attachments.put(key, value);
        }
    }

    public Object getAttachment(String key){
        return attachments.get(key);
    }


    public Map<String, Object> getAttachments() {
        return attachments;
    }

}
