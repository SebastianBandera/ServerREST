package json.binding.jaxb.token.auth;

public class ModeloRespuestaTokenAutentificacion {

	private int estado;
	private String mensaje;
	private String token;
	private String fechaHoraValidez;
	
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getFechaHoraValidez() {
		return fechaHoraValidez;
	}
	public void setFechaHoraValidez(String fechaHoraValidez) {
		this.fechaHoraValidez = fechaHoraValidez;
	}
}
