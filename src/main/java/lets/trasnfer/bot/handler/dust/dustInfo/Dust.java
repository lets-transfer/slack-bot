package lets.trasnfer.bot.handler.dust.dustInfo;

import lombok.Data;

/**
 * Created by shinkook.kim on 2017-06-19.
 */
@Data
public class Dust {
    Station station;
    String name;
    String id;
    String latitude;
    String longitude;
    String timeObservation;

    Pm10 pm10;
}
