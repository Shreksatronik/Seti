public class Main {
    public static void main(String[] args) {

        new Server((req, resp) -> {
            String name = req.url.substring(7);
            return "<html><body><h1>Hello " + name + " </h1></body></html>";
        }).bootstrap();
    }
}