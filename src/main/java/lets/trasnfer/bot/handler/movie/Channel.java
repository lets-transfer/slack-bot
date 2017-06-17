package lets.trasnfer.bot.handler.movie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Data;

@Data
public class Channel {
    private String title;
    private String description;
    private String generator;
    private String totalCount;
    private String result;
    private String page;
    private String q;

    private Map<String, Object> dynamicProperties = new HashMap<>();

    @JsonAnyGetter
    public Map<String,Object> any() {
        return dynamicProperties;
    }

    @JsonAnySetter
    public void set(String name, Object value) {
        dynamicProperties.put(name, value);
    }
}
