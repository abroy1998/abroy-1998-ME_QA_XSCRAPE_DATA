package demo.data;


//POJO class for testcase1
public class Score {
     private long epoch_time;
     private String team_name;
     private String year;
     private Double win_per;

    public Score(long epoch_time, String team_name, String year, Double win_per)
    {
        this.epoch_time = epoch_time;
        this.team_name = team_name;
        this.year = year;
        this.win_per = win_per;

    }

    public long getEpoch_time() {
        return epoch_time;
    }

    public void setEpoch_time(long epoch_time) {
        this.epoch_time = epoch_time;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Double getWin_per() {
        return win_per;
    }

    public void setWin_per(Double win_per) {
        this.win_per = win_per;
    }
}

