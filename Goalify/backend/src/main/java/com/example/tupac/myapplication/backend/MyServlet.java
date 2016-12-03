package com.example.tupac.myapplication.backend;

import com.example.tupac.myapplication.backend.models.Competition;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.spi.http.HttpHandler;

/**
 * @author Rupak Lamsal
 */

public class MyServlet extends HttpServlet {
    static Logger Log = Logger.getLogger("com.example.[USERNAME].myapplication.backend.MyServlet");

    private DatabaseReference firebase;

    ArrayList<Competition> competitionArrayList;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        String credential = config.getInitParameter("credential");
        String databaseURL = config.getInitParameter("databaseUrl");

        Log.info(credential);

        // Note: Ensure that the [PRIVATE_KEY_FILENAME].json has read
        // permissions set.
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setServiceAccount(getServletContext().getResourceAsStream(credential))
                .setDatabaseUrl(databaseURL)
                .build();

        FirebaseApp.initializeApp(options);

        firebase = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Log.info("Sending the todo list email.");

        String outString;
        outString = "<p>Sending the todo list email.</p>";

        resp.getWriter().println(outString);


        HttpHandler sh = new HttpHandler();
        // Making a request to url and getting response
        String mainUrl = "https://api.crowdscores.com/v1";
        String apiKey = "?dc736bae447b4f6ba8ec68c530195151";
        String competitions = "/competitions";

        String jsonStr = sh.makeServiceCall(mainUrl + competitions);

        if (jsonStr != null) {
            try {
                resp.getWriter().println(jsonStr);
                // Getting JSON Array node
                JSONArray competitionList = new JSONArray(jsonStr);
                //use service class to parse this json array and create an airlines model
                fromJson(competitionList);

            } catch (final JSONException e) {
            }

        }

        if (competitionArrayList != null) {
            for (int i=0; i<competitionArrayList.size(); i++){
                resp.getWriter().println(competitionArrayList.get(i).getName());
            }
        }


    }

    /*
     * Decodes airlines json into airlines model object
     *
     * @param   jsonObject  a json object that represents an airlines
     * @return              an airline with its attributes
     */
    public Competition fromJson(JSONObject jsonObject) {
        Competition competition = new Competition();
        // Deserialize json into object fields
        try {
            competition.setName(jsonObject.getString("name"));
            competition.setId(jsonObject.getString("dbid"));

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return competition;
    }

    /*
    * Decodes array of airlines json results into airlines model objects
    *
    * @param   jsonArray  a json array that represents a list of airlines
    * @return             void
    */
    public void fromJson(JSONArray jsonArray) {
        JSONObject competitionsJSON;

        competitionArrayList = new ArrayList<Competition>(jsonArray.length());

        // Process each result in json array, decode and convert to airlines object
        for (int i=0; i < jsonArray.length(); i++) {
            try {
                competitionsJSON = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Competition competition = fromJson(competitionsJSON);
            if (competition != null) {
                competitionArrayList.add(competition);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }



    public class HttpHandler {

        public HttpHandler() {
        }

        public String makeServiceCall(String reqUrl) {
            String response = null;
            try {
                URL url = new URL(reqUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("x-crowdscores-api-key", "dc736bae447b4f6ba8ec68c530195151");
                // read the response
                InputStream in = new BufferedInputStream(conn.getInputStream());
                response = convertStreamToString(in);
            } catch (MalformedURLException e) {
            } catch (ProtocolException e) {
            } catch (IOException e) {
            } catch (Exception e) {
            }
            return response;
        }

        private String convertStreamToString(InputStream is) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return sb.toString();
        }
    }

}
