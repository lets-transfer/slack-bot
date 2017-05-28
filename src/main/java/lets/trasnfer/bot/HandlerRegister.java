package lets.trasnfer.bot;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by spoon on 2017. 5. 28..
 */
public class HandlerRegister {
	Map<String, Handler> handlers = new HashMap<>();

	public void addHandler() {
		handlers.put("날씨", new WeatherHandler());
		handlers.put("환율", new CurrencyHandler());
	}


	public void getHandleMessage(String text) {
		String[] split = text.split(" "); // 환율 USD, 날씨 서울

		Handler handler = handlers.get(split[0]);

		handler.handle(text);  //

	}
}
