package me.ocel.capturetheflag;

public class GameStatus {

    private String status;

    public GameStatus() {
        //Waiting, Starting, Playing
        this.status = "Waiting";
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
