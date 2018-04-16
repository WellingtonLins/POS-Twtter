package ifpb.ads.pos.twitter.web;

import java.util.List;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 20/02/2018, 15:01:51
 */
public class Twitter {

     private String  from;//Logged user name
  private int fromId;//Logged user id
  private String tweetId; 
  private String userId;// User that has been influenced
  private boolean tipo; 

    public Twitter(String from, int fromId, String tweetId, String userId, boolean tipo) {
        this.from = from;
        this.fromId = fromId;
        this.tweetId = tweetId;
        this.userId = userId;
        this.tipo = tipo;
    }

  
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public String getTweetId() {
        return tweetId;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isTipo() {
        return tipo;
    }

    public void setTipo(boolean tipo) {
        this.tipo = tipo;
    }

  

    @Override
    public String toString() {
        return "Twitter{" + "from=" + from + ", fromId=" + fromId + ", tweetId=" + tweetId + ", userId=" + userId + ", tipo=" + tipo + '}';
    }

  

}
