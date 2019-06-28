package in.philo.fr;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;

/**
 * 服务端请求
 */
public class ApiCaller {

    public String get(String url, HashMap<String, String> params){

        HttpUrl rUrl = HttpUrl.parse(url);

        HashMap<String,String> orgParams  = new HashMap<>();
        int querySize = rUrl.querySize();
        for(int i = 0 ; i< querySize;i++)
        {
            orgParams.put(rUrl.queryParameterName(i) , rUrl.queryParameterValue(i));
        }

        if( params != null) {
            orgParams.putAll(params);
        }

        HttpUrl.Builder builder = new HttpUrl.Builder();

        builder.scheme(rUrl.scheme())
                .host(rUrl.host())
                .port(rUrl.port());

        rUrl.pathSegments().forEach( segment ->
            builder.addPathSegment(segment)
        );

        orgParams.forEach( (key,value) -> {
            builder.addQueryParameter(key,(String) value);
        });

        System.out.println(builder.toString());


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(rUrl).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();

            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }



    }
}
