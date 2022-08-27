package discover;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class DiscoverUtils {

	public static ArrayList<String> getClassNamesFromPackage(String packageName) {
		ArrayList<String> names = new ArrayList<String>();
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			URL packageURL;

			packageName = packageName.replace(".", "/");
			packageURL = classLoader.getResource(packageName);

			if (packageURL.getProtocol().equals("jar")) {
				String jarFileName;
				JarFile jf;
				Enumeration<JarEntry> jarEntries;
				String entryName;

				jarFileName = URLDecoder.decode(packageURL.getFile(), "UTF-8");
				jarFileName = jarFileName.substring(5, jarFileName.indexOf("!"));
				System.out.println(">" + jarFileName);
				jf = new JarFile(jarFileName);
				jarEntries = jf.entries();
				while (jarEntries.hasMoreElements()) {
					entryName = jarEntries.nextElement().getName();
					if (entryName.startsWith(packageName) && entryName.length() > packageName.length() + 5) {
						entryName = entryName.substring(packageName.length(), entryName.lastIndexOf('.'));
						names.add(entryName);
					}
				}
				jf.close();
			} else {
				URI uri = new URI(packageURL.toString());
				File folder = new File(uri.getPath());
				File[] contenuti = folder.listFiles();
				String entryName;
				for (File actual : contenuti) {
					entryName = actual.getName();
					entryName = entryName.substring(0, entryName.lastIndexOf('.'));
					names.add(entryName);
				}
			}
		} catch (Exception e) {
			System.out.println("System fail.");
		}
		
		return names;
	}
}
