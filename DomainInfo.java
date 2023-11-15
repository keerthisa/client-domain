import java.io.*;
import java.net.*;
import java.util.*;

public class DomainInfo {

    public static void main(String[] args) throws UnknownHostException, IOException {
        if (args.length != 1) {
            System.out.println("Usage: java DomainInfo ");
            return;
        }

        String domain = args[0];

        //server
        Socket socket = new Socket("whois.internic.net", 43);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        // query
        PrintWriter writer = new PrintWriter(out);
        writer.println(domain);
        writer.flush();

        //  response
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        Map<String, String> whoisData = new HashMap<>();
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(": ");
            whoisData.put(parts[0].trim(), parts[1].trim());
        }

        // Print information
        System.out.println("Domain: " + domain);
        System.out.println("Expiry Date: " + whoisData.get("Expiration Date"));
        System.out.println("Subnets: ");
        for (String subnet : whoisData.get("NetRange").split(", ")) {
            System.out.println("- " + subnet);
        }
        System.out.println("Contact Details: ");
        System.out.println("- Registrant Name: " + whoisData.get("Registrant Name"));
        System.out.println("- Registrant Organization: " + whoisData.get("Registrant Organization"));
        System.out.println("- Registrant Email: " + whoisData.get("Registrant Email"));
    }
}
