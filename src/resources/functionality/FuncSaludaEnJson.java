package resources.functionality;

import javax.ws.rs.core.Response;

import xmlBinding.jaxb.FuncPostmanCalc.SaludaEnJsonOutput;

public class FuncSaludaEnJson {

	private String input;
	
	public FuncSaludaEnJson(String input) {
		this.input = input;
	}
	
	public Response process() {
		try {
			return _process();
		} catch (Exception e) {
			//Evita que un stackTrace sea enviado al usuario
			return Response.status(400).build();
		}
	}
	
	private Response _process() {
		SaludaEnJsonOutput output = new SaludaEnJsonOutput();
		try {
			String res = "Hola ";
			if (input != null) {
				res += input;
			}
			
			output.setResultado(res);
		} catch (Exception e) {
			throw e;
		}
		
		return Response.status(201).entity(output).build();
	}
}
