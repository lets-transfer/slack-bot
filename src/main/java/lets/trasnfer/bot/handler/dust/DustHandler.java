package lets.trasnfer.bot.handler.dust;

import com.squareup.okhttp.OkHttpClient;
import lets.trasnfer.bot.configuration.ConfigurationLoader;
import lets.trasnfer.bot.handler.MessageHandler;
import lets.trasnfer.bot.handler.dust.dustInfo.DustResponse;
import lets.trasnfer.bot.handler.dust.location.LocationResponse;
import lets.trasnfer.bot.handler.vo.RequestMessage;
import lets.trasnfer.bot.handler.vo.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttpClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

/**
 * Created by shinkook.kim on 2017-06-13.
 * DustHandler main
 */
@Slf4j
public class DustHandler implements MessageHandler {


	private LocationResponse locationResponse;
	private DustApiConfiguration dustApiConfiguration;
	private RestClientException errCause = null;

	public DustHandler() {
		this.dustApiConfiguration = ConfigurationLoader.load(DustApiConfiguration.class);
	}

	@Override
	public ResponseMessage handle(RequestMessage message) {
		ResponseMessage response = new ResponseMessage();
		ResponseEntity<DustResponse> dustResponse;
		String[] inputText = message.getText().split(" ");

		try {
			log.info("inputText size: " + inputText.length);
			if (inputText.length == 1) {
				response = setResponseMessage(response, message);
				response.setText("지역을 입력 하세요");
				return response;
			}

			if (!connectLocationServer(inputText[1])) {
				response = setResponseMessage(response, message);
				response.setText("잘못된 지역을 입력하셨습니다");
				return response;
			} else {

				try {
					dustResponse = connectDustServer();
					response = checkDustInfoResp(dustResponse, response, message);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		} catch (Exception e) {
			response = new ResponseMessage();
			response.setText(e.getMessage());
		}
		return response;
	}

	private ResponseMessage checkDustInfoResp(ResponseEntity<DustResponse> dustResponse, ResponseMessage response, RequestMessage message) {
		if (dustResponse == null) {
			response = setResponseMessage(response, message);
			response.setText("Server Error: " + errCause.getMessage());
		} else {
			response = setResponseMessage(response, message);
			response.setText("[미세먼지] 수치: " + dustResponse.getBody().getDustValue().getValue() +
				" / 등급: " + dustResponse.getBody().getDustGrade().getGrade() + " 입니다");
		}
		return response;
	}

	private boolean connectLocationServer(String location) {
		OkHttpClient client = new OkHttpClient();
		ClientHttpRequestFactory requestFactory = new OkHttpClientHttpRequestFactory(client);

		URI locationUri = UriComponentsBuilder.newInstance().scheme(dustApiConfiguration.scheme())
			.host(dustApiConfiguration.locUrl())
			.path(dustApiConfiguration.locPath())
			.queryParam("apikey", dustApiConfiguration.locApiKey())
			.queryParam("q", location)
			.queryParam("output", "json")
			.build()
			.encode()
			.toUri();

		RestTemplate restTemplate = new RestTemplate(requestFactory);
		locationResponse = restTemplate.getForObject(locationUri, LocationResponse.class);
		log.info("Location response: " + locationResponse.toString());

		if (Integer.parseInt(locationResponse.getChannel().getTotalCount()) != 0)
			return true;
		else
			return false;
	}

	private ResponseMessage setResponseMessage(ResponseMessage response, RequestMessage message) {
		response.setType("message");
		response.setChannel(message.getChannel());
		return response;
	}

	private ResponseEntity<DustResponse> connectDustServer() throws IOException {
		ResponseEntity<DustResponse> responseEntity = null;
		org.springframework.http.HttpEntity<String> httpEntity;

		OkHttpClient client = new OkHttpClient();
		ClientHttpRequestFactory requestFactory = new OkHttpClientHttpRequestFactory(client);

		URI uri = UriComponentsBuilder.newInstance().scheme(dustApiConfiguration.scheme())
			.host(dustApiConfiguration.dustUrl())
			.path(dustApiConfiguration.dustPath())
			.queryParam("lon", locationResponse.getChannel().getItem().get(0).getLng())
			.queryParam("lat", locationResponse.getChannel().getItem().get(0).getLat())
			.queryParam("version", 1)
			.build()
			.encode()
			.toUri();

		RestTemplate restTemplate = new RestTemplate(requestFactory);
		httpEntity = addHeaderForHttpEntity();

		//RestTemplate 로 HTTP request 전달
		try {
			responseEntity = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, DustResponse.class);
			log.info("Dust response: " + responseEntity.toString());
		} catch (RestClientException e) {
			errCause = e;
			log.info("Occur error");
		}
		return responseEntity;
	}

	private org.springframework.http.HttpEntity addHeaderForHttpEntity() {
		return new org.springframework.http.HttpEntity<>(makeDustHeader());
	}

	private org.springframework.http.HttpHeaders makeDustHeader() {
		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
		headers.set("appKey", dustApiConfiguration.dustApiKey());
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
}
