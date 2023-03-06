package webscraper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Entry {

    public static void main(String[] args) {
        
        String url = "https://www.realtor.com/realestateandhomes-search/Stockton_CA/show-newest-listings";

        try {
            Document doc = Jsoup.connect(url).get();
            Elements listings = doc.select(".fallBackImgWrap");
            File file = new File ( "fikri.txt" );
            if ( ! file . exists ()) { 
            	file.createNewFile(); 
            	}
            FileWriter fw = new FileWriter(file.getAbsoluteFile()); 
            BufferedWriter bw = new BufferedWriter(fw); 
            if (listings.isEmpty()) {
                System.out.println("No listings found.");
            } else {
                for (Element listing : listings) {
                    String location = listing.select(".srp-address-redesign").text();
                    String price = listing.select(".gipzbd").text().replace("From", "");
                    String infos = listing.select(".meta-value").text();
                    String owner = listing.select(".ellipsis").text().replace(location,"");
                    String status = listing.select(".statusText").text();
                    String[] result = infos.split(" ");
                    ArrayList<String> info= new ArrayList<String>();
                    for(int i=0;i<4;i++) {
                    	if(i<result.length)
                    		info.add(result[i]);
                    	else
                    		info.add("Not Specified");
                    }
                    
               
                    bw.write(owner); bw.write("\n");
                    bw.write(" Location : " + location); bw.write("\n");
                    bw.write(" Price : " + price); bw.write("\n");
                    bw.write(" Status : " + status); bw.write("\n");
                    bw.write(" bed : " + info.get(0)); bw.write("\n");
                    bw.write(" bath : " + info.get(1)); bw.write("\n");
                    bw.write(" sqft : " + info.get(2)); bw.write("\n");
                    bw.write(" sqft lot : " + info.get(3)); bw.write("\n");
                    bw.write("\n");
                    

                }
                
            }
            bw.close();
            
        } catch (IOException e) {
            System.err.println("Error fetching URL: " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("Error parsing HTML: " + e.getMessage());
        }
        
    }
}
