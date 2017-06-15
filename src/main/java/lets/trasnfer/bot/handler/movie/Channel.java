package lets.trasnfer.bot.handler.movie;

import java.util.List;
import lombok.Data;

/**
 * Created by heeyeon.nah on 2017-06-15.
 */
@Data
public class Channel {
    private String title;
    private String description;
    private String generator;
    private String totalCount;
    private String result;
    private String pageno;
    private String q;
//    private List<Item> items;
}
