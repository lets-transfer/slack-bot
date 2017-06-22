package lets.trasnfer.bot.handler.movie;

import com.squareup.okhttp.OkHttpClient;
import lets.trasnfer.bot.handler.MessageHandler;
import lets.trasnfer.bot.configuration.ConfigurationLoader;
import lets.trasnfer.bot.handler.movie.MovieApiConfiguration;
import lets.trasnfer.bot.handler.movie.MovieResponse;
import lets.trasnfer.bot.handler.vo.RequestMessage;
import lets.trasnfer.bot.handler.vo.ResponseMessage;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttpClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class MovieInfoHandler implements MessageHandler {
	private final MovieApiConfiguration configuration;

	public MovieInfoHandler() {
		this.configuration = ConfigurationLoader.load(MovieApiConfiguration.class);
	}

    @Override
    public ResponseMessage handle(RequestMessage message) {
        String[] split = message.getText().split(" ");

        OkHttpClient client = new OkHttpClient();
        ClientHttpRequestFactory requestFactory = new OkHttpClientHttpRequestFactory(client);

        URI uri = UriComponentsBuilder.newInstance().scheme("https")
				.host(configuration.host())
				.path(configuration.path())
				.queryParam("apikey", configuration.apiKey())
                .queryParam("q", split[1])
                .queryParam("output", "json")
                .build()
                .encode()
                .toUri();

        RestTemplate restTemplate = new RestTemplate(requestFactory);
//        restTemplate.setMessageConverters(Arrays.asList(new MarshallingHttpMessageConverter()));
        MovieResponse response = restTemplate.getForObject(uri, MovieResponse.class);

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setType("message");
        responseMessage.setChannel(message.getChannel());
        responseMessage.setText(response.getChannel().getTitle());


        return responseMessage;
    }
}
