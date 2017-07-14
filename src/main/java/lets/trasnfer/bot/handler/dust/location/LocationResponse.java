package lets.trasnfer.bot.handler.dust.location;

import lombok.Data;

/**
 * Created by shinkook.kim on 2017-06-20.
 * location response class
 */
@Data
public class LocationResponse {
    Channel channel;

    public boolean checkLocCnt(){
    	return Integer.parseInt(channel.getTotalCount()) != 0 ? true:false;
	}
}
