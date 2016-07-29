import java.io.File;
import java.io.FileWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class scraper
{
    public static void main(String args[])
    {
        int linkCount=2,indexCount=0; //indexCount counts the index numbers. numbered from 0 to 120 with intervals of 10
        File f=new File("..//Folklore Scrapes"); 
        FileWriter fw;
        f.mkdir(); //creates a new folder in the root directory
  
            try{
       while(indexCount<130){
        String weburl="http://www.folklore.org/ProjectView.py?project=Macintosh&index="+indexCount; //visits each Index Page @ folklore.org. There are 13 index pages
        Document doc=Jsoup.connect(weburl).get(); //fetches the DOM
        Elements els=doc.select("div.story-index-entry >div > a[href]"); //scrapes up all links to stories present in DOM 
         for(Element el : els){
         if(linkCount%2==0){ 
         String childDOM= el.absUrl("href");  
           Document story=Jsoup.connect(childDOM).get(); //fetches DOM of the story page 
           fw=new FileWriter("..//Folklore Scrapes//"+story.select("div.story-view-title").text().replaceAll("\\?","")+".txt"); //site specific case to remove ? from file name to avoid conflicts with NTFS file naming methodology
           fw.write(story.select("div.story-main-content").text()); //scrapes up the main content and writes it to a text file
           fw.close();
           System.out.println(story.select("div.story-view-title").text()); //displays story name in Terminal 
        }
        linkCount++;//two links for the same page are generated when links to stories are scraped up from DOM. This is used to avoid overwrite and unnecessary page requests.
        }
        indexCount+=10;
    }
    
}catch(Exception e){//System.out.println(e); debug purposes
        }
}
}

