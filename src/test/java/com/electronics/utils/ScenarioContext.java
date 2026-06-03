package com.electronics.utils;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Thread-safe context holder to share state between Cucumber step definitions.
 */
public class ScenarioContext {

    private final Map<String, Object> context = new HashMap<>();

    public void set(String key, Object value) {
        context.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) context.get(key);
    }

    public boolean contains(String key) {
        return context.containsKey(key);
    }

    public void clear() {
        context.clear();
    }

    // ── Convenience shortcuts ──────────────────────────────────────────────

    public void setResponse(Response response) {
        set("response", response);
    }

    public Response getResponse() {
        return get("response");
    }

    public void setToken(String token) {
        set(Constants.CONTEXT_TOKEN, token);
    }

    public String getToken() {
        return get(Constants.CONTEXT_TOKEN);
    }
}
