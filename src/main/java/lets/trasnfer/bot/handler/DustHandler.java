package lets.trasnfer.bot.handler;

import com.squareup.okhttp.OkHttpClient;
import lets.trasnfer.bot.websocket.vo.Message;
import lets.trasnfer.bot.websocket.vo.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
    org.springframework.http.HttpEntity httpEntity;

    @Override
    public ResponseMessage handle(Message message) {
        ResponseMessage response = new ResponseMessage();
        //DustResponse dustResponse;
        ResponseEntity<String> dustResponse;
        try {
            dustResponse = connectServer(message);
        } catch (IOException e) {

        }

        return response;
    }

    private ResponseEntity<String> connectServer(Message message) throws IOException {
        String[] split = message.getText().split(" ");

        log.info("split Test: {}, {}", split[0], split[1]);

        OkHttpClient client = new OkHttpClient();
        ClientHttpRequestFactory requestFactory = new OkHttpClientHttpRequestFactory(client);

        //Header 추가 app key
        httpEntity = addHeaderForAPI();

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
        //DustResponse dustResponse = restTemplate.exchange(uri, HttpMethod.GET,httpEntity, DustResponse.class);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);

        log.info("response: " + responseEntity.toString());
        return responseEntity;
    }

    private org.springframework.http.HttpEntity addHeaderForAPI() {

        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.set("appKey", "42a9fc24-5097-37c8-b05b-61919d08aac2");
        httpEntity = new org.springframework.http.HttpEntity(headers);

        return httpEntity;
    }
}
