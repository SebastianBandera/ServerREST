package discover;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import params.Global;
import system.DiscoverIgnore;

import javax.ws.rs.QueryParam;

public class ServiceInfo {
	
	private final static String GET  = "GET";
	private final static String POST = "POST";
	private final static String SEP_REGEX = ":";

	private static List<ItemServiceInfo> services;
	private static List<ItemServiceInfo> servicesGET;
	private static List<ItemServiceInfo> servicesPOST;
	
	static {
		loadInfo();
	}
	
	//Cuidado, no modificar los elementos dentro de la lista
	public static List<ItemServiceInfo> getServices() {
		return services;
	}

	public static List<ItemServiceInfo> getServicesGET() {
		return servicesGET;
	}

	public static List<ItemServiceInfo> getServicesPOST() {
		return servicesPOST;
	}

	private static void loadInfo() {
		System.out.println("Inicio carga de informacion de los servicios publicados");
		
		services     = new ArrayList<ItemServiceInfo>(50);
		servicesGET  = new ArrayList<ItemServiceInfo>(25);
		servicesPOST = new ArrayList<ItemServiceInfo>(25);
		try {
			List<String> clases = DiscoverUtils.getClassNamesFromPackage(Global.getPackageForServices());

			for (int i = 0; i < clases.size(); i++) {
				processClass(clases.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		StringBuilder sb = new StringBuilder();
		sb.append("Servicios: ").append(services.size()).append(" - ");
		sb.append("GET: ").append(services.size()).append(" - ");
		sb.append("POST: ").append(servicesPOST.size());
		System.out.println(sb.toString());
		System.out.println("Fin carga de informacion de los servicios publicados");
	}

	private static void processClass(String clsName) throws ClassNotFoundException {
		Class<?> cls = Class.forName(Global.getPackageForServices() + "." + clsName, false, ServiceInfo.class.getClassLoader());
		
		javax.ws.rs.Path annPath = (javax.ws.rs.Path) cls.getAnnotation(javax.ws.rs.Path.class);

		if (annPath == null) {
			return;
		}
		
		String ruta = "/" + Global.go(Global.getAppContext(), Global.getAppRestServices(), annPath.value());

		Method[] metodos = cls.getDeclaredMethods();

		if (metodos == null) {
			return;
		}
		
		for (int i = 0; i < metodos.length; i++) {
			processMethod(metodos[i], ruta);
		}
	}

	private static void processMethod(Method metodo, String pathValue) {
		if (metodo == null) {
			return;
		}
		
		DiscoverIgnore discoverIgnore = metodo.getAnnotation(DiscoverIgnore.class);
		if (discoverIgnore != null) {
			return;
		}
		
		javax.ws.rs.Path annPath = (javax.ws.rs.Path) metodo.getAnnotation(javax.ws.rs.Path.class);
		
		if (annPath == null) {
			return;
		}
		
		ItemServiceInfo info = new ItemServiceInfo();
		info.setPath(pathValue);
		
		processQueryParams(metodo, info);
		
		javax.ws.rs.GET get = metodo.getAnnotation(javax.ws.rs.GET.class);
		
		if (get != null) {
			processMethodGET(metodo, info);
		}
		
		javax.ws.rs.POST post = metodo.getAnnotation(javax.ws.rs.POST.class);
		
		if (post != null) {
			processMethodPOST(metodo, info);
		}
	}

	private static void processMethodGET(Method metodo, ItemServiceInfo info) {
		info.setMethod(GET);
		
		processMethodServiceName(metodo, info);
		
		processMethodServiceProduces(metodo, info);
		
		services.add(info);
		
		servicesGET.add(info);
	}
	
	private static void processMethodPOST(Method metodo, ItemServiceInfo info) {
		info.setMethod(POST);
		
		processMethodServiceName(metodo, info);
		
		processMethodServiceProduces(metodo, info);
		
		javax.ws.rs.Consumes consume = metodo.getAnnotation(javax.ws.rs.Consumes.class);
		String[] consumes = consume.value();
		
		String joinConsume = consumes[0];
		
		info.setConsumes(joinConsume);
		

		services.add(info);
		
		servicesPOST.add(info);
	}
	
	private static void processQueryParams(Method metodo, ItemServiceInfo info) {
		Parameter[] parametros = metodo.getParameters();
		if (parametros != null) {
			for (int i = 0; i < parametros.length; i++) {
				javax.ws.rs.QueryParam queryParam = parametros[i].getAnnotation(QueryParam.class);
				if (queryParam != null) {
					info.getQueryParams().add(queryParam.value());
				}
			}
		}
	}
	
	private static void processMethodServiceName(Method metodo, ItemServiceInfo info) {
		javax.ws.rs.Path path = metodo.getAnnotation(javax.ws.rs.Path.class);
		String nombreYAlias = path.value();
		
		if (nombreYAlias == null) {nombreYAlias = "";};
		
		if (nombreYAlias.contains(SEP_REGEX)) {
			try {
				String[] split = nombreYAlias.split(SEP_REGEX);
				String value = split[1];
				value = value.substring(0, value.length() - 1);
				
				String[] alias = value.split("\\|");
				//El primer alias se considera nombre
				info.setName(alias[0]);
				for (int i = 1; i < alias.length; i++) {
					info.getAlias().add(alias[i]);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			info.setName(nombreYAlias);
		}
		
		String tmp = info.getPath() + "/" + info.getName();
		tmp = tmp.replace("//", "/");
		info.setPath(tmp);
	}
	
	private static void processMethodServiceProduces(Method metodo, ItemServiceInfo info) {
		javax.ws.rs.Produces produces = metodo.getAnnotation(javax.ws.rs.Produces.class);
		String[] produce = produces.value();
		
		String joinProduce = produce[0];
		
		info.setProduces(joinProduce);
	}
}
