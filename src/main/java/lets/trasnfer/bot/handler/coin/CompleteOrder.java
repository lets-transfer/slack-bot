package lets.trasnfer.bot.handler.coin;

import lombok.Data;

@Data
public class CompleteOrder {
	private String timestamp;
	private String price;
	private String qty;
}
