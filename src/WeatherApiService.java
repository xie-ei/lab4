import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class WeatherApiService {

    private static final String AMAP_KEY = "6fdb517bbe534c5ef4cfaec46d8f873e";
    private static final String WEATHER_API_URL = "https://restapi.amap.com/v3/weather/weatherInfo?key=%s&city=%s";

    public WeatherData getWeatherData(String city) {
        try {
            String encodedCity = URLEncoder.encode(city, "UTF-8");
            String apiUrl = String.format(WEATHER_API_URL, AMAP_KEY, encodedCity);

            String jsonResponse = getApiResponse(apiUrl);
            return parseWeatherData(jsonResponse);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getApiResponse(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        reader.close();
        return response.toString();
    }

    private WeatherData parseWeatherData(String jsonResponse) {
        try {
            //System.out.println("API响应: " + jsonResponse);

            // 检查是否包含 "status":"1"
            if (jsonResponse.contains("\"status\":\"1\"")) {
                // 找到 "lives" 的起始位置
                int livesIndex = jsonResponse.indexOf("\"lives\":[");

                if (livesIndex != -1) {
                    // 截取 "lives" 对应的 JSON 部分
                    String livesJson = jsonResponse.substring(livesIndex);

                    // 找到 "}" 的位置，截取 "lives" JSON 对象
                    int endIndex = livesJson.indexOf("}");
                    if (endIndex != -1) {
                        livesJson = livesJson.substring(0, endIndex + 1);

                        // 创建 Map 对象表示 "lives" JSON 对象
                        Map<String, Object> livesObject = parseJsonObject(livesJson);

                        // 从 "lives" 对象中获取需要的数据
                        String city = String.valueOf(livesObject.get("city"));
                        String temperature = String.valueOf(livesObject.get("temperature"));
                        String windDirection = String.valueOf(livesObject.get("winddirection"));

                        return new WeatherData(city, temperature, windDirection);
                    }
                }
            }

            System.out.println("未找到天气信息或格式不匹配。");
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取响应失败：" + jsonResponse);
            return null;
        }
    }

    // 解析JSON对象
    private Map<String, Object> parseJsonObject(String json) {
        Map<String, Object> result = new HashMap<>();

        // 以","分割键值对
        String[] keyValuePairs = json.split(",");
        for (String pair : keyValuePairs) {
            // 以":"分割键和值
            String[] keyValue = pair.split(":");
            if (keyValue.length == 2) {
                String key = keyValue[0].replaceAll("\"", "").trim();
                String value = keyValue[1].replaceAll("\"", "").trim();
                result.put(key, value);
            }
        }

        return result;
    }
}