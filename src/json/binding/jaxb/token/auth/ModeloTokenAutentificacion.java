package json.binding.jaxb.token.auth;


public class ModeloTokenAutentificacion {

	private String identificador;
	private String password;
	
	public ModeloTokenAutentificacion() {
		
	}
	
	public ModeloTokenAutentificacion(String identificador, String password) {
		this.identificador = identificador;
		this.password      = password;
	}
	
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
