package lets.trasnfer.bot.handler.hello;

import lets.trasnfer.bot.handler.MessageHandler;
import lets.trasnfer.bot.handler.vo.RequestMessage;
import lets.trasnfer.bot.handler.vo.ResponseMessage;


public class HelloHandler implements MessageHandler {
	@Override
	public ResponseMessage handle(RequestMessage message) {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setChannel(message.getChannel());
		responseMessage.setType(message.getType());

		responseMessage.setText("안녕하세요");

		return responseMessage;
	}
}
