package com.example.tupac.myapplication.backend.models;

import java.util.Date;

/**
 * Created by tupac on 12/4/2016.
 */

public class Match {

    int matchId;
    Competition competition;
    Team homeTeam;
    Team awayTeam;
    int homeGoals;
    int awayGoals;
    Date startTime;

    public Match(int awayGoals, Team awayTeam, Competition competition, int homeGoals, Team homeTeam, int matchId, Date startTime) {
        this.awayGoals = awayGoals;
        this.awayTeam = awayTeam;
        this.competition = competition;
        this.homeGoals = homeGoals;
        this.homeTeam = homeTeam;
        this.matchId = matchId;
        this.startTime = startTime;
    }

    public Match() {

        this.matchId = 0;
    }

    public int getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(int awayGoals) {
        this.awayGoals = awayGoals;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public int getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(int homeGoals) {
        this.homeGoals = homeGoals;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
