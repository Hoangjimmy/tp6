package tp6;

import com.google.gson.annotations.SerializedName;

public class WeatherInfo {

    public static class Info {

        @SerializedName("temp")
        public double temp;

        @SerializedName("temp_min")
        public double minTemp;

        @SerializedName("temp_max")
        public double maxTemp;

        @SerializedName("humidity")
        public int humidity;
    }

    @SerializedName("main")
    public Info info = new WeatherInfo.Info();

    @SerializedName("name")
    public String name;

    @SerializedName("cod")
    public int code;

    @SerializedName("message")
    public String message;

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        //res = res.append(name).append(" ").append(info.temp).append(" ").append(info.minTemp).append(" ").append(info.maxTemp).append(" ").append(info.humidity);
        res = res.append("Weather in ").append(this.name).append("\n").append("----------\n")
                .append("T°     : ").append(this.info.temp).append("°C").append("\n")
                .append("T° min     : ").append(this.info.minTemp).append("°C").append("\n")
                .append("T° max     : ").append(this.info.maxTemp).append("°C").append("\n")
                .append("Humidity   : ").append(this.info.humidity).append("%").append("\n");
        return res.toString();
    }
}
