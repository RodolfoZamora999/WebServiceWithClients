import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyStore;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws Exception {
        final var URL = "https://localhost:8443/api/user";

        //Todo: Add a Cookie Handler
        var httpClient = HttpClient.newBuilder().
                version(HttpClient.Version.HTTP_2).sslContext(loadMyOwnSSLContext()).build();

        var request = HttpRequest.newBuilder(URI.create(URL)).GET().
                version(HttpClient.Version.HTTP_2).build();

        var responseHandler = HttpResponse.BodyHandlers.ofString();

       try {
           var response = httpClient.send(request, responseHandler);
           System.out.println("Version: " + response.version().name());
           System.out.println("status code: " + response.statusCode());
           System.out.println("body: " + response.body());

       } catch (Exception exception) {
           System.out.println("Send exception: " + exception.getMessage());
       }
    }

    /**
     * Generate a ssl context with my own TSL Certificate
     * @return a SSLContext
     * @throws Exception if file not exist
     */
    public static SSLContext loadMyOwnSSLContext() throws Exception {
        final var keyStoreFile = new File(Objects.requireNonNull(Main.class.
                getResource("client-keystore.pks12")).toURI());
        final var password = "1234";

        var keyStore = KeyStore.getInstance(keyStoreFile, password.toCharArray());
        var trustManager = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManager.init(keyStore);

        var sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManager.getTrustManagers(), null);

        return sslContext;
    }
}
