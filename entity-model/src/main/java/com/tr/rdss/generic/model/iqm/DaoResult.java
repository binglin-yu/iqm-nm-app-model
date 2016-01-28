package com.tr.rdss.generic.model.iqm;

import java.util.List;

public class DaoResult {

    private String key;
    private String key_value;

    private Entity entity;
    private boolean isSucc;
    private String message;
    private String requestId;
    private List<String> messages;

	/**
     * constructor *
     */
    public DaoResult() {
    }

    public DaoResult(boolean isSucc, String message) {
        this.isSucc = isSucc;
        this.message = message;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey_value() {
        return key_value;
    }

    public void setKey_value(String key_value) {
        this.key_value = key_value;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public boolean isSucc() {
        return isSucc;
    }

    public void setSucc(boolean isSucc) {
        this.isSucc = isSucc;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
}
