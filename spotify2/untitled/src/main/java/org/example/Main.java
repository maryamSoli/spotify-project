package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import java.util.Scanner;


public class Main {
    // Copy your API-KEY here
    public final static String apiKey = "6e0a85da86de4dfb910141736232602";
    // TODO: Write main function
    public static void main(String[] args)  {

        Scanner input = new Scanner(System.in);
        //AudioDevice audioDevice = FactoryRegistry.systemRegistry().createAudioDevice();

        String City = "";

        // in this while true the program will get a city as input until the user enters 'o'

        while (true){

            System.out.println("enter the city plz and press 'o' to get out ");

            City = input.nextLine();

            if(City.equals("o")){

                break;
            }

            String Response = getWeatherData(City);

            if (Response == null){

                System.out.println("invalid city please enter a valid city name and try again");
            }

            else{

                System.out.println("TEMPERATURE : " + getTemperature(Response) + " c");
                System.out.println("HUMIDITY : " + getHumidity(Response));
                System.out.println("WIND SPEED : " + WindSpeed(Response)+ " mph");
                System.out.println("WIND DIRECTION : " + windDirection(Response));

            }


        }


    }

    /**
     * Retrieves weather data for the specified city.
     *
     * @param city the name of the city for which weather data should be retrieved
     * @return a string representation of the weather data, or null if an error occurred
     */
    public static String getWeatherData(String city) {

        try {

            URL url = new URL("http://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=" + city);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder stringBuilder = new StringBuilder();

            String line;

            while ((line = reader.readLine()) != null) {

                stringBuilder.append(line);
            }
            reader.close();

            return stringBuilder.toString();

        }

        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    // TODO: Write getTemperature function returns celsius temperature of city by given json string
    public static double getTemperature(String weatherJson){

        double temperature = 0.0;
        JSONObject obj = new JSONObject(weatherJson);
        JSONObject time = obj.getJSONObject("current");
        temperature = time.getDouble("temp_c");

        return temperature;
    }

    // TODO: Write getHumidity function returns humidity percentage of city by given json string
    public static int getHumidity(String weatherJson){

        int humidity = 0;
        JSONObject obj = new JSONObject(weatherJson);
        JSONObject time = obj.getJSONObject("current");
        humidity = time.getInt("humidity");

        return humidity;

    }

    //Windspeed function returns wind speed of city by given json string

    public static double WindSpeed(String weatherJson){

        double speed = 0.0;
        JSONObject obj = new JSONObject(weatherJson);
        JSONObject time = obj.getJSONObject("current");
        speed = time.getDouble("wind_mph");

        return speed;

    }

    //windDirection function returns wind direction of city by given json string
    public static String windDirection(String weatherJson){

        String direction ="";
        JSONObject obj = new JSONObject(weatherJson);
        JSONObject time = obj.getJSONObject("current");
        direction = time.getString("wind_dir");

        return direction;

    }


}
