package com.gift.consumer.request;

import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class GiftRequestDeserializer implements Deserializer<GiftRequest> {

    private Gson gson = new Gson();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // No additional configuration needed in this case.
    }

    @Override
    public GiftRequest deserialize(String topic, byte[] data) {
        try {
            // Convert the byte array to a String representation of JSON and then parse it into GiftRequest object
            return gson.fromJson(new String(data, "UTF-8"), GiftRequest.class);
        } catch (Exception e) {
            // Handle any deserialization errors here
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void close() {
        // No resources to release in this case.
    }
}
