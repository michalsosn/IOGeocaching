package pl.lodz.p.ftims.geocaching.dao;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import pl.lodz.p.ftims.geocaching.model.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.DefaultHttpClient;


public class ProfilesAccessDao implements IProfilesAccess{
    private String webServiceAddress;
    private static final String TAG = "myApp";

    public ProfilesAccessDao(Context context){
        PropertyReader reader = new PropertyReader(context);
        webServiceAddress = reader.getProperties("httpClientProperties.properties").getProperty("ProfileAccess");
    }

    public boolean changePassword(Credentials credentials, String newPassword){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ChangePasswordRequest request = new ChangePasswordRequest(credentials, newPassword);
        XStream xstreamIn = new XStream(new DomDriver());
        xstreamIn.alias("ChangePasswordRequest", ChangePasswordRequest.class);
        xstreamIn.aliasField("Credentials", ChangePasswordRequest.class, "credentials");
        String inputXML;
        inputXML = xstreamIn.toXML(request);
        StringEntity entity = null;
        try {
            entity = new StringEntity(inputXML); //nie mam pojęcia czemu nie znajduje tego ontruktora
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        entity.setChunked(true);
        HttpPost httppost = new HttpPost(webServiceAddress);
        httppost.setEntity(entity);
        HttpClient client = new DefaultHttpClient();

        InputStream in;
        try {
            HttpResponse response = client.execute(httppost);
            in=response.getEntity().getContent();
            String outputXML = IOUtils.toString(in);
            return outputXML.contains("true");
        } catch (ClientProtocolException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public List<RankingEntry> pickRanking() {
        // TODO: Implement
        return null;
    }

    public boolean createNewUser(Profile profile, Credentials credentials){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        CreateUserRequest request = new CreateUserRequest(profile, credentials);
        XStream xstreamIn = new XStream(new DomDriver());
        xstreamIn.alias("CreateUserRequest", CreateUserRequest.class);
        String inputXML;
        inputXML = xstreamIn.toXML(request);
        StringEntity entity = null;
        try {
            entity = new StringEntity(inputXML);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        entity.setChunked(true);
        HttpPost httppost = new HttpPost(webServiceAddress);
        httppost.setEntity(entity);
        HttpClient client = new DefaultHttpClient();

        InputStream in;
        try {
            HttpResponse response = client.execute(httppost);
            in=response.getEntity().getContent();
            String outputXML = IOUtils.toString(in);
            return outputXML.contains("true"); //lamerko ale nie wiem jak będzie boolean zwracany
        } catch (ClientProtocolException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean login(Credentials credentials){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        LoginRequest request = new LoginRequest(credentials);
        XStream xstreamIn = new XStream(new DomDriver());
        xstreamIn.alias("LoginRequest", LoginRequest.class);
        xstreamIn.aliasField("Credentials", LoginRequest.class, "credentials");

        String inputXML;
        inputXML = xstreamIn.toXML(request);
        System.out.print(inputXML);
        StringEntity entity = null;
        try {
            entity = new StringEntity(inputXML);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        entity.setChunked(true);
        HttpPost httppost = new HttpPost(webServiceAddress);
        httppost.setEntity(entity);
        HttpClient client = new DefaultHttpClient();
        InputStream in ;
        try {
            HttpResponse response = client.execute(httppost);
            in=response.getEntity().getContent();
            String outputXML = IOUtils.toString(in);
            return outputXML.contains("true"); //lamerko ale nie wiem jak będzie boolean zwracany
        } catch (ClientProtocolException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

 public Profile getUserProfile(Credentials currentCredentials){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        LoginRequest request = new LoginRequest(currentCredentials);
        XStream xstreamIn = new XStream(new DomDriver());
        xstreamIn.alias("ProfileRequest", LoginRequest.class);
        xstreamIn.aliasField("Credentials", LoginRequest.class, "credentials");
        String inputXML;
        inputXML = xstreamIn.toXML(request);
        StringEntity entity  = null;
        try{
            entity = new StringEntity(inputXML);

        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        entity.setChunked(true);
        HttpPost httppost = new HttpPost(webServiceAddress);
        httppost.setEntity(entity);
        HttpClient client = new DefaultHttpClient();
        InputStream in;
        try {
            HttpResponse response = client.execute(httppost);
            in=response.getEntity().getContent();
            String outputXML = IOUtils.toString(in);
            XStream xstream = new XStream(new DomDriver());
            xstream.alias("LoginResponse", LoginResponse.class);
            xstream.aliasField("LoginResponse", LoginResponse.class, "profil");
            LoginResponse loginResponse = (LoginResponse)xstream.fromXML(outputXML);
            return loginResponse.getProfile();
        } catch (ClientProtocolException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    boolean saveUserProfile(Credentials currentCredentials, Profile profile){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SaveProfileRequest request = new SaveProfileRequest(currentCredentials, profile);
        XStream xstreamIn = new XStream(new DomDriver());
        xstreamIn.alias("SaveProfileRequest", SaveProfileRequest.class);
        xstreamIn.aliasField("Profile", SaveProfileRequest.class, "profile");
        String inputXML;
        inputXML = xstreamIn.toXML(request);
        StringEntity entity  = null;
        try{
            entity = new StringEntity(inputXML);

        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        entity.setChunked(true);
        HttpPost httppost = new HttpPost(webServiceAddress);
        httppost.setEntity(entity);
        HttpClient client = new DefaultHttpClient();
        InputStream in;
        try {
            HttpResponse response = client.execute(httppost);
            in=response.getEntity().getContent();
            String outputXML = IOUtils.toString(in);
            return outputXML.contains("true");
        } catch (ClientProtocolException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    private class LoginRequest{
        private Credentials credentials;
        public LoginRequest(Credentials c){
            this.credentials = c;
        }
    }

    private class LoginResponse{
        private DataModelProfile profil;

        public Profile getProfile(){
            Profile profile = new Profile(profil.nick, profil.email, 666, 666);  // Tutaj dodaj pola z rankingu
            return profile;
        }
    }

    private class ChangePasswordRequest{
        private Credentials credentials;
        private String newPasswd;

        public ChangePasswordRequest(Credentials credentials, String newPasswd){
            this.credentials=credentials;
            this.newPasswd=newPasswd;
        }
    }

    private class CreateUserRequest{
        private String login = "";
        private String password = "";
        private String nick = "";
        private String email = "";

        public CreateUserRequest(Profile profile, Credentials credentials){
            this.login = credentials.getLogin();
            this.password = credentials.getPassword();
            this.nick = profile.getNick();
            this.email = profile.getEmail();
        }
    }

    private class SaveProfileRequest{
        private DataModelProfile profile;

        SaveProfileRequest(Credentials credentials, Profile profil){
            profile = new DataModelProfile(credentials.getLogin(), credentials.getPassword(), profil.getEmail(), profil.getNick());
        }
    }

    private class DataModelProfile{
        private String id = null;
        private String login;
        private String password;
        private String nick;
        private String email;
        private String ranking = null;

        DataModelProfile(String login, String password, String email, String nick){
            this.login = login;
            this.password = password;
            this.nick = nick;
            this.email = email;
        }
    }
}