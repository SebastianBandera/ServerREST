package jersey;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import json.binding.jaxb.token.auth.ModeloRespuestaTokenAutentificacion;
import json.binding.jaxb.token.auth.ModeloTokenAutentificacion;

@Path("/api")
public class TokenAuth {
	
	@POST
	@Path("authorization")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response postmanCalc(ModeloTokenAutentificacion input) {
		ModeloRespuestaTokenAutentificacion output = new ModeloRespuestaTokenAutentificacion();

		if (input.getIdentificador().equals("id1") && input.getPassword().equals("ps1")) {
			output.setEstado(0);
			output.setMensaje("descripcion");
			output.setToken("jwt");
			output.setFechaHoraValidez("20220826155047");
		} else {
			output.setEstado(1);
		}
		
		return Response.status(201).entity(output).build();
	}
	
}
