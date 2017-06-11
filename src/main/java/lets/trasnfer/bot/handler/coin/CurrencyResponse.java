package lets.trasnfer.bot.handler.coin;

import lombok.Data;

import java.util.List;

@Data
public class CurrencyResponse {
	private String errorCode;
	private String timestamp;
	private String result;
	private String currency;
	private List<CompleteOrder> completeOrders;
}
