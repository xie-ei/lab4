public class WeatherData {

    private String city;
    private String temperature;
    private String windDirection;

    public WeatherData(String city, String temperature, String windDirection) {
        this.city = city;
        this.temperature = temperature;
        this.windDirection = windDirection;
    }

    public String getCity() {
        return city;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getWindDirection() {
        return windDirection;
    }
}