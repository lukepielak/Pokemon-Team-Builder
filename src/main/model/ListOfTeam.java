package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// contains the list of Pokemon teams created by the user.
public class ListOfTeam implements Writable {
    private ArrayList<Team> teams; // stores list of teams created by the user.

    // MODIFIES: this.
    // EFFECTS: creates an object to store teams, empty initially.
    public ListOfTeam() {
        this.teams = new ArrayList<Team>();
    }

    // MODIFIES: this.
    // EFFECTS: adds a pokemon team to a listOfTeam.
    public void addTeam(Team team) {
        teams.add(team);
        EventLog.getInstance().logEvent(new Event("Team named " + team.getTeamName() + " was added to teams."));
    }

    // MODIFIES: this.
    // EFFECTS: given a string representing a team name, removes the corresponding pokemon team from listOfTeam.
    public void removeTeam(String name) {
        for (Team t : this.getTeams()) {
            if (t.getTeamName().equals(name)) {
                this.getTeams().remove(t);
                EventLog.getInstance().logEvent(new Event("A team named " + name + " was removed from teams."));
            }
        }
    }

    public ArrayList<Team> getTeams() {
        return this.teams;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("teams", teamsToJson());
        return json;
    }

    // EFFECTS: returns things in this ListOfTeam as a JSON array
    private JSONArray teamsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Team t : teams) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}
