package lets.trasnfer.bot.websocket.vo;

import java.util.Objects;

public class Message {

	private String type;
	private String text;

	public boolean ofType(String type) {
		return Objects.equals(this.type, type);
	}

	public String getText() {
		return text;
	}

}
