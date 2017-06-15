package lets.trasnfer.bot.handler;

import com.squareup.okhttp.OkHttpClient;
import lets.trasnfer.bot.handler.dust.DustResponse;
import lets.trasnfer.bot.websocket.vo.Message;
import lets.trasnfer.bot.websocket.vo.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttpClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
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
        DustResponse dustResponse;

        try {
            dustResponse = connectServer(message);
        } catch (IOException e) {

        }

//        response.setChannel(message.getChannel());
//        response.setType(message.getType());
//        response.setText(dustResponse.getGrade());

        return response;
    }

    private DustResponse connectServer(Message message) throws IOException {
        String[] split = message.getText().split(" ");
        log.info("split Test: {}, {}", split[0], split[1]);

        OkHttpClient client = new OkHttpClient();
        ClientHttpRequestFactory requestFactory = new OkHttpClientHttpRequestFactory(client);
        // Dust server 에 접속
//        connectDustServer();

        //Header 추가
        org.springframework.http.HttpHeaders headers = addHeaderForAPI();
        
        //lat , lon 에 대한 정보를 가져와야 함
        //먼지 위치(lat , lon 위치 정보 필요)
        //가산 위도경도: 126.88382980000006 / 37.4758795
        URI uri = UriComponentsBuilder.newInstance().scheme("http")
                .host(dustapiHost)
                .path("weather/airquality/current")
                .queryParam("lon", 126.88382980000006)
                .queryParam("lat", 37.4758795)
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

    private org.springframework.http.HttpHeaders addHeaderForAPI() {
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.add("appKey", "42a9fc24-5097-37c8-b05b-61919d08aac2");
        return headers;
    }
}
