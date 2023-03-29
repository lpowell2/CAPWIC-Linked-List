public class Song {
    private String title;
    private String artist;

    //constructors
    Song (String name, String singer){
        title=name;
        artist=singer;
    }
    Song (){
        title="";
        artist="";
    }

    //getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
