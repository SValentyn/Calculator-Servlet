import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GETRequest {

    private final static Logger LOG = LogManager.getLogger(GETRequest.class);
    private HttpURLConnection connection;

    public static void main(String[] args) throws IOException {
        GETRequest request = new GETRequest();
        String query = "http://localhost:8080/CalculatorServlet";
        LOG.info(request.createGETRequest(query));
    }

    public String createGETRequest(String query) throws IOException {
        StringBuilder builder = new StringBuilder();

        connection = establishConnect(query);

        if (checkResponseCode()) {
            // getting a response from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String someString;
            while ((someString = reader.readLine()) != null) {
                builder.append(someString).append("\n");
            }
        } else {
            // in another case, the error or the answer is an empty string
            builder = new StringBuilder();
        }
        return builder.toString();
    }

    /**
     * Establishing a connection to a specific server host.
     *
     * @return connection – http connection with properties
     */
    private HttpURLConnection establishConnect(String query) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(query).openConnection();
        connection.setRequestMethod("GET");
        connection.setUseCaches(false);
        connection.setConnectTimeout(3000);
        connection.setReadTimeout(3000);
        connection.connect();
        return connection;
    }

    public boolean checkResponseCode() throws IOException {
        LOG.info(HttpURLConnection.HTTP_OK == connection.getResponseCode() ? "OK" : "ERROR.. code = " + connection.getResponseCode());
        return HttpURLConnection.HTTP_OK == connection.getResponseCode();
    }
}

