package ifpb.ads.pos.twitter;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 07/02/2018, 10:42:51
 */
public class Credentials {

    private final String consumerKey = "3N0JKQfI5I9Slj5Oo2lHG2rDI";
    private final String consumerSecret = "M1RlLvaPuMDWT5cHP0yF1IEdbIEmajHQxNZQQ5ru99UXTGvgZH";  
 
    private final String token;
    private final String verifier;

    public Credentials(String token, String verifier) {
        this.token = token;
        this.verifier = verifier;
    }

    public String consumerKey() {
        return consumerKey;
    }

    public String consumerSecret() {
        return consumerSecret;
    }

    public String token() {
        return token;
    }

    public String verifier() {
        return verifier;
    }
}
