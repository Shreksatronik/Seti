
import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    public static void HttpRequest(Socket socket) throws IOException {

        OutputStream os = socket.getOutputStream();

        InputStream inputStream = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        Request request = new Request(reader);
        if (!request.parse()) {
            respond(500, "Unable to parse request", os);
            return;
        }
        log.info(DELEMITER);
        log.info("Request path: " + request.getPath());
        log.info("Request Query: " + request.getQueryParameters());
        log.info(DELEMITER);

        String responseHtml = null;

        switch (request.getPath()) {
            case "/msg":
                responseHtml = handleMessagePage(request);
                break;
            default:
                responseHtml = handleRequestMethod(request, HOME_PAGE);
        }

        String result = getResponse(responseHtml) + responseHtml;
        os.write(result.getBytes());
        os.flush();
        reader.close();
    }

    private static String getResponse(String responseHtml) {
        return "HTTP/1.1 200 OK\r\n"
                + "Server: Server\r\n"
                + "Content-Type: text/html\r\n"
                + "Content-Length: "
                + responseHtml.length()
                + "\r\n"
                + "Connection: close\r\n\r\n";
    }

    private static void respond(int statusCode, String msg, OutputStream out) throws IOException {
        String responseLine = "HTTP/1.1 " + statusCode + " " + msg + "\r\n\r\n";
        log.info(responseLine);
        out.write(responseLine.getBytes());
    }

    private static String handleMessagePage(Request request) throws IOException {

        return handleRequestMethod(request, MESSAGE_PAGE);
    }
    }
