package lets.trasnfer.bot.handler.dust.location;

import lombok.Data;

import java.util.List;

/**
 * Created by shinkook.kim on 2017-06-20.
 */
@Data
public class Channel {
    String totalCount;
    String link;
    String result;
    String generator;
    String pageCount;
    String lastBuildDate;
    List<Item> item;
}
