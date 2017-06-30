package lets.trasnfer.bot.handler.dust.dustInfo;

import lombok.Data;

import java.util.List;

/**
 * Created by shinkook.kim on 2017-06-14.
 */
@Data
public class DustResponse {
    Common common;
    List<Dust> dust;
    Pm10 pm10;
    Result result;
    Station station;
    Weather weather;

    public Pm10 getDustValue(){
		return getWeather().getDust().get(0).getPm10();
	}
    public Pm10 getDustGrade(){
		return getWeather().getDust().get(0).getPm10();
	}
}
