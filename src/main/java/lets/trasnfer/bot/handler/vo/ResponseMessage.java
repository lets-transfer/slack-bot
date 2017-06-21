package lets.trasnfer.bot.handler.vo;

import lombok.Data;

@Data
public class ResponseMessage {
	private String channel;
	private String type;
	private String text;
}
