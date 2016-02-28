package Util;

import Model.Railway;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.io.IOException;


/**
 * Created by tommy on 2016/2/18.
 */
public class Init {
    public static void main(String[] args){
        JsonFile jf = new JsonFile();
        Gson gson = new Gson();

        // TODO Auto-generated method stub

        //create a json object to store objects
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("railway",jf.getRailway());
//        jsonObject.put("signal", jf.getSignal());
//        jsonObject.put("section", jf.getSection());
//        System.out.println(jsonObject);

        try {
//            jf.writeFile("./src/test.json", jsonObject.toString());
            jf.writeFile("./src/test.json", gson.toJson(jf.getRailway()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String jsonStr = jf.readFile("./src/test.json");
        Railway railway = gson.fromJson(jsonStr,Railway.class);
//        System.out.println(railway.getSignals().size()+" "+railway.getSections().size());
        //convert string to json object
        JSONObject jo = new JSONObject(jsonStr);
//        System.out.println(jo.get("signal"));
        //convert string to list
//        List<Signal> signals = gson.fromJson(jo.get("signal").toString(),new TypeToken<List<Signal>>(){}.getType());

//        for (int i = 0; i < signals.size(); i++) {
//            Signal s = signals.get(i);
//            System.out.println(s.getName());
//        }

    }
}
