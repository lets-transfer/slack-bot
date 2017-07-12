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

    def "DustHandler"() {
        given:
        def handler = Mock(MessageHandler)
        dispatcher.addHandler("먼지", handler)

        RequestMessage message = new RequestMessage()
        message.setText("테스트 파라미터")

        DustHandler handle = new DustHandlerTest();

    }
}
