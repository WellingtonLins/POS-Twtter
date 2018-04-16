package ifpb.ads.pos.twitter.web;

import ifpb.ads.pos.twitter.AuthenticatorOfTwitter;
import ifpb.ads.pos.twitter.Credentials;
import ifpb.ads.pos.twitter.EndpointInTwitter;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.json.JsonObject;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 20/02/2018, 14:15:17
 */
@Named("controladorTwitter")
@RequestScoped
public class ControladorTwitter {

    private Client builder = ClientBuilder.newClient();

    private String max_id = "";

    private Map<String, Integer> seguidores = new HashMap<>();

    private Credentials getCredentials() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        return (Credentials) session.getAttribute("token");
    }

    public String seguidores() {
        return String.valueOf(getSeguidores());
    }

    public String tweets() {
        return String.valueOf(getTweets());
    }

    public String mensao() {
        return getMentions();
    }

    public String getMax_id() {
        return agarraMax_ID(getTweets());
    }

    public Map<String, Integer> getSeguidores() {
        AuthenticatorOfTwitter authenticator = new AuthenticatorOfTwitter(getCredentials());
        WebTarget webTarget = builder.target("https://api.twitter.com/1.1/followers/ids.json");

        EndpointInTwitter endpoint = new EndpointInTwitter("GET", webTarget.getUri().toString());
        String headerAuthorization = authenticator.in(endpoint).authenticate();
        WebTarget updateTarget = builder.target(webTarget.getUri().toString());
        Response update = updateTarget
                .request().accept(MediaType.APPLICATION_JSON)
                .header("Authorization", headerAuthorization)
                .get();

        JSONObject jsonObject = new JSONObject(update.readEntity(String.class));

        JSONArray jsonArray = jsonObject.getJSONArray("ids");

        for (Object object : jsonArray) {
            seguidores.put(String.valueOf(object), 0);
        }

        return seguidores;
    }

    public String getMentions() {
//    public Map<String, Integer> getMentions() {

        JSONArray array = new JSONArray();

        JSONArray jsonObject = getTweets();
        for (Object object : jsonObject) {
            JSONObject jo = (JSONObject) object;

            jo.getJSONObject("entities").getJSONArray("user_mentions").length();
         

            System.out.println("Mentions: " + jo.getJSONObject("entities").getJSONArray("user_mentions").length());
            JSONArray mentions = jo.getJSONObject("entities").getJSONArray("user_mentions");
            for (int j = 0; j < mentions.length(); j++) {
                System.out.println("Mentioned---> " + mentions.getJSONObject(j).getString("screen_name"));
          array.put(object);
            }
        }

        return array.toString();
    }

    private double calculador() {

        double mentions = 0;
        double retweets = 0;
        double soma = mentions + retweets + 2;
        double resultado = 1 - (1 / soma);

        return resultado;
    }

    public String rodar() {
        int cont = 1;
        int flag = 0;
        JSONArray ja = new JSONArray();
        do {
            flag++;
            if (flag == 0) {
                ja.put(getTweets("0"));

            } else {

                ja.put(getTweets());
            }

            cont++;
        } while (cont <= 3);
        return ja.toString();
    }

    private JSONArray getTweets() {

        AuthenticatorOfTwitter authenticator = new AuthenticatorOfTwitter(getCredentials());
        WebTarget webTarget = builder.target("https://api.twitter.com/1.1/statuses/user_timeline.json");

        Map<String, String> map = new HashMap<>();
        map.put("count", "3");
        EndpointInTwitter endpoint = new EndpointInTwitter("GET", webTarget.getUri().toString());
        String headerAuthorization = authenticator.in(endpoint).authenticate(map);
        WebTarget updateTarget = builder.target(webTarget.getUri().toString());
        Response update = updateTarget
                .queryParam("count", "3")
                .request().accept(MediaType.APPLICATION_JSON)
                .header("Authorization", headerAuthorization)
                .get();

        JSONArray jsonArray = new JSONArray(update.readEntity(String.class));

        return jsonArray;

    }
    private JSONArray getTweets(String marcador) {

        AuthenticatorOfTwitter authenticator = new AuthenticatorOfTwitter(getCredentials());
        WebTarget webTarget = builder.target("https://api.twitter.com/1.1/statuses/user_timeline.json");

        Map<String, String> map = new HashMap<>();
        map.put("count", "3");
        map.put("max_id", marcador);

        EndpointInTwitter endpoint = new EndpointInTwitter("GET", webTarget.getUri().toString());
        String headerAuthorization = authenticator.in(endpoint).authenticate(map);
        WebTarget updateTarget = builder.target(webTarget.getUri().toString());
        Response update = updateTarget
                .queryParam("count", "3")
                .queryParam("max_id", marcador)
                .request().accept(MediaType.APPLICATION_JSON)
                .header("Authorization", headerAuthorization)
                .get();

        JSONArray jsonArray = new JSONArray(update.readEntity(String.class));

        return jsonArray;

    }

    public String todosTweets() {//??

        AuthenticatorOfTwitter authenticator = new AuthenticatorOfTwitter(getCredentials());
        WebTarget webTarget = builder.target("https://api.twitter.com/1.1/statuses/user_timeline.json");

        Map<String, String> map = new HashMap<>();
        map.put("count", "60");

        EndpointInTwitter endpoint = new EndpointInTwitter("GET", webTarget.getUri().toString());
        String headerAuthorization = authenticator.in(endpoint).authenticate(map);
        WebTarget updateTarget = builder.target(webTarget.getUri().toString());
        Response update = updateTarget
                .queryParam("count", "60")
                .request().accept(MediaType.APPLICATION_JSON)
                .header("Authorization", headerAuthorization)
                .get();

        JSONArray jsonArray = new JSONArray(update.readEntity(String.class));

        return jsonArray.toString();

    }

    private String agarraMax_ID(JSONArray jsonArray) {
        String max = "";
        int length = jsonArray.length() - 1;

        max = jsonArray.getJSONObject(length).getString("id_str");
        max_id = max;
        return max;
    }
}
