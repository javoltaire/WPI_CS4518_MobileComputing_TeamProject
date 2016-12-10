package com.example.tupac.myapplication.backend;

import com.example.tupac.myapplication.backend.models.Competition;
import com.example.tupac.myapplication.backend.models.Match;
import com.example.tupac.myapplication.backend.models.Round;
import com.example.tupac.myapplication.backend.models.Team;
import com.example.tupac.myapplication.backend.service.ConvertJson;
import com.google.appengine.repackaged.com.google.api.client.json.Json;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
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


/**
 * @author Rupak Lamsal
 *
 * This is a servlet class that is the back end of the application. It will talk to the CrowdScores API, collect Json data,
 * parse them and make required model classes, for instance, teams, matches, etc. The model classes will then be stored in
 * the firebase database where the client app can access the info from. This servlet will also be used to push notification
 * to the client app using the Firebase Messaging.
 *
 */

public class MyServlet extends HttpServlet {
    static Logger Log = Logger.getLogger("com.example.tupac.myapplication.backend.MyServlet");

    private DatabaseReference firebase;

    ArrayList<Competition> competitionArrayList;
    ArrayList<Round> roundArrayList;
    ArrayList<Team> teamArrayList;
    ArrayList<Match> matchArrayList;

    FirebaseDatabase database;

    private String SERVER_KEY = "AAAALHg9Clg:APA91bEFAgnZ-6Ch1J9e49k68SlJswfw8hBBwKHfxG8vQYsVzaKVwyx_tpazifVLxLjjtmv-RN4hcbEuG9W7Bgsa0hltpeuYQbJIq6euO2E7QZGQK2_fZ3m13m1OXfosHTAtOEtIwmYciB0xnPIMwZ0JQhDdzTe8SA";
    private String token="fX9_n4W0QPo:APA91bEhx7uK6DKT8LL-ZNM0_kwLIzPSMT5lA8MXHICBsiMbgdD_Vaf13M2lRPvRl2hq_91VKzjdih09HyPZt8l60vG3iarIqhAmk91-LouZK_vVBLS9whQ8uA56ulY_UTTcnXCj0UlA";



    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        String credential = config.getInitParameter("credential");
        String databaseURL = config.getInitParameter("databaseUrl");

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
        outString = "<p>Match Id, Competition Name, Home Team, Away Team, Start Time</p>";

        resp.getWriter().println(outString);

        doPost(req,resp);


        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();


        /*
         * use the following commented code only once to populate info in the firebase database
         */

//        HttpHandler sh = new HttpHandler();
//        // Making a request to url and getting response
//        String mainUrl = "https://api.crowdscores.com/v1";
//        String competitions = "/competitions";
//        //competition_id for Premier League is 2
//
//        String rounds = "/rounds";
//        String teams = "/teams?round_ids=&competition_ids=2";
//        String matches = "/matches?team_id=7&round_ids=1044&competition_ids=2";
//        String matchDetails = "/matches/69407";

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


//        String teamsJsonStr = sh.makeServiceCall(mainUrl + teams);
//
//        if (teamsJsonStr != null) {
//            try {
//                //resp.getWriter().println(teamsJsonStr);
//                // Getting JSON Array node
//                JSONArray teamList = new JSONArray(teamsJsonStr);
//                //use service class to parse this json array and create a team model
//                teamArrayList = ConvertJson.getTeamfromJson(teamList);
//
//            } catch (final JSONException e) {
//
//            }
//        }

//        String matchesJsonStr = sh.makeServiceCall(mainUrl + matches);
//
//        if (matchesJsonStr != null) {
//            try {
//                //resp.getWriter().println(matchesJsonStr);
//                // Getting JSON Array node
//                JSONArray matchlist = new JSONArray(matchesJsonStr);
//                //use service class to parse this json array and create a match model
//                matchArrayList = ConvertJson.getMatchfromJson(matchlist);
//
//            } catch (final JSONException e) {
//
//            }
//        }

//        String matchDetailsJsonStr = sh.makeServiceCall(mainUrl + matchDetails);
//
//        if (matchDetailsJsonStr != null) {
//            try {
//                resp.getWriter().println(matchDetailsJsonStr);
//                // Getting JSON Array node
//                JSONArray matchlist = new JSONArray(matchDetailsJsonStr);
//                //use service class to parse this json array and create a match model
//                //matchArrayList = ConvertJson.getMatchfromJson(matchlist);
//
//            } catch (final JSONException e) {
//
//            }
//        }
//
//        if (competitionArrayList != null) {
//            for (int i=0; i<competitionArrayList.size(); i++){
//                resp.getWriter().println("<p>" +  competitionArrayList.get(i).getCompetitionId() + "&nbsp;&nbsp;&nbsp;" +
//                        competitionArrayList.get(i).getCompetitionName() + "<p>");
//
//            }
//        }
//
//        if (roundArrayList != null) {
//            for (int i=0; i<roundArrayList.size(); i++){
//                resp.getWriter().println("<p>" +  roundArrayList.get(i).getRoundId()  + "&nbsp;&nbsp;&nbsp;" +
//                        roundArrayList.get(i).getCompetition().getCompetitionId() + "&nbsp;&nbsp;&nbsp;" +
//                        roundArrayList.get(i).getCompetition().getCompetitionName() + "<p>");
//
//            }
//        }
//
//        if (teamArrayList != null) {
//            for (int i=0; i<teamArrayList.size(); i++){
//                resp.getWriter().println("<p>" +  teamArrayList.get(i).getTeamId() + "      "  +
//                        teamArrayList.get(i).getTeamName() + "      "  +
//                        teamArrayList.get(i).getTeamLocation().getTeamLatitude() + "      "  +
//                        teamArrayList.get(i).getTeamLocation().getTeamLatitude() + "<p>");
//            }
//        }
//
//        if (matchArrayList != null) {
//            for (int i=0; i<matchArrayList.size(); i++){
//
//                resp.getWriter().println("<p>" +  matchArrayList.get(i).getMatchId() + "&nbsp;&nbsp;&nbsp;"  +
//                        matchArrayList.get(i).getCompetition().getCompetitionName() + "&nbsp;&nbsp;&nbsp;"  +
//                        matchArrayList.get(i).getHomeTeam().getTeamName() + "&nbsp;&nbsp;&nbsp;"  +
//                        matchArrayList.get(i).getHomeGoals() + "&nbsp;&nbsp;&nbsp;"  +
//                        matchArrayList.get(i).getAwayGoals() + "&nbsp;&nbsp;&nbsp;" +
//                        matchArrayList.get(i).getAwayTeam().getTeamName()  + "&nbsp;&nbsp;&nbsp;" +
//                        matchArrayList.get(i).getStartTime() + "<p>");
//            }
//        }

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

         /*
         * use this only once to populate matches
         */
//        DatabaseReference matchesRef = ref.child("matches");
//
//        Map<String, Match> matchesMap = new HashMap<String, Match>();
//
//        if (matchArrayList != null) {
//            for (int i=0; i<matchArrayList.size(); i++){
//                Match match = matchArrayList.get(i);
//                matchesMap.put(match.getHomeTeam().getTeamName() + " vs " + match.getAwayTeam().getTeamName(), new Match(match.getAwayGoals(), match.getAwayTeam(), match.getCompetition(),
//                        match.getHomeGoals(), match.getHomeTeam(), match.getMatchId(), match.getStartTime()));
//            }
//        }
//
//        matchesRef.setValue(matchesMap);



    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.getWriter().println("*********************post****************************");


        //issue a post request
        HttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost httppost = new HttpPost("https://fcm.googleapis.com/fcm/send");

        //add headers
        httppost.addHeader("Authorization", "key=" + SERVER_KEY);
        httppost.addHeader("Content-Type", "application/json");

        //create json body
        JSONObject notification = new JSONObject();
        notification.put("title", "Chelsea vs Arsenal");
        notification.put("body", "Match begins in 10 mins");

        JSONObject body = new JSONObject();
        body.put("to", "/topics/Chelsea");
        body.put("notification", notification);

        StringEntity params = new StringEntity(body.toString());

        //add the body
        httppost.setEntity(params);

        //Execute and get the response.
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            InputStream instream = entity.getContent();
            try {
                // do something useful
                resp.getWriter().println(instream.toString());
            } finally {
                instream.close();
            }
        }



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
