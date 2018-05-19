package com.jxs.rest;

import java.util.ArrayList;
import java.util.HashMap;

public class Login {
    /**
     * Format :
     * <Id, <Service , Token >>
     * Used to associate the tokens from different services to their owner.
     */
    private HashMap<String, HashMap<String, String>> idMap;
    private ArrayList<String > logged_users;


    public Login() {
        this.logged_users = new ArrayList<>();
        this.idMap=new HashMap<String, HashMap<String, String>>();
    }

    public int addUser(String pseudo) { // return 0 if ALL OK, 1 if pseudo already used
        int res = 1;
        if ( !this.idMap.containsKey(pseudo) ) {
            this.idMap.put(pseudo, new HashMap<>());
            res = 0;
        }
        return res;
    }

    public void addTokenFromService(String pseudo, String service, String token ) {
        if ( this.idMap.containsKey(pseudo )) { // if pseudo exists
            this.idMap.get(pseudo).put(service, token);
        } else {
            this.addUser(pseudo);
            this.addTokenFromService(pseudo, service, token);
        }
    }

    public String getTokenFromService(String pseudo, String service) {
        String res = "none";
        System.out.println(pseudo);
        System.out.println(this.idMap);
        if ( this.idMap.containsKey(pseudo) && this.getLoggedUsers().contains(pseudo)) {
            res = this.idMap.get(pseudo).get(service);
        }
        return res;
    }

    public ArrayList<String> getLoggedUsers() {
        return this.logged_users;
    }

    public void addLoggedUser(String pseudo ) {
        this.logged_users.add(pseudo);
    }

    public void unlogUser(String pseudo ) {
        System.out.println("Disconnecting user " + pseudo);
        this.logged_users.remove(pseudo);
    }
}
