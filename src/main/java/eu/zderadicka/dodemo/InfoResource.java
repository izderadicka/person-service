package eu.zderadicka.dodemo;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/info")
public class InfoResource {
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String info() {
        Map<String,String> env = System.getenv();
        StringWriter out = new StringWriter();
        List<String> keys = new ArrayList<String>(env.keySet());
        Collections.sort(keys);
        for (String key: keys) {
            out.write(key);
            out.write(": ");
            out.write(env.get(key));
            out.write("\n");
        }

        return out.toString();
    }

}
