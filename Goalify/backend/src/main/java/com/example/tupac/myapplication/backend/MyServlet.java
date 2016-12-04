package com.example.tupac.myapplication.backend;

import com.example.tupac.myapplication.backend.models.Competition;
import com.example.tupac.myapplication.backend.models.Round;
import com.example.tupac.myapplication.backend.models.Team;
import com.example.tupac.myapplication.backend.service.ConvertJson;
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
import java.util.HashMap;
import java.util.Map;
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
    ArrayList<Round> roundArrayList;
    ArrayList<Team> teamArrayList;

    FirebaseDatabase database;


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

        String outString;
        outString = "<p>Team_id, Name, Latitude, Longitude</p>";

        resp.getWriter().println(outString);


        HttpHandler sh = new HttpHandler();
        // Making a request to url and getting response
        String mainUrl = "https://api.crowdscores.com/v1";
        String competitions = "/competitions";
        //competition_id for Premier League is 2

        String rounds = "/rounds";
        String teams = "/teams?round_ids=&competition_ids=2";

//        String competitionsJsonStr = sh.makeServiceCall(mainUrl + competitions);
//
//        if (competitionsJsonStr != null) {
//            try {
//                //resp.getWriter().println(competitionsJsonStr);
//                // Getting JSON Array node
//                JSONArray competitionList = new JSONArray(competitionsJsonStr);
//                //use service class to parse this json array and create a competition model
//                competitionArrayList = ConvertJson.getCompetitionfromJson(competitionList);
//
//            } catch (final JSONException e) {
//            }
//        }
//
//        String roundsJsonStr = sh.makeServiceCall(mainUrl + rounds);
//
//        if (roundsJsonStr != null) {
//            try {
//                //resp.getWriter().println(roundsJsonStr);
//                // Getting JSON Array node
//                JSONArray roundList = new JSONArray(roundsJsonStr);
//                //use service class to parse this json array and create a round model
//                roundArrayList = ConvertJson.getRoundfromJson(roundList);
//
//            } catch (final JSONException e) {
//            }
//        }


        String teamsJsonStr = sh.makeServiceCall(mainUrl + teams);

        if (teamsJsonStr != null) {
            try {
                //resp.getWriter().println(teamsJsonStr);
                // Getting JSON Array node
                JSONArray teamList = new JSONArray(teamsJsonStr);
                //use service class to parse this json array and create a team model
                teamArrayList = ConvertJson.getTeamfromJson(teamList);

            } catch (final JSONException e) {

            }
        }

        if (competitionArrayList != null) {
            for (int i=0; i<competitionArrayList.size(); i++){
                resp.getWriter().println("<p>" +  competitionArrayList.get(i).getCompetitionId() + "&nbsp;&nbsp;&nbsp;" +
                        competitionArrayList.get(i).getCompetitionName() + "<p>");

            }
        }

        if (roundArrayList != null) {
            for (int i=0; i<roundArrayList.size(); i++){
                resp.getWriter().println("<p>" +  roundArrayList.get(i).getRoundId()  + "&nbsp;&nbsp;&nbsp;" +
                        roundArrayList.get(i).getCompetition().getCompetitionId() + "&nbsp;&nbsp;&nbsp;" +
                        roundArrayList.get(i).getCompetition().getCompetitionName() + "<p>");

            }
        }

        if (teamArrayList != null) {
            for (int i=0; i<teamArrayList.size(); i++){
                resp.getWriter().println("<p>" +  teamArrayList.get(i).getTeamId() + "      "  +
                        teamArrayList.get(i).getTeamName() + "      "  +
                        teamArrayList.get(i).getTeamLocation().getTeamLatitude() + "      "  +
                        teamArrayList.get(i).getTeamLocation().getTeamLatitude() + "<p>");
            }
        }

        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        /*
         * use this only once to populate
         */
//        DatabaseReference teamRef = ref.child("teams");
//
//        Map<String, Team> teamsMap = new HashMap<String, Team>();
//
//        if (teamArrayList != null) {
//            for (int i=0; i<teamArrayList.size(); i++){
//                Team team = teamArrayList.get(i);
//                teamsMap.put(team.getTeamName(), new Team(team.getTeamId(), team.getTeamName(), team.getTeamLocation()));
//            }
//        }
//
//        teamRef.setValue(teamsMap);



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
