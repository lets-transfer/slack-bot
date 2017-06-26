package lets.trasnfer.bot.handler.shortenurl;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.squareup.okhttp.OkHttpClient;
import lets.trasnfer.bot.configuration.ConfigurationLoader;
import lets.trasnfer.bot.handler.MessageHandler;
import lets.trasnfer.bot.handler.vo.RequestMessage;
import lets.trasnfer.bot.handler.vo.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttpClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ShortenUrlHandler implements MessageHandler {

	private final GoogleApiConfiguration configuration;

	public ShortenUrlHandler() {
		this.configuration = ConfigurationLoader.load(GoogleApiConfiguration.class);
	}

	@Override
	public ResponseMessage handle(RequestMessage request) {
		log.debug("Shorten Url: \n"
			+ "  type: {}\n"
			+ "  channel: {}\n"
			+ "  text: {}\n", request.getType(), request.getChannel(), request.getText());

		ResponseMessage response = null;
		try {
			String requestUrl = getRequestUrl(request);
			response = createRestTemplate(requestUrl);
		} catch (Exception e) {
			response = new ResponseMessage();
			response.setText(e.getMessage());
		} finally {
			assert response != null;
			response.setType(request.getType());
			response.setChannel(request.getChannel());
		}
		return response;
	}

	private String getRequestUrl(RequestMessage request) throws IllegalRequestException {
		String[] parameters = request.getText().split(" ");
		Pattern pattern = Pattern.compile("<([http|https].*)>");

		Matcher matcher = pattern.matcher(parameters[1]);
		if (matcher.find()) {
			return matcher.group(1);
		}
		throw new IllegalRequestException("Invalid Request: " + parameters[1]);
	}

	private ShortenUrlResponse createRestTemplate(String url) throws RequestFailedException {
		OkHttpClient client = new OkHttpClient();
		ClientHttpRequestFactory requestFactory = new OkHttpClientHttpRequestFactory(client);

		ShortenUrlRequest request = new ShortenUrlRequest(url);
		RestTemplate restTemplate = new RestTemplate(requestFactory);

		MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
		jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		restTemplate.setMessageConverters(Collections.singletonList(jsonHttpMessageConverter));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ShortenUrlRequest> entity = new HttpEntity<>(request, headers);

		return restTemplate.postForObject(createUri(),
				entity,
				ShortenUrlResponse.class);
	}

	private URI createUri() {
		return UriComponentsBuilder.newInstance()
			.scheme(configuration.scheme())
			.host(configuration.url())
			.path(configuration.path())
			.queryParam("key", configuration.apiKey())
			.build()
			.encode()
			.toUri();
	}

}
