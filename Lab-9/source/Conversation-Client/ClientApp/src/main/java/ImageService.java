import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Naga on 13-03-2017.
 */
@WebServlet(name = "ImageService", urlPatterns = "/ImageService")
public class ImageService extends HttpServlet {
    public static String recognize(String imageUrl) {
// Provide your Client ID
        String CLIENT_ID = "CrhSEW7UR2nDOfGk2DpoeSOj1UvmgE0DemVXyoT9";

        // Provider Your Client Secret Key
        String CLIENT_SECRET_KEY = "R2f4_jf9ZJC4LnNT6h2Yyv6Q6jCBbZPJcfHaIxbb";
        // Defining List Object
        List<String> resultList = new ArrayList<String>();

        if(imageUrl != null) {

            final ClarifaiClient client = new ClarifaiBuilder(CLIENT_ID, CLIENT_SECRET_KEY).buildSync();

            final List<ClarifaiOutput<Concept>> predictionResults =
                    client.getDefaultModels().generalModel() // You can also do client.getModelByID("id") to get custom models
                            .predict()
                            .withInputs(
                                    ClarifaiInput.forImage(ClarifaiImage.of(imageUrl))
                            )
                            .executeSync()
                            .get();

            if (predictionResults != null && predictionResults.size() > 0) {

                // Prediction List Iteration
                for (int i = 0; i < predictionResults.size(); i++) {

                    ClarifaiOutput<Concept> clarifaiOutput = predictionResults.get(i);

                    List<Concept> concepts = clarifaiOutput.data();

                    if(concepts != null && concepts.size() > 0) {
                        for (int j = 0; j < concepts.size(); j++) {

                            resultList.add(concepts.get(j).name());
                        }
                    }
                }
            }

        }
        String annotations="";
        // Iteration of Result
        for(String result : resultList) {

            //System.out.println(result);
            annotations+=result+", ";
        }
        return annotations;

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = req.getReader();
        String response="";
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
        if (parameters.get("climate").toString().equals("climate change pictures")) {
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            jsonArray.put("https://timedotcom.files.wordpress.com/2015/11/climate-change-drought-terrorism.jpeg?w=1100&quality=85");
            jsonArray.put("https://aos.iacpublishinglabs.com/question/aq/700px-394px/acid-rain-affect-buildings_9100801efb52ac63.jpg?domain=cx.aos.ask.com");
            jsonArray.put("http://images.gawker.com/rwrsuud1wijfnvkocpf1/c_scale,fl_progressive,q_80,w_800.jpg");
            jsonArray.put("http://fivestaradk.com/wp-content/uploads/2011/08/Peak-in-middle-of-a-storm.jpg");
            jsonArray.put("http://www.citi.io/wp-content/uploads/2015/02/178-9.jpg");
            jsonArray.put("http://socialistparty.ie/wp-content/uploads/2016/09/Climate-chang-e.jpg");
            jsonObject.put("data", jsonArray);
            output = jsonObject.toString();
            Data data_ob = Data.getInstance();
            data_ob.setData(output);
            data_ob.setFlag(true);
            JSONObject js = new JSONObject();
            js.put("speech", "climate change pictures are displayed");
            js.put("displayText", "climate change pictures are displayed");
            js.put("source", "image database");
            response = js.toString();
        }
        else if (parameters.get("climate").toString().equals("acid rain")) {
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            jsonArray.put("https://aos.iacpublishinglabs.com/question/aq/1400px-788px/what-effect-dose-acid-rain-have-on-the-environment_9f924552-1127-457a-a767-44457c3a3e83.jpg?domain=cx.aos.ask.com");
            jsonArray.put("https://aos.iacpublishinglabs.com/question/aq/700px-394px/acid-rain-affect-buildings_9100801efb52ac63.jpg?domain=cx.aos.ask.com");
            jsonArray.put("http://www.conserve-energy-future.com/wp-content/uploads/2013/04/Atmospheric_Pollution_water_droplets.jpg");
            jsonArray.put("http://www.protectadks.org/wp-content/uploads/2012/10/Irene-d.gif");
            jsonArray.put("http://images.hngn.com/data/images/full/10505/scientists-map-earth-regions-and-ecosystems-most-vulnerable-to-climate-change.jpg?w=650");
            jsonArray.put("http://fivestaradk.com/wp-content/uploads/2011/08/Peak-in-middle-of-a-storm.jpg");
            jsonObject.put("data", jsonArray);
            output = jsonObject.toString();
            Data data_ob = Data.getInstance();
            data_ob.setData(output);
            data_ob.setFlag(true);
            JSONObject js = new JSONObject();
            js.put("speech", "acid rain pictures are displayed");
            js.put("displayText", "acid rain pictures are displayed");
            js.put("source", "image database");
            response = js.toString();
        }
        else if (parameters.get("climate").toString().equals("ice sheet melting")){
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            jsonArray.put("http://images.csmonitor.com/csm/2016/01/957944_1_ice_standard.jpg?alias=standard_900x600");
            jsonArray.put("http://climatenewsnetwork.net/wp-content/uploads/2016/02/CROP-Antarctic-iceberg-800x400.jpg");
            jsonArray.put("http://media.npr.org/assets/img/2016/10/24/antarctic_custom-c054aaacc042163d09a0694b26f5822e4a35714c-s900-c85.jpg");
            jsonArray.put("http://55510d9f7920f3b5d825-17c608ed19d9debb708dcf2aadbcc929.r37.cf2.rackcdn.com/d440f798cab4bf6fbb8c690b18fcd370.jpg");
            jsonArray.put("http://www.redorbit.com/media/uploads/2012/12/antarcticice.jpg");
            jsonArray.put("https://news.agu.org/files/2013/06/pr_2011-09-hi-res.jpg");
            jsonObject.put("data", jsonArray);
            output = jsonObject.toString();
            Data data_ob = Data.getInstance();
            data_ob.setData(output);
            data_ob.setFlag(true);
            JSONObject js = new JSONObject();
            js.put("speech", "ice sheet melting pictures are displayed");
            js.put("displayText", "ice sheet melting pictures are displayed");
            js.put("source", "image database");
            response = js.toString();
        }
        else if (parameters.get("climate").toString().equals("warming oceans")){
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            jsonArray.put("http://www.oeschger.unibe.ch/unibe/portal/fak_naturwis/g_dept_kzen/d_c_oeschger/content/e65551/e387548/Eisberg_1_eng.jpeg");
            jsonArray.put("http://wallstreetpit.com/wp-content/uploads/2017/01/global-warming-pollution.jpg");
            jsonArray.put("http://www.climatechangenews.com/files/2014/11/hot-weather-578x337.jpg");
            jsonArray.put("https://news.uci.edu/wp-content/uploads/2017/01/07_Stenzel_Greenland_2014_0818_0263_.jpg");
            jsonArray.put("http://www.commondreams.org/sites/default/files/styles/cd_large/public/headlines/ocean_2.jpg?itok=_tYNVjV_");
            jsonArray.put("https://grist.files.wordpress.com/2013/07/ocean-sunset-red.jpg?w=1200&h=675&crop=1");
            jsonObject.put("data", jsonArray);
            output = jsonObject.toString();
            Data data_ob = Data.getInstance();
            data_ob.setData(output);
            data_ob.setFlag(true);
            JSONObject js = new JSONObject();
            js.put("speech", "warming ocean pictures are displayed");
            js.put("displayText", "warming ocean pictures are displayed");
            js.put("source", "image database");
            response = js.toString();
        }
        else if (parameters.get("climate").toString().equals("clarifai api")){
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            jsonArray.put("https://timedotcom.files.wordpress.com/2015/11/climate-change-drought-terrorism.jpeg?w=1100&quality=85");
            jsonArray.put("http://images.csmonitor.com/csm/2016/01/957944_1_ice_standard.jpg?alias=standard_900x600");
            jsonArray.put("http://www.oeschger.unibe.ch/unibe/portal/fak_naturwis/g_dept_kzen/d_c_oeschger/content/e65551/e387548/Eisberg_1_eng.jpeg");
            jsonObject.put("data", jsonArray);
            output = jsonObject.toString();
            Data data_ob = Data.getInstance();
            data_ob.setData(output);
            data_ob.setFlag(true);
            JSONObject js = new JSONObject();
            js.put("speech", "redirecting to clarifai api page");
            js.put("displayText", "redirecting to clarifai api page");
            js.put("source", "image database");
            response = js.toString();
        }

        else if (parameters.get("null").toString().equals("clear")){
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
            response = js.toString();
        }
        else if (parameters.get("image").toString().equals("one")){
            Data data_ob = Data.getInstance();
            JSONObject js1 = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            jsonArray.put("https://timedotcom.files.wordpress.com/2015/11/climate-change-drought-terrorism.jpeg?w=1100&quality=85");
            jsonArray.put("http://images.csmonitor.com/csm/2016/01/957944_1_ice_standard.jpg?alias=standard_900x600");
            jsonArray.put("http://www.oeschger.unibe.ch/unibe/portal/fak_naturwis/g_dept_kzen/d_c_oeschger/content/e65551/e387548/Eisberg_1_eng.jpeg");
            js1.put("data", jsonArray);
            data_ob.setData(js1.toString());
            data_ob.setFlag(true);
            JSONObject js = new JSONObject();
            String imageUrl = "https://timedotcom.files.wordpress.com/2015/11/climate-change-drought-terrorism.jpeg?w=1100&quality=85";
            String words=recognize(imageUrl);
            js.put("speech", "From image one we can see "+words );
            js.put("displayText", "From image one we can see "+words );
            js.put("source", "image database");
            response = js.toString();
        }
        else if (parameters.get("image").toString().equals("two")){
            Data data_ob = Data.getInstance();
            JSONObject js1 = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            jsonArray.put("https://timedotcom.files.wordpress.com/2015/11/climate-change-drought-terrorism.jpeg?w=1100&quality=85");
            jsonArray.put("http://images.csmonitor.com/csm/2016/01/957944_1_ice_standard.jpg?alias=standard_900x600");
            jsonArray.put("http://www.oeschger.unibe.ch/unibe/portal/fak_naturwis/g_dept_kzen/d_c_oeschger/content/e65551/e387548/Eisberg_1_eng.jpeg");
            js1.put("data", jsonArray);
            data_ob.setData(js1.toString());
            data_ob.setFlag(true);
            JSONObject js = new JSONObject();
            String imageUrl = "http://images.csmonitor.com/csm/2016/01/957944_1_ice_standard.jpg?alias=standard_900x600";
            String words=recognize(imageUrl);
            js.put("speech", "From image two we can see "+words );
            js.put("displayText", "From image two we can see "+words );
            js.put("source", "image database");
            response = js.toString();
        }
        else if (parameters.get("image").toString().equals("three")){
            Data data_ob = Data.getInstance();
            JSONObject js1 = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            jsonArray.put("https://timedotcom.files.wordpress.com/2015/11/climate-change-drought-terrorism.jpeg?w=1100&quality=85");
            jsonArray.put("http://images.csmonitor.com/csm/2016/01/957944_1_ice_standard.jpg?alias=standard_900x600");
            jsonArray.put("http://www.oeschger.unibe.ch/unibe/portal/fak_naturwis/g_dept_kzen/d_c_oeschger/content/e65551/e387548/Eisberg_1_eng.jpeg");
            js1.put("data", jsonArray);
            data_ob.setData(js1.toString());
            data_ob.setFlag(true);
            JSONObject js = new JSONObject();
            String imageUrl = "http://www.oeschger.unibe.ch/unibe/portal/fak_naturwis/g_dept_kzen/d_c_oeschger/content/e65551/e387548/Eisberg_1_eng.jpeg";
            String words=recognize(imageUrl);
            js.put("speech","From image three we can see "+words );
            js.put("displayText", "From image three we can see "+words);
            js.put("source", "image database");
            response = js.toString();
        }
        resp.setHeader("Content-type", "application/json");
        resp.getWriter().write(response);
    }
}

