package lets.trasnfer.bot.handler.shortenurl;

import lets.trasnfer.bot.handler.vo.ResponseMessage;
import lombok.Getter;

@Getter
class ShortenUrlResponse extends ResponseMessage {

	private String id;

	private String kind;

	private String longUrl;

	@Override
	public String getText() {
		return longUrl + " 의 단축 URL 은 " + id + " 입니다.";
	}
}
