package lets.trasnfer.bot.handler.vo;

import lombok.Data;
import lombok.ToString;

import java.util.Objects;

@ToString
@Data
public class RequestMessage {
	private String type;
	private String url;
	private String channel;
	private String user;
	private String text;
	private String sourceTeam;
	private String team;

	public boolean ofType(String type) {
		return Objects.equals(this.type, type);
	}
}
