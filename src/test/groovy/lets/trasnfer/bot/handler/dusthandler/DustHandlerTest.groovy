package lets.trasnfer.bot.handler.dusthandler

import lets.trasnfer.bot.handler.MessageDispatcher
import lets.trasnfer.bot.handler.MessageHandler
import lets.trasnfer.bot.handler.dust.DustHandler
import lets.trasnfer.bot.handler.dust.dustInfo.DustResponse
import lets.trasnfer.bot.handler.vo.RequestMessage
import lets.trasnfer.bot.handler.vo.ResponseMessage
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import spock.lang.Specification

/**
 * Created by shinkook.kim on 2017-07-12.
 * DustHandlerTest
 */
class DustHandlerTest extends Specification {
    MessageDispatcher dispatcher
    boolean flag
    RequestMessage message
    ResponseMessage resp
    DustHandler dust

    def setup() {
        this.dispatcher = new MessageDispatcher()
    }

    def "핸들러 조회,실행 테스트"() {
        given:
        def handler = Mock(MessageHandler)
        dispatcher.addHandler("테스트", handler)

        RequestMessage message = new RequestMessage()
        message.setText("테스트 파라미터")

        when:
        dispatcher.getHandleMessage(message)

        then:
        1 * handler.handle(message) >> new ResponseMessage()
    }

    def "connectDustServer 동작 check"() {

        given:
        dust = new DustHandler()
        dust.connectLocationServer("가산")

        when:
        ResponseEntity<DustResponse> resp = dust.connectDustServer()

        then:
        resp.statusCodeValue == 200

    }

    def "setResponseMessage 호출 시 ResponseMessage 에 return 값 체크"() {

        given:
        message = new RequestMessage()
        dust = new DustHandler()
        resp = new ResponseMessage()

        when:
        resp = dust.setResponseMessage(resp, message)

        then:
        resp.getType() == "message"
    }


    def "connectLocationServer 에 parameter 에 대한 return 값 체크"() {
        given:
        dust = new DustHandler()

        when:
        flag = dust.connectLocationServer("가산")

        then:
        flag == true
    }

    def "checkDustInfoResp response 확인"() {
        given:
        dust = new DustHandler()
        dust.connectLocationServer("가산")
        ResponseEntity<DustResponse> dustResponse = dust.connectDustServer()
        message = new RequestMessage()
        resp = new ResponseMessage()

        when:
        resp = dust.checkDustInfoResp(dustResponse, resp, message)

        then:
        resp.getText().contains("[미세먼지]") == true
    }

    def "makeDustHeader response 확인"() {
        given:
        dust = new DustHandler()

        when:
        HttpHeaders headers = dust.makeDustHeader()

        then:
        headers.getAccept() != null
    }

    def "handler for Dust 테스트 - split length"() {
        given:
        def handler = Mock(lets.trasnfer.bot.handler.dust.DustHandler)
        dispatcher.addHandler("먼지", handler)

        message = new RequestMessage()
        message.setText("먼지 가산")

        when:
        handler.handle(message)

        then:
        1 * message.getText().split(" ").length == 2

    }

    def "handler for Dust 테스트 - connectLocationServer"() {
        given:
        def handler = Mock(lets.trasnfer.bot.handler.dust.DustHandler)
        dispatcher.addHandler("먼지", handler)
        message = new RequestMessage()
        message.setText("먼지 가산")

        when:
        handler.handle(message)

        then:
        handler.connectLocationServer("가산") == true
    }


    def "DustHandler"() {
        given:
        def handler = Mock(MessageHandler)
        dispatcher.addHandler("먼지", handler)
        DustHandler handle = new DustHandler()

        RequestMessage message = new RequestMessage()
        message.setText("먼지 가산")

        when:
        dispatcher.getHandleMessage(message)

        then:
        1 * handler.handle(message) >> print(new ResponseMessage())

    }
}
