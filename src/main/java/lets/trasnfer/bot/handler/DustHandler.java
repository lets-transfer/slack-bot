package lets.trasnfer.bot.handler;

import com.squareup.okhttp.OkHttpClient;
import lets.trasnfer.bot.handler.dust.dustInfo.DustResponse;
import lets.trasnfer.bot.handler.dust.location.LocationResponse;
import lets.trasnfer.bot.websocket.vo.Message;
import lets.trasnfer.bot.websocket.vo.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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
    org.springframework.http.HttpEntity<String> httpEntity;
    LocationResponse locationResponse;

    @Override
    public ResponseMessage handle(Message message) {
        ResponseMessage response = new ResponseMessage();
        ResponseEntity<DustResponse> dustResponse = null;
        try {
            String inputText[] = message.getText().split(" ");
            log.info("inputText size: " + inputText.length);
            if (inputText.length == 1) {
                response = setResponseMessage(response, message);
                response.setText("지역을 입력 하세요");
                return response;
            }

            if (!locationCheck(inputText[1])) {
                log.info("wrong location");
                response = setResponseMessage(response, message);
                response.setText("잘못된 지역을 입력하셨습니다");
                return response;
            } else {
                dustResponse = connectDustServer(message);
                response = setResponseMessage(response, message);
                response.setText("[미세먼지] 수치: " + dustResponse.getBody().getWeather().getDust().get(0).getPm10().getValue() +
                        " / 등급: " + dustResponse.getBody().getWeather().getDust().get(0).getPm10().getGrade() + " 입니다");
            }
        } catch (IOException e) {
        }
        return response;
    }

    private boolean locationCheck(String location) {
        OkHttpClient client = new OkHttpClient();
        ClientHttpRequestFactory requestFactory = new OkHttpClientHttpRequestFactory(client);

        URI locationUri = UriComponentsBuilder.newInstance().scheme("http")
                .host("apis.daum.net")
                .path("local/geo/addr2coord")
                .queryParam("apikey", "xxxxx")
                .queryParam("q", location)
                .queryParam("output", "json")
                .build()
                .encode()
                .toUri();

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        locationResponse = restTemplate.getForObject(locationUri, LocationResponse.class);
        log.info("Location Check: " + locationResponse.toString());

        return Integer.parseInt(locationResponse.getChannel().getTotalCount()) == 0 ? false : true;
    }

    public ResponseMessage setResponseMessage(ResponseMessage response, Message message) {
        response.setType("message");
        response.setChannel(message.getChannel());
        return response;
    }

    private ResponseEntity<DustResponse> connectDustServer(Message message) throws IOException {
        String[] split = message.getText().split(" ");
        ResponseEntity<DustResponse> responseEntity;
        log.info("Command Check: {}, {}", split[0], split[1]);

        OkHttpClient client = new OkHttpClient();
        ClientHttpRequestFactory requestFactory = new OkHttpClientHttpRequestFactory(client);

        //lat , lon 에 대한 정보를 가져와야 함
        URI uri = UriComponentsBuilder.newInstance().scheme("http")
                .host(dustapiHost)
                .path("weather/dust")
                .queryParam("lon", locationResponse.getChannel().getItem().get(0).getLng())
                .queryParam("lat", locationResponse.getChannel().getItem().get(0).getLat())
                .queryParam("version", 1)
                .build()
                .encode()
                .toUri();

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        httpEntity = addHeaderForHttpEntity();

        //RestTemplate 로 HTTP request 전달
        responseEntity = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, DustResponse.class);
        log.info("Dust response: " + responseEntity.toString());
        return responseEntity;
    }

    private org.springframework.http.HttpEntity addHeaderForHttpEntity() {
        httpEntity = new org.springframework.http.HttpEntity<String>(makeDustHeader());
        return httpEntity;
    }

    private org.springframework.http.HttpHeaders makeDustHeader() {
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.set("appKey", "xxxx");
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
