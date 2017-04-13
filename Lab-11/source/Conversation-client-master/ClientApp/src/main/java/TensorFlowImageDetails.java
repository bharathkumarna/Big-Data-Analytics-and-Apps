import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Naga on 22-03-2017.
 */
@WebServlet(name = "TensorFlowImageDetails", urlPatterns = "/tensorFlowImageDetails")
public class TensorFlowImageDetails extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String data = buffer.toString();
        System.out.println(data);
        String output = "";
        JSONObject params = new JSONObject(data);
        JSONObject result = params.getJSONObject("result");
        JSONObject parameters = result.getJSONObject("parameters");
        if (parameters.get("null").toString().equals("clear")){
            Data data_ob = Data.getInstance();
            JSONObject js1 = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(" ");
            js1.put("data", jsonArray);
            data_ob.setData(js1.toString());
            data_ob.setFlag(true);
            JSONObject js = new JSONObject();
            js.put("speech", "screen is cleared");
            js.put("displayText", "screen is cleared");
            js.put("source", "image database");
            output = js.toString();
        }
        else{
            String classs = parameters.get("climate").toString();
            JSONObject js1 = new JSONObject();

            JSONArray jsonArray = new JSONArray();
            jsonArray.put("https://aos.iacpublishinglabs.com/question/aq/1400px-788px/what-effect-dose-acid-rain-have-on-the-environment_9f924552-1127-457a-a767-44457c3a3e83.jpg?domain=cx.aos.ask.com");
            jsonArray.put("http://www.conserve-energy-future.com/wp-content/uploads/2013/04/Atmospheric_Pollution_water_droplets.jpg");
            jsonArray.put("http://images.hngn.com/data/images/full/10505/scientists-map-earth-regions-and-ecosystems-most-vulnerable-to-climate-change.jpg?w=650");
            jsonArray.put("http://images.csmonitor.com/csm/2016/01/957944_1_ice_standard.jpg?alias=standard_900x600");
            jsonArray.put("http://55510d9f7920f3b5d825-17c608ed19d9debb708dcf2aadbcc929.r37.cf2.rackcdn.com/d440f798cab4bf6fbb8c690b18fcd370.jpg");
            jsonArray.put("https://news.agu.org/files/2013/06/pr_2011-09-hi-res.jpg");
            js1.put("imageBase64", jsonArray);
              String url="https://afternoon-bastion-87048.herokuapp.com";
 //           String url = "http://ec2-34-200-227-114.compute-1.amazonaws.com/";
//            String url = "https://mighty-headland-54930.herokuapp.com";
//            String url = "https://gentle-ocean-30903.herokuapp.com/api/predict";


//        String url = "https://image-details.herokuapp.com/imageDetails";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//        wr.writeBytes(urlParameters);
            wr.writeBytes(js1.toString());
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();

            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String[] c = response.toString().replace("[","").replace("]","").replace("'","").split(",");
            Data data_ob = Data.getInstance();
            JSONObject newData=new JSONObject();
            JSONArray js = new JSONArray();
            for (int i=0; i<c.length; i++){
                if (c[i].trim().equals(classs))
                    js.put(jsonArray.get(i));
            }
            newData.put("data", js);
            data_ob.setData(newData.toString());
            //print result
//            System.out.println(response.toString());
            JSONObject js2 = new JSONObject();
            js2.put("speech", "Images are displayed on the screen");
            js2.put("displayText", "Images are displayed on the screen");
            js2.put("source", "database");
            output = js2.toString();
        }

        resp.setHeader("Content-type", "application/json");
        resp.getWriter().write(output);
    }
}
