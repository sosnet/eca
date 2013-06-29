package rest;

import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import datatypes.PointSet;

public class RestClient {

  private static RestClient instance;

  private WebResource res;

  public static RestClient getInstance() {
    if (instance == null)
      instance = new RestClient();

    return instance;

  }

  private RestClient() {
    this.res = Client.create().resource(RestConf.URL_SERVICE);
    
  }

  @SuppressWarnings("unchecked")
  public String queryPoints(PointSet set) {
    @SuppressWarnings("rawtypes")
    MultivaluedMap queryParams = new MultivaluedMapImpl();
    queryParams.add(RestConf.KEY_QUERY, set.stringify());
    return res.queryParams(queryParams).get(String.class);
  }

}
