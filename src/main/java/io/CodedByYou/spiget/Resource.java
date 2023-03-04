package io.CodedByYou.spiget;

import io.CodedByYou.spiget.cUtils.U;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Created by CodedByYou on 10/8/2017.
 * Day: Sunday
 * Time: 8:17 PM
 */
public class Resource {
    private JSONObject resoure;
    private int resourceid;
    private Author author;
    private String resourcename;
    private boolean permium;
    private int price;
    private int releaseDate;
    private int downloads;
    private int likes;
    private String downloadLink;
    private String resourceLink;
    private String resourceIconLink;
    private Rating rating;
    private List<String> links;
    private List<String> testedVersions;
    private String description,descriptionAsXml;
    private int version;
    public Resource(String resourcename) throws Exception {
        this.resourcename = resourcename;
        resoure = get("").getJSONObject(0);
        resourceid = resoure.getInt("id");
        resoure = U.getResource(null,resourceid);
        this.resourcename = resoure.getString("name");
        permium = resoure.getBoolean("premium");
        price = resoure.getInt("price");
        releaseDate = resoure.getInt("releaseDate");
        downloads = resoure.getInt("downloads");
        likes = resoure.getInt("likes");
        resourceIconLink = "https://www.spigotmc.org/" + resoure.getJSONObject("icon").getString("url");
        descriptionAsXml = resoure.getString("description");
        descriptionAsXml = new String(Base64.getDecoder().decode(descriptionAsXml));
        description = descriptionAsXml;
        description=description.replaceAll("<.*?>", "");
        description = description.replaceAll("(?m)^[ \t]*\r?\n", "");

        JSONArray version_arr = new JSONArray(resoure.get("versions").toString());
        JSONObject version_obj = version_arr.getJSONObject(0);
        version = Integer.parseInt(version_obj.get("id").toString());

        links = new ArrayList<>();
        JSONObject object = resoure.getJSONObject("links");
        String o = object.toString();
        String[] x = o.split(",");
        for(String l : x){
            links.add(l);
        }
        object = resoure.getJSONObject("rating");
        rating = new Rating(object.getInt("count"),object.getInt("average"));
        JSONArray array = resoure.getJSONArray("testedVersions");
        testedVersions = new ArrayList<>();
        for(int i = 0; i < array.length();i++){
            testedVersions.add(array.getString(i));
        }
        JSONObject ox = resoure.getJSONObject("file");
        downloadLink = "https://www.spigotmc.org/" + ox.getString("url");
        resourceLink = "https://spigotmc.org/" + ox.getString("url").replaceAll("/download\\?version=.*", "");

        author = Author.getByResource(resourceid);
    }
    public Resource(int resourceid) throws Exception {
        this.resourceid = resourceid;
        resoure = U.getResource(null,resourceid);
        this.resourcename = resoure.getString("name");
        // Sometimes resource dont have premium field
        try {
            permium = (Boolean) resoure.get("premium");
        } catch (JSONException je) {
            permium = false;
        }
        price = resoure.getInt("price");
        releaseDate = resoure.getInt("releaseDate");
        downloads = resoure.getInt("downloads");
        descriptionAsXml = resoure.getString("description");
        descriptionAsXml = new String(Base64.getDecoder().decode(descriptionAsXml));
        description = descriptionAsXml;
        description=description.replaceAll("<.*?>", "");
        description = description.replaceAll("(?m)^[ \t]*\r?\n", "");
        resourceIconLink = "https://www.spigotmc.org/" + resoure.getJSONObject("icon").getString("url");

        JSONObject ox = resoure.getJSONObject("file");

        //Some resources don't have versions... Try to get version from url...
        try {
            JSONArray version_arr = new JSONArray(resoure.get("versions").toString());
            JSONObject version_obj = version_arr.getJSONObject(0);
            version = Integer.parseInt(version_obj.get("id").toString());
        } catch (JSONException je) {
            version = Integer.parseInt(ox.getString("url").split("\\?version=")[1]);
        }

        likes = resoure.getInt("likes");
        links = new ArrayList<>();
        JSONObject object = resoure.getJSONObject("links");
        String o = object.toString();
        String[] x = o.split(",");
        for(String l : x){
            links.add(l);
        }
        object = resoure.getJSONObject("rating");
        rating = new Rating(object.getInt("count"),object.getInt("average"));
        JSONArray array = resoure.getJSONArray("testedVersions");
        testedVersions = new ArrayList<>();
        for(int i = 0; i < array.length();i++){
            testedVersions.add(array.getString(i));
        }

        downloadLink = "https://www.spigotmc.org/" + ox.getString("url");
        resourceLink = "https://spigotmc.org/" + ox.getString("url").replaceAll("/download\\?version=.*", "");

        author = Author.getByResource(resourceid);
    }
    private JSONArray get(String x)throws Exception{
        String url = "https://api.spiget.org/v2/search/resources/"+resourcename+"/"+x;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        InputStream inputStream = con.getInputStream();
        String res = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        return new JSONArray(res);
    }
    public String getResourceIconLink() {
        return resourceIconLink;
    }
    public String getTag(){
        String i;
        try {
            i = resoure.getString("tag");
            return  i;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) throws Exception {
        Resource r = new Resource("dlinker");
        System.out.print(r.getDescription());
    }
    public String getDescription() {
        return description;
    }

    public String getResourceName(){
        return resourcename;
    }

    public int getResourceId(){
        return resourceid;
    }

    public Author getAuthor(){
        return author;
    }

    public int getResourceid() {
        return resourceid;
    }

    public boolean isPermium() {
        return permium;
    }

    public int getDownloads() {
        return downloads;
    }

    public int getLikes() {
        return likes;
    }

    public int getReleaseDate() {
        return releaseDate;
    }

    public Rating getRating() {
        return rating;
    }

    public String getDownloadLink(){
        return downloadLink;
    }

    public String getResourceLink() {
        return resourceLink;
    }

    public int getPrice() {
        return price;
    }

    public List<String> getLinks() {
        return links;
    }

    public List<String> getTestedVersions() {
        return testedVersions;
    }

    public int getVersion() {
        return version;
    }

}
