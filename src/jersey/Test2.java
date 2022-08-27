package jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import system.DiscoverIgnore;

@Path("/test2")
public class Test2 {

	@GET
	@Path("bienvenida")
	@Produces(MediaType.TEXT_PLAIN)
	@DiscoverIgnore
	public String bienvenida() {
		return "Hola Mundo 2 !";
	}
}
