package lets.trasnfer.bot.handler

import lets.trasnfer.bot.handler.vo.RequestMessage
import lets.trasnfer.bot.handler.vo.ResponseMessage
import spock.lang.Specification

class MessageDispatcherTest extends Specification {

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

}
