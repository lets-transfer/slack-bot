package lets.trasnfer.bot.handler;

import lets.trasnfer.bot.websocket.vo.Message;

public class WeatherHandler implements MessageHandler {
	@Override
	public Message handle(Message message) {
		Message response = new Message();

		response.setChannel(message.getChannel());
		response.setType(message.getType());
		response.setText("날씨 정보를 아직은 가져올 수 없어요");

		return response;
	}
}
