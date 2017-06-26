package lets.trasnfer.bot.handler;

import lets.trasnfer.bot.handler.vo.RequestMessage;
import lets.trasnfer.bot.handler.vo.ResponseMessage;

public class WeatherHandler implements MessageHandler {
	@Override
	public ResponseMessage handle(RequestMessage message) {
		// 1. api를 통해서 날씨 정보 가져온다
		// 2. 가져온 정보를 알맞게 parsing해서 리턴

		ResponseMessage response = new ResponseMessage();

		response.setChannel(message.getChannel());
		response.setType(message.getType());
		response.setText("날씨 정보를 아직은 가져올 수 없어요");

		return response;
	}
}
