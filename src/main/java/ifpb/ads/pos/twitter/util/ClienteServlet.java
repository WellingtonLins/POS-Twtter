package ifpb.ads.pos.twitter.util;

import ifpb.ads.pos.twitter.AuthenticatorOfTwitter;
import ifpb.ads.pos.twitter.Credentials;
import ifpb.ads.pos.twitter.EndpointInTwitter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Document   ClienteServlet
 * @Date  09/04/2018 @Time 10:08:08
 * @author Wellington Lins Claudino Duarte
 * @mail wellingtonlins2013@gmail.com
 */ 
@WebServlet(name="ClienteServlet", urlPatterns={"/cliente"})
public class ClienteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       response.setContentType("text/html;charset=UTF-8");
    Credentials c = new Credentials("", "");
        AuthenticatorOfTwitter authenticator = new AuthenticatorOfTwitter(c);
        EndpointInTwitter endpoint = new EndpointInTwitter("POST", "https://api.twitter.com/oauth/request_token");
        String authorization = authenticator.in(endpoint).authenticate();
        System.out.println("authorization = " + authorization);

        Client newBuilder = ClientBuilder.newBuilder().build();
        WebTarget target = newBuilder.target("https://api.twitter.com/oauth/request_token");

        Response post = target.request().accept(MediaType.APPLICATION_JSON)
                .header("Authorization", authorization)
                .post(Entity.json(""));

        String oauth_token = post.readEntity(String.class);
        System.out.println("readObject = " + oauth_token);
       
        response.sendRedirect("https://api.twitter.com/oauth/authenticate?" + oauth_token);
        
    }
  }
