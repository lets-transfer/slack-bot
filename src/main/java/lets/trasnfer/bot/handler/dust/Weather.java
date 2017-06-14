package lets.trasnfer.bot.handler.dust;

/**
 * Created by shinkook.kim on 2017-06-14.
 */
public class Weather {
    class AirQuality {
        class Current {
            class Station {
                String name;
                String owner;
                String latitude;
                String longitude;
                String network;
                String timeObservation;
            }

            class so2 {
                String value;
                String grade;
            }

            class co {
                String value;
                String grade;
            }

            class o3 {
                String value;
                String grade;
            }

            class no2 {
                String value;
                String grade;
            }

            class pm10 {
                String value;
                String grade;
            }

            class pm25 {
                String value;
                String grade;
            }

            class khai {
                String value;
                String grade;
            }
        }
    }
}
