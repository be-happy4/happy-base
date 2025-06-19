package org.happy.common.config.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class TextualBooleanDeserializer extends JsonDeserializer<Boolean> {

    @Override
    public Boolean deserialize(
            JsonParser parser, DeserializationContext context) throws IOException {
        return Boolean.valueOf(parser.getText());
    }
}