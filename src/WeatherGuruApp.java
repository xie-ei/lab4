import java.util.Scanner;

public class WeatherGuruApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("请输入城市名称: ");
        String city = scanner.nextLine();

        WeatherApiService weatherApiService = new WeatherApiService();
        WeatherData weatherData = weatherApiService.getWeatherData(city);

        if (weatherData != null) {
            displayWeatherInfo(weatherData);
        } else {
            System.out.println("获取天气信息失败，请检查城市名称或稍后重试。");
        }

        scanner.close();
    }

    private static void displayWeatherInfo(WeatherData weatherData) {
        System.out.println("城市: " + weatherData.getCity());
        System.out.println("温度: " + weatherData.getTemperature() + "℃");
        System.out.println("风向: " + weatherData.getWindDirection());
    }
}