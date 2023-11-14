// Source code is decompiled from a .class file using FernFlower decompiler.
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

public class DomainInfo {
   public DomainInfo() {
   }

   public static void main(String[] var0) throws UnknownHostException, IOException {
      if (var0.length != 1) {
         System.out.println("Usage: java DomainInfo <domain>");
      } else {
         String var1 = var0[0];
         Socket var2 = new Socket("whois.internic.net", 43);

         try {
            InputStream var3 = var2.getInputStream();
            OutputStream var4 = var2.getOutputStream();
            PrintWriter var5 = new PrintWriter(var4);
            var5.println(var1);
            var5.flush();
            BufferedReader var6 = new BufferedReader(new InputStreamReader(var3));
            HashMap<String, Integer> var8 = new HashMap<String, Integer>();

            while(true) {
               String var7;
               String[] var9;
               if ((var7 = var6.readLine()) == null) {
                  System.out.println("Domain: " + var1);
                  System.out.println("Expiry Date: " + (String)var8.get("Expiration Date"));
                  System.out.println("Subnets: ");
                  var9 = ((String)var8.get("NetRange")).split(", ");
                  int var10 = var9.length;

                  for(int var11 = 0; var11 < var10; ++var11) {
                     String var12 = var9[var11];
                     System.out.println("- " + var12);
                  }

                  System.out.println("Contact Details: ");
                  System.out.println("- Registrant Name: " + (String)var8.get("Registrant Name"));
                  System.out.println("- Registrant Organization: " + (String)var8.get("Registrant Organization"));
                  System.out.println("- Registrant Email: " + (String)var8.get("Registrant Email"));
                  break;
               }

               var9 = var7.split(": ");
               var8.put(var9[0].trim(), var9[1].trim());
            }
         } catch (Throwable var14) {
            try {
               var2.close();
            } catch (Throwable var13) {
               var14.addSuppressed(var13);
            }

            throw var14;
         }

         var2.close();
      }
   }
}
