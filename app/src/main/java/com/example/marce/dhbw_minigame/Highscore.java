package com.example.marce.dhbw_minigame;

/**
 * Created by marce on 08.09.2015.
 */
public class Highscore {

    //private variables
    private int _id;
    private String _player;
    private int _level;
    private  int _points;

    // Empty constructor
    public Highscore(){

    }
    // constructor
    public Highscore(int id, String name, int _level, int _points){
        this._id = id;
        this._player = name;
        this._level = _level;
        this._points = _points;
    }

    // constructor
    public Highscore(String name, int _level, int _points){
        this._player = name;
        this._level = _level;
        this._points = _points;
    }

    public void setPlayername(String name) {
        this._player = name;
    }

    public void setLevel(int level) {
        this._level = level;
    }

    public void setPoints(int points) {
        this._points = points;
    }

    public String getPlayername() {
        return this._player;
    }

    public int getLevel() {
        return this._level;
    }

    public int getPoints() {
        return this._points;
    }
}
