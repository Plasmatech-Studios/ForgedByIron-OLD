package com.example.fbifitness;

public class SessionController {

    //Singleton 
    private static SessionController instance = null;
    
    private SessionController() {
        // Exists only to defeat instantiation.
    }
    public static SessionController getInstance() {
        if(instance == null) {
            instance = new SessionController();
        }
        return instance;
    } 


}
