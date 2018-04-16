package ifpb.ads.pos.twitter.web;

import ifpb.ads.pos.twitter.AuthenticatorOfTwitter;
import ifpb.ads.pos.twitter.Credentials;
import ifpb.ads.pos.twitter.EndpointInTwitter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONException;
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

    private static String ultimoID;

    private Map<String, Integer> seguidores = new HashMap<>();
    private List<String> lista = new ArrayList<>();

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
        boolean flag = true;
        String maior = "";
        JSONArray ja = new JSONArray();
        do {
            if (flag) {
                System.out.println("ENTROU NA FLAGGG");

                JSONArray jSONArray = getTweets();

                //debug
                imprimeSaida(jSONArray, "jSONArray");
                
                maior = agarraMax_ID(jSONArray);
                int length = jSONArray.length();
                jSONArray.remove(length - 1);
         
                //debug
                imprimeSaida(jSONArray, "Removendo do jSONArray");

                //adionando objetos ao array para a impressao na tela
                for (Object object : jSONArray) {
                    JSONObject js = (JSONObject) object;
                    ja.put(js);
                }
                flag = false;

            } else {
                
                System.out.println("SAIU SAIU SAIU DA FLAGG  " + maior);
                JSONArray jSONArray = getTweets(maior);
                
                //atualizando maior id
                 maior = agarraMax_ID(jSONArray);
                int length = jSONArray.length();
                jSONArray.remove(length - 1);

                for (Object object : jSONArray) {
                    JSONObject js = (JSONObject) object;
                    ja.put(js);
                }
            }

            cont++;
        } while (cont <= 3);

        JSONArray array_Ids3 = criaArrayComIds(ja);
        System.out.println("FINAL jSONArray");
        System.out.println("FINAL jSONArray");
        System.out.println(array_Ids3);

        return ja.toString();
    }

    private void imprimeSaida(JSONArray jSONArray, String saida) throws JSONException {
        System.out.println(saida);

        JSONArray array_Ids = criaArrayComIds(jSONArray);

        System.out.println(array_Ids);
        System.out.println(saida + " FIM");

    }

    private JSONArray criaArrayComIds(JSONArray jSONArray) throws JSONException {
        JSONArray array_Ids = new JSONArray();
        for (Object object : jSONArray) {
            JSONObject o = (JSONObject) object;

            array_Ids.put(o.getString("id_str"));
        }
        return array_Ids;
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
        map.put("include_rts", "true");
        map.put("max_id", marcador);

        EndpointInTwitter endpoint = new EndpointInTwitter("GET", webTarget.getUri().toString());
        String headerAuthorization = authenticator.in(endpoint).authenticate(map);
        WebTarget updateTarget = builder.target(webTarget.getUri().toString());
        Response update = updateTarget
                .queryParam("count", "3")
                .queryParam("include_rts", "true")
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

        JSONArray array_Ids = new JSONArray();

        for (Object object : jsonArray) {
            JSONObject o = (JSONObject) object;

            array_Ids.put(o.getString("id_str"));
        }
        return array_Ids.toString();

    }

    private String agarraMax_ID(JSONArray jsonArray) {
        String max = "";
        int length = jsonArray.length() - 1;

        max = jsonArray.getJSONObject(length).getString("id_str");
        return max;
    }
}
