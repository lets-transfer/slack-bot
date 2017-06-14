package lets.trasnfer.bot.handler;

import com.squareup.okhttp.OkHttpClient;
import lets.trasnfer.bot.handler.dust.DustResponse;
import lets.trasnfer.bot.websocket.vo.Message;
import lets.trasnfer.bot.websocket.vo.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttpClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Created by shinkook.kim on 2017-06-13.
 */
@Slf4j
public class DustHandler implements MessageHandler {

    String dustapiHost = "apis.skplanetx.com";

    @Override
    public ResponseMessage handle(Message message) {
        ResponseMessage response = new ResponseMessage();

        DustResponse dustResponse = connectServer(message);

        response.setChannel(message.getChannel());
        response.setType(message.getType());
        response.setText(dustResponse.getGrade());

        return response;
    }

    private DustResponse connectServer(Message message) {
        String[] split = message.getText().split(" ");
        log.info("split Test: {}, {}", split[0], split[1]);

        OkHttpClient client = new OkHttpClient();
        ClientHttpRequestFactory requestFactory = new OkHttpClientHttpRequestFactory(client);

        //lat , lon 에 대한 정보를 가져와야 함
        //먼지 위치(lat , lon 위치 정보 필요)
        //request:     http://apis.skplanetx.com/weather/airquality/current?lon=126&lat=37&version=1
        //dusthandler: http://apis.skplanetx.com/weather/airquality/current?lon=126&lat=37&version=1
        URI uri = UriComponentsBuilder.newInstance().scheme("http")
                .host(dustapiHost)
                .path("weather/airquality/current")
                .queryParam("lon", 126)
                .queryParam("lat", 37)
                .queryParam("version", 1)
                .build()
                .encode()
                .toUri();

        log.info("uri print: " + uri.toString());

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        DustResponse dustResponse = restTemplate.getForObject(uri, DustResponse.class);
//        DustResponse dustResponse = new DustResponse();

        return dustResponse;
    }
}
