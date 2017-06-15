package lets.trasnfer.bot.handler;

import com.squareup.okhttp.OkHttpClient;
import lets.trasnfer.bot.handler.movie.MovieResponse;
import lets.trasnfer.bot.websocket.vo.Message;
import lets.trasnfer.bot.websocket.vo.ResponseMessage;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttpClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;

/**
 * Created by heeyeon.nah on 2017-06-15.
 */
public class MovieInfoHandler implements MessageHandler {
    private final String apiHost = "apis.daum.net";
    private String apiKey = "abcdef";

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
                .build()
                .encode()
                .toUri();

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        MovieResponse response = restTemplate.getForObject(uri, MovieResponse.class);

        System.out.println(response.getChannel().getQ());
//        ResponseMessage responseMessage = new ResponseMessage();
//        responseMessage.setType("message");
//        responseMessage.setChannel(message.getChannel());

        return null;
    }
}
