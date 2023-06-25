package org.example;

import java.util.*;
import java.io.*;
import java.net.*;
import org.json.*;

public class ApiServer {

    static List <User> users = new ArrayList<>();

    public static void main(String[] args) {

        int port = 5432;
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Example app listening at http://localhost:" + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String request = in.readLine();
                String[] requestParts = request.split(" ");
                String method = requestParts[0];
                String endpoint = requestParts[1];
                String requestBody = "";

                if (method.equals("POST")) {
                    String line;
                    while ((line = in.readLine()) != null) {
                        requestBody += line;
                    }
                }

                String responseBody = handleRequest(method, endpoint, requestBody);

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type: application/json");
                out.println("Access-Control-Allow-Origin: *");
                out.println();
                out.println(responseBody);

                clientSocket.close();
            }

        } catch (IOException e) {
            System.err.println("Could not listen on port " + port);
            System.exit(-1);
        }

    }

    private static String handleRequest(String method, String endpoint, String requestBody) {

        String[] endpointParts = endpoint.split("/");

        if (endpoint.equals("/api/users") && method.equals("POST")) {
            JSONObject json = new JSONObject(requestBody);
            User user = new User(json);
            users.add(user);
            System.out.println("Created user with ID " + user.getId());
            return user.toString();

        } else if (endpointParts.length == 4 && endpointParts[2].equals("liked_songs")) {
            int userId = Integer.parseInt(endpointParts[2]);
            JSONObject json = new JSONObject(requestBody);
            Song song = new Song(json);
            User user = getUserById(userId);
            if (user == null) {
                return "User not found";
            } else {
                user.addLikedSong(song);
                System.out.println("Added song '" + song.getTitle() + "' to user " + userId + "'s liked songs");
                return song.toString();
            }

        } else if (endpointParts.length == 4 && endpointParts[3].equals("liked_songs") && method.equals("GET")) {
            int userId = Integer.parseInt(endpointParts[2]);
            User user = getUserById(userId);
            if (user == null) {
                return "User not found";
            } else {
                return user.getLikedSongs().toString();
            }

        } else if (endpointParts.length == 3 && endpointParts[1].equals("songs") && endpointParts[2].equals("play") && method.equals("GET")) {
            int songId = Integer.parseInt(endpointParts[2]);
            Song song = getSongById(songId);
            if (song == null) {
                return "Song not found";
            } else {
                String filePath = song.getFilePath();
                try {
                    Process player = Runtime.getRuntime().exec("afplay " + filePath);
                    player.waitFor();
                    System.out.println("Finished playing " + song.getTitle());
                    return "Finished playing " + song.getTitle();
                } catch (IOException e) {
                    return "Error playing song: " + e.getMessage();
                } catch (InterruptedException e) {
                    return "Error playing song: " + e.getMessage();
                }
            }

        } else {
            return "Invalid endpoint";
        }

    }

    private static User getUserById(int userId) {
        for (User user : users) {
            if (user.getId() == userId) {
                return user;
            }
        }
        return null;
    }

    private static Song getSongById(int songId) {
        for (User user : users) {
            List<Song> likedSongs = user.getLikedSongs();
            for (Song song : likedSongs) {
                if (song.getId() == songId) {
                    return song;
                }
            }
        }
        return null;
    }

}

class User {

    private int id;
    private String name;
    private List<Song> likedSongs;

    public User(JSONObject json) {
        this.id = ApiServer.users.size() + 1;
        this.name = json.getString("name");
        this.likedSongs = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Song> getLikedSongs() {
        return likedSongs;
    }

    public void addLikedSong(Song song) {
        song.setId(this.likedSongs.size() + 1);
        this.likedSongs.add(song);
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("name", name);
        json.put("liked_songs", likedSongs);
        return json.toString();
    }

}

class Song {

    private int id;
    private String title;
    private String artist;
    private String album;
    private String filePath;

    public Song(JSONObject json) {
        this.title = json.getString("Hotel");
        this.artist = json.getString("Eagles");
        this.album = json.getString("Hotel");
        this.filePath = "Desktop" + artist + "/" + album + "/" + title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("title", title);
        json.put("artist", artist);
        json.put("album", album);
        json.put("file_path", filePath);
        return json.toString();
    }

}