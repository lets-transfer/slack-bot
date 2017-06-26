package lets.trasnfer.bot.handler.dust;

import lets.trasnfer.bot.configuration.Configuration;

@Configuration(path = "config/handler/dust.json")
public interface DustApiConfiguration {

//	"dustUrl": "apis.skplanetx.com",
//	"dustPath": "weather/dust",
//	"dustApiKey": "42a9fc24-5097-37c8-b05b-61919d08aac2"
//	"locUrl": "apis.daum.net"
//	"locPath": "local/geo/addr2coord"
//	"locApiKey": "813ccd1408fbef1b58983cfa55d64f82"

	String dustUrl();

	String dustPath();

	String dustApiKey();

	String locUrl();

	String locPath();

	String locApiKey();

}
