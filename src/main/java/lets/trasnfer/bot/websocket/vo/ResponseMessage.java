package lets.trasnfer.bot.websocket.vo;

import lombok.Data;

@Data
public class ResponseMessage {
	private String channel;
	private String type;
	private String text;
}
