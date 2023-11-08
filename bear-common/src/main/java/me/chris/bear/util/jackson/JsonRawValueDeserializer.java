package me.chris.bear.util.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * @author Christopher
 * @date 2023/11/8
 **/
public class JsonRawValueDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException {
        TreeNode tree = jp.getCodec().readTree(jp);
        return tree.toString();
    }
}
