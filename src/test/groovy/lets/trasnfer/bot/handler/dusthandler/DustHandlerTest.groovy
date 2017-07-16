package lets.trasnfer.bot.handler.dusthandler

import lets.trasnfer.bot.handler.MessageDispatcher
import lets.trasnfer.bot.handler.MessageHandler
import lets.trasnfer.bot.handler.dust.DustHandler
import lets.trasnfer.bot.handler.vo.RequestMessage
import lets.trasnfer.bot.handler.vo.ResponseMessage
import spock.lang.Specification

/**
 * Created by shinkook.kim on 2017-07-12.
 * DustHandlerTest
 */
class DustHandlerTest extends Specification {
    MessageDispatcher dispatcher
    boolean flag
    ResponseMessage resp

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

    def "setResponseMessage 호출 시 ResponseMessage 에 return 값 체크"() {

        given:
        RequestMessage message = new RequestMessage()
        lets.trasnfer.bot.handler.dust.DustHandler dust = new DustHandler()
        resp = new ResponseMessage()

        when:
        resp = dust.setResponseMessage(resp, message)

        then:
        resp.getType() == "message"
    }


    def "connectLocationServer 에 parameter 에 대한 return 값 체크"() {
        given:
        DustHandler dust = new DustHandler()

        when:
        flag = dust.connectLocationServer("가산")

        then:
        flag == true
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
