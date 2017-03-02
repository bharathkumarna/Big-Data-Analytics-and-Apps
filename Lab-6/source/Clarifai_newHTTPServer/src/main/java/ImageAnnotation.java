import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry;
import okhttp3.OkHttpClient;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.typography.hershey.HersheyFont;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class ImageAnnotation { public static String summary="",words="";
    public static void main(String[] args) throws IOException {

        final ClarifaiClient client = new ClarifaiBuilder("CrhSEW7UR2nDOfGk2DpoeSOj1UvmgE0DemVXyoT9", "R2f4_jf9ZJC4LnNT6h2Yyv6Q6jCBbZPJcfHaIxbb")
                .client(new OkHttpClient()) // OPTIONAL. Allows customization of OkHttp by the user
                .buildSync(); // or use .build() to get a Future<ClarifaiClient>
        client.getToken();

        File file = new File("output/mainframes");
        File[] files = file.listFiles();
        TreeMap test = new TreeMap(Collections.reverseOrder());
        TreeMap test1 = new TreeMap(Collections.reverseOrder());
        for (int i=0; i<files.length;i++){
            ClarifaiResponse response = client.getDefaultModels().generalModel().predict()
                    .withInputs(
                            ClarifaiInput.forImage(ClarifaiImage.of(files[i]))
                    )
                    .executeSync();
            List<ClarifaiOutput<Concept>> predictions = (List<ClarifaiOutput<Concept>>) response.get();
            MBFImage image = ImageUtilities.readMBF(files[i]);
            int x = image.getWidth();
            int y = image.getHeight();



            List<Concept> data = predictions.get(0).data();

            for (int j = 0; j < 3;j++) {
                //System.out.println(data.get(j).name() + " - " + data.get(j).value());
                test.put(data.get(j).name(),data.get(j).value()+i);
                image.drawText(data.get(j).name(), (int)Math.floor(Math.random()*x), (int) Math.floor(Math.random()*y), HersheyFont.ASTROLOGY, 20, RGBColour.RED);
            }
            DisplayUtilities.displayName(image, "image" + i);
        }
//Tree Map 1
        int z=0;
        Set set = test.entrySet();
        Iterator count = set.iterator();
        while(count.hasNext()) {
            z++;
            Map.Entry sample = (Map.Entry)count.next();
            test1.put(sample.getValue(),sample.getKey());

        }
//Tree Map2
        System.out.print("From the video, the most identified unique annotations are ");
        summary+="From the video, the most identified unique annotations are ";
        Set set1 = test1.entrySet();
        Iterator count1 = set1.iterator();
        while(count1.hasNext()) {
            Map.Entry sample1 = (Map.Entry) count1.next();
            System.out.print(", "+sample1.getValue());
            summary+=", "+sample1.getValue();

        }
        System.out.print(".");
        System.out.println();
        System.out.print("The identified words with frame numbers are ");
        words+="\nIdentified words with Frame Numbers:\n ";
        int a[]=new int[z],x=0;
        String arr[]=new String[z];
        Set set2 = test1.entrySet();
        Iterator count2 = set2.iterator();
        while(count2.hasNext()) {
            Map.Entry sample1 = (Map.Entry)count2.next();
            System.out.print(",");
            System.out.print(sample1.getValue()+" from frame "+((Float)sample1.getKey()).intValue());
            words+="\n"+sample1.getValue()+" from frame"+((Float)sample1.getKey()).intValue();
        }
        System.out.print(".");
        HttpServer server = HttpServer.create(new InetSocketAddress("192.168.1.146",8082), 0);
        server.createContext("/get_img", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("\n---------Waiting for Request-----------");

        }
    static class MyHandler implements HttpHandler {

        public void handle(HttpExchange t) throws IOException {
            String response = "Summary of the video:\n\n"+summary+".\n\n\n"+words;

            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    }

