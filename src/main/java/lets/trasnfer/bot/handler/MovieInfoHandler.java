package lets.trasnfer.bot.handler;

import com.squareup.okhttp.OkHttpClient;
import lets.trasnfer.bot.handler.movie.MovieResponse;
import lets.trasnfer.bot.websocket.vo.Message;
import lets.trasnfer.bot.websocket.vo.ResponseMessage;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttpClientHttpRequestFactory;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.Arrays;

public class MovieInfoHandler implements MessageHandler {
    private final String apiHost = "apis.daum.net";
    private String apiKey = "abcd";

    @Override
    public ResponseMessage handle(Message message) {
        String[] split = message.getText().split(" ");

        OkHttpClient client = new OkHttpClient();
        ClientHttpRequestFactory requestFactory = new OkHttpClientHttpRequestFactory(client);

        URI uri = UriComponentsBuilder.newInstance().scheme("https")
                .host(apiHost)
                .path("/contents/movie")
                .queryParam("apikey", apiKey)
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
