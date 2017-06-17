package lets.trasnfer.bot.handler.movie;

import java.util.List;
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
    private List<Item> item;
}
