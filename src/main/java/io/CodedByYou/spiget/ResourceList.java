package io.CodedByYou.spiget;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DominikSLK on 9/10/2022.
 * Day: Sunday
 * Time: 13:30
 */
public class ResourceList {
    List<Resource> resourceList = new ArrayList<>();

    public ResourceList(int size, String fields, String sort) throws Exception {
        new ResourceList(-1, size, fields, sort);
    }

    public ResourceList(int start, int size, String fields, String sort) throws Exception {
        URL url = new URL( "https://api.spiget.org/v2/resources/free/?size=" + size + "&fields=" + fields + "&sort=" + sort);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestProperty("Accept", "application/json");
        InputStream inputStream = http.getInputStream();
        String text = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        http.disconnect();

        JSONArray ja = new JSONArray(text);

        int count = 0;
        for (Object obj : ja) {
            if(count > start) {
                JSONObject json = new JSONObject(String.valueOf(obj));
                resourceList.add(new Resource(Integer.parseInt(json.get("id").toString())));
            }
            count++;
        }
    }

    public List<Resource> getList(){
        return this.resourceList;
    }
}
