import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * The class contains several very simple tests of the functionality of the calculator.
 *
 * @author Syniuk Valentyn
 */
public class GETRequestTest {

    private GETRequest request = new GETRequest();
    private String query = "http://localhost:8080/CalculatorServlet";

    @Test
    public void testGETRequest() throws IOException {
        Assert.assertFalse(request.createGETRequest(query).isEmpty());
    }

    @Test
    public void testCheckResponseCode() throws IOException {
        request.createGETRequest(query);
        Assert.assertTrue(request.checkResponseCode());
    }

    @Test
    public void testCheckContainsHTMLTag() throws IOException {
        String result = request.createGETRequest(query);
        Assert.assertTrue(result.contains("<html>"));
    }

}