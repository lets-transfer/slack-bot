package lets.trasnfer.bot;

import javax.xml.ws.Response;

/**
 * Created by spoon on 2017. 5. 28..
 */
public class CurrencyHandler implements Handler {
	@Override
	public void handle(String message, Response response) {
		// 네이버 환율 API를 이요해서
		// 정보를 조회

		response.setMesage("aa")

	}
}
