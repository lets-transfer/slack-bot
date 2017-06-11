package lets.trasnfer.bot.websocket.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ResponseMessage {
	private String channel;
	private String type;
	private String text;

	public ResponseMessage(String type, String text) {
		this.type = type;
		this.text = text;
	}

	public ResponseMessage(String text) {
		this.type = "message";
		this.text = text;
	}
}
