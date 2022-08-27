package resources.functionality;

import javax.ws.rs.core.Response;

import xmlBinding.jaxb.FuncPostmanCalc.PostmanCalcInput;
import xmlBinding.jaxb.FuncPostmanCalc.PostmanCalcOutput;

public class FuncPostmanCalc {

	private PostmanCalcInput input;
	
	public FuncPostmanCalc(PostmanCalcInput input) {
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
		PostmanCalcOutput output = new PostmanCalcOutput();
		try {
			int n1 = Integer.parseInt(input.getA());
			int n2 = Integer.parseInt(input.getB());
			int res = n1 + n2;
			
			output.setResultado(res);
		} catch (Exception e) {
			throw e;
		}
		
		return Response.status(201).entity(output).build();
	}
}
