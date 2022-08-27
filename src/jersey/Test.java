package jersey;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import resources.functionality.FuncPostmanCalc;
import resources.functionality.FuncSaludaEnJson;
import xmlBinding.jaxb.FuncPostmanCalc.PostmanCalcInput;

@Path("/test")
public class Test {

	@GET
	@Path("{alias:bienvenida|bienvenido}")
	@Produces(MediaType.TEXT_PLAIN)
	public String bienvenida() {
		return "Hola Mundo!";
	}
	
	@GET
	@Path("{alias:saludo|saluda}")
	@Produces(MediaType.TEXT_PLAIN)
	public String saludo(@QueryParam("nombre") String nombre) {
		if (nombre == null) {
			return "Hola";
		}
		return "Hola " + nombre;
	}
	
	@GET
	@Path("{alias:saludoenjson|saludaenjson}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response saludoenjson(@QueryParam("nombre") String nombre) {
		//Para separar un poco de esta clase la implementacion de la funcion.
		//Se llama a una clase encargada de procesar este servicio.
		return (new FuncSaludaEnJson(nombre)).process();
	}
	
	@GET
	@Path("{alias:suma|sumar}")
	@Produces(MediaType.TEXT_PLAIN)
	public String suma(@QueryParam("a") String a, @QueryParam("b") String b) {
		try {
			int n1 = Integer.parseInt(a);
			int n2 = Integer.parseInt(b);
			int res = n1 + n2;
			
			return "La suma entre " + a + " + " + b + " es " + res;
		} catch (Exception e) {
			return "No se pudo realizar la suma";
		}
	}
	
	@POST
	@Path("/postmancalc")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response postmanCalc(PostmanCalcInput input) {
		//Para separar un poco de esta clase la implementacion de la funcion.
		//Se llama a una clase encargada de procesar este servicio.
		return (new FuncPostmanCalc(input)).process();
	}
	
	@GET
	@Path("{alias:index}")
	@Produces(MediaType.TEXT_HTML)
	public Response index() {
		URI location = null;
		try {
			location = new URI("/rest/");
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
		return Response.temporaryRedirect(location).build();
	}
}
