/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.richroid.builditbigger.backend;

import com.example.Joker;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.richroid.com",
                ownerName = "backend.builditbigger.richroid.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    /**
     * A simple endpoint method to retrieve a joke
     * http://localhost:8080/_ah/api/myApi/v1/fetchJoke
     */
    @ApiMethod(name = "fetchJoke")
    public MyBean fetchJoke()
    {
        MyBean response = new MyBean();
        Joker joker = new Joker();
        String joke = joker.getJoke();
        response.setData(joke);
        return response;
    }

}
