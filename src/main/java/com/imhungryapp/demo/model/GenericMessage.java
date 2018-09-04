package com.imhungryapp.demo.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * This class implements a custom message header creation
 * This is a temporary solution until spring implements native kafka custom headers creation
 */
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class GenericMessage implements Serializable {

    protected Map<String, Object> headers;

    @JsonProperty("resource_id")
    protected String resourceId;

    public void addHeaders(Map headers) {
        if (this.headers == null) {
            this.headers = new HashMap<>();
        }
        this.headers.putAll(headers);
    }

    public interface GenericMessageBuilderInterface<T> {

        T setHeaders(final Map<String, Object> headers);

        T addHeader(String key, Object value);

        T setResourceId(final String resourceId);
    }

	public Map<String, Object> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, Object> headers) {
		this.headers = headers;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
    
    
    
}
