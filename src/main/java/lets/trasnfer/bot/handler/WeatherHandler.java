package lets.trasnfer.bot.handler;

import lets.trasnfer.bot.websocket.vo.ResponseMessage;

public class WeatherHandler implements MessageHandler {
	@Override
	public ResponseMessage handle(String message) {
		return new ResponseMessage("날씨정보를 가져옵니다"+message);
	}
}
