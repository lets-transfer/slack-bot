package lets.trasnfer.bot.handler;

import com.squareup.okhttp.OkHttpClient;
import lets.trasnfer.bot.handler.coin.CurrencyResponse;
import lets.trasnfer.bot.websocket.vo.Message;
import lets.trasnfer.bot.websocket.vo.ResponseMessage;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttpClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.text.NumberFormat;

public class CoinCurrencyHandler implements MessageHandler {
	private final String apiHost = "api.coinone.co.kr";

	@Override
	public ResponseMessage handle(Message message) {
		String[] split = message.getText().split(" ");

		OkHttpClient client = new OkHttpClient();
		ClientHttpRequestFactory requestFactory = new OkHttpClientHttpRequestFactory(client);

		URI uri = UriComponentsBuilder.newInstance().scheme("https")
				.host(apiHost)
				.path("/trades")
				.queryParam("currency", split[1])
				.queryParam("period", "hour")
				.build()
				.encode()
				.toUri();

		RestTemplate restTemplate = new RestTemplate(requestFactory);
		CurrencyResponse response = restTemplate.getForObject(uri, CurrencyResponse.class);

		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setType("message");
		responseMessage.setChannel(message.getChannel());

		NumberFormat numberFormat = NumberFormat.getInstance();
		String price = response.getCompleteOrders().get(response.getCompleteOrders().size() - 1).getPrice();

		responseMessage.setText("현재 "+split[1]+" 가격은" +numberFormat.format(Long.parseLong(price))+" 입니다.");

		return responseMessage;
	}
}
