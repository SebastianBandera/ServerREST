package params;

public abstract class Global extends GlobalAbstract {

	public static String getSeparator() {
		return SEPARATOR;
	}
	
	public static String getAppContext() {
		return APP_CONTEXT;
	}
	
	public static String getAppName() {
		return APP_NAME;
	}
	
	public static String getAppRestServices() {
		return APP_REST_SERVICES;
	}
	
	public static String getPackageForServices() {
		return PACKAGE_FOR_SERVICES;
	}
	
	public static String getAppPath() {
		String result = "";
		try {
			String wtpDeploy = System.getProperty("wtp.deploy");
			
			result = Global.go(wtpDeploy, Global.getAppName());
		} catch (Exception e) {
			result = "";
		}

		return result;
	}

	public static String go(String ... nodes) {
		StringBuilder sb = new StringBuilder();
		if (nodes != null) {
			for (int i = 0; i < nodes.length; i++) {
				if (i != 0) {
					sb.append(Global.getSeparator());
				}
				sb.append(nodes[i]);
			}
		}
		return sb.toString();
	}
}
