package lets.trasnfer.bot.handler;

import lets.trasnfer.bot.websocket.vo.Message;
import lets.trasnfer.bot.websocket.vo.ResponseMessage;

/**
 * Created by shinkook.kim on 2017-06-13.
 */
public class DustHandler implements MessageHandler {
    @Override
    public ResponseMessage handle(Message message) {
        ResponseMessage response = new ResponseMessage();

        response.setChannel(message.getChannel());
        response.setType(message.getType());
        response.setText("먼지 정보를 아직은 가져올 수 없어요");

        return response;
    }
}
