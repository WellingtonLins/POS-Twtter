package ifpb.ads.pos.twitter.web;

import ifpb.ads.pos.twitter.AuthenticatorOfTwitter;
import ifpb.ads.pos.twitter.Credentials;
import ifpb.ads.pos.twitter.EndpointInTwitter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    private Map<String, Integer> seguidores = new HashMap<>();

    private JSONArray ja = new JSONArray();

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

//    public List<String> getNomeSeguidores() {
//        AuthenticatorOfTwitter authenticator = new AuthenticatorOfTwitter(getCredentials());
//        WebTarget webTarget = builder.target("https://api.twitter.com/1.1/followers/list.json");
//
//        EndpointInTwitter endpoint = new EndpointInTwitter("GET", webTarget.getUri().toString());
//        String headerAuthorization = authenticator.in(endpoint).authenticate();
//        WebTarget updateTarget = builder.target(webTarget.getUri().toString());
//        Response update = updateTarget
//                .request().accept(MediaType.APPLICATION_JSON)
//                .header("Authorization", headerAuthorization)
//                .get();
//
//        List<String> listaNomes = new ArrayList();
//
//        JSONObject jsonObject = new JSONObject(update.readEntity(String.class));
//
//        JSONArray jsonArray = jsonObject.getJSONArray("users");
//
//        for (Object object : jsonArray) {
//                      JSONObject o = (JSONObject) object;
//                      
//            listaNomes.add(o.getString("screen_name"));
//        }
//
//        return listaNomes;
//    }

    public String getMentions() {
//    public Map<String, Integer> getMentions() {

        JSONArray array = new JSONArray();

        JSONArray jsonObject = ja;
        for (Object object : jsonObject) {
            JSONObject jo = (JSONObject) object;

            jo.getJSONObject("entities").getJSONArray("user_mentions").length();

            System.out.println("Mentions: " + jo.getJSONObject("entities").getJSONArray("user_mentions").length());
            JSONArray mentions = jo.getJSONObject("entities").getJSONArray("user_mentions");
            for (int j = 0; j < mentions.length(); j++) {
                System.out.println("Mentioned---> " + mentions.getJSONObject(j).getString("screen_name"));
                array.put(mentions.getJSONObject(j).getString("screen_name"));
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
        String aux = "";
        String aux2 = "";
        String maior = "";

        boolean cont = true;
        boolean flag = true;

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
                aux = maior;
                System.out.println("SAIU SAIU SAIU DA FLAGG  " + maior);
                JSONArray jSONArray = getTweets(maior);

                //atualizando maior id
                maior = agarraMax_ID(jSONArray);

                aux2 = maior;

                if (aux.equals(aux2)) {
                    cont = false;
                }
                aux = maior;
                int length = jSONArray.length();
                jSONArray.remove(length - 1);

                for (Object object : jSONArray) {
                    JSONObject js = (JSONObject) object;
                    ja.put(js);
                }
            }

        } while (cont);

        JSONArray array_Ids3 = criaArrayComIds(ja);
        System.out.println("FINAL jSONArray");
        System.out.println(array_Ids3);

        return ja.toString();
    }

//debug
    private void imprimeSaida(JSONArray jSONArray, String saida) throws JSONException {
        System.out.println(saida);

        JSONArray array_Ids = criaArrayComIds(jSONArray);

        System.out.println(array_Ids);
        System.out.println(saida + " FIM");

    }
//debug

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
        map.put("count", "30");
        map.put("include_rts", "true");
        EndpointInTwitter endpoint = new EndpointInTwitter("GET", webTarget.getUri().toString());
        String headerAuthorization = authenticator.in(endpoint).authenticate(map);
        WebTarget updateTarget = builder.target(webTarget.getUri().toString());
        Response update = updateTarget
                .queryParam("count", "30")
                .queryParam("include_rts", "true")
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
        map.put("count", "30");
        map.put("include_rts", "true");
        map.put("max_id", marcador);

        EndpointInTwitter endpoint = new EndpointInTwitter("GET", webTarget.getUri().toString());
        String headerAuthorization = authenticator.in(endpoint).authenticate(map);
        WebTarget updateTarget = builder.target(webTarget.getUri().toString());
        Response update = updateTarget
                .queryParam("count", "30")
                .queryParam("include_rts", "true")
                .queryParam("max_id", marcador)
                .request().accept(MediaType.APPLICATION_JSON)
                .header("Authorization", headerAuthorization)
                .get();

        JSONArray jsonArray = new JSONArray(update.readEntity(String.class));

        return jsonArray;

    }

    private String agarraMax_ID(JSONArray jsonArray) {
        String max = "";
        int length = jsonArray.length() - 1;

        max = jsonArray.getJSONObject(length).getString("id_str");
        return max;
    }
    
    public String tabularMencao(){

        String mensao = mensao().trim();

        
        String modificada = mensao.substring(1, mensao.length() - 1);

        String[] strings = Arrays.stream(modificada.split(","))
                .map(s -> s.substring(1, s.length() - 1))
                .toArray(String[]::new);
        
        
        List<String> lista =  Arrays.asList(strings);
//        List<String> lista = Arrays.asList("oe1cxw", "miolivc", "brega_falcao", "andrematurano", "nosborcastilho", "SamiPietikainen", "natan_severo", "ricardojob", "TecRahul", "JoeNihon", "gpantuza", "ricardojob", "ricardojob", "mkyong", "mkyong", "iamfutureproof", "YouTube", "YouTube", "MarcosBrizeno", "labnol", "SlideShare", "MarcosBrizeno", "loiane", "SlideShare", "YouTube", "jucindra", "SlideShare", "YouTube", "YouTube", "algaworks", "YouTube", "GalantiAndrea", "rafa_cz", "algaworks", "Tutorialzine", "wordpressdotcom");
        Map<String, Integer> mencaoMap = new HashMap<>();

        //filtrando lista
        Set<String> set = new HashSet<>(lista);
        List<String> listaFitrada = new ArrayList<>(set);

        for (int i = 0; i < set.size(); i++) {
            mencaoMap.put(listaFitrada.get(i), Collections.frequency(lista, listaFitrada.get(i)));
        }

        List<String> retorno = new ArrayList();
        final String format = "Seguidor: %s possui: %d mencao";
        final Set<String> chaves = mencaoMap.keySet(); // as chaves são os ids
        for (final String chave : chaves) {
            retorno.add(chave + "  " + mencaoMap.get(chave));
            System.out.println(String.format(format, chave, mencaoMap.get(chave)));
        }

        Set<String> repetidos = new HashSet<>();

        System.out.println("Tamanho original ===>  " + lista.size());

//pegando elementos repetidos
        for (int i = 0; i < lista.size(); i++) {
            for (int j = i + 1; j < lista.size(); j++) {
                if (lista.get(i).equals(lista.get(j))) {
                    repetidos.add(lista.get(j));

                }
            }
        }

        System.out.println("SET sem duplicatas ===>  " + set.size());
        System.err.println("Set ===> " + set);
        System.out.println("Repetidos com os repetidos ===>  " + repetidos.size());
        System.err.println("Repetidos ===> " + repetidos);
        System.err.println("MencaoMap");
        System.err.println(mencaoMap);

        return String.valueOf(retorno);
    }

}
