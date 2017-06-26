package lets.trasnfer.bot.handler;

import lets.trasnfer.bot.handler.vo.RequestMessage;
import lets.trasnfer.bot.handler.vo.ResponseMessage;

public interface MessageHandler {
	ResponseMessage handle(RequestMessage message);
}
