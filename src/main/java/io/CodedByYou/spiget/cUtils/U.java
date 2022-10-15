package io.CodedByYou.spiget.cUtils;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created by CodedByYou on 10/9/2017.
 * Day: Monday
 * Time: 5:12 PM
 */
public class U {
    public static JSONObject getResource(String x,int id)throws Exception{
        String url;
        if(x == null){
            url = "https://api.spiget.org/v2/resources/"+id;
        }else{
            url = "https://api.spiget.org/v2/resources/"+id+"/"+x;
        }
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        InputStream inputStream = con.getInputStream();
        String res = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        if(x != null) {
            res = res.replaceFirst("\\[", "");
            res = res.replaceFirst("\\]", "");
        }
        return new JSONObject(res);
    }

    public static JSONObject searchAuthor(String x)throws Exception{
        String url = "https://api.spiget.org/v2/search/authors/"+x;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        InputStream inputStream = con.getInputStream();
        String res = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        res = res.replaceFirst( "\\[","");
        res = res.replaceFirst( "\\]","");
        return new JSONObject(res);
    } public static JSONObject getAuthor(int x)throws Exception{
        String url = "https://api.spiget.org/v2/authors/"+x;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        InputStream inputStream = con.getInputStream();
        String res = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        res = res.replaceFirst( "\\[","");
        res = res.replaceFirst( "\\]","");
        return new JSONObject(res);
    }
}
