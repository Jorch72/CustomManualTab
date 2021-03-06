package vexatos.manualtab.manual;

import com.google.common.base.Charsets;
import net.minecraftforge.fml.common.Loader;
import li.cil.oc.api.manual.ContentProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author Vexatos
 */
public class ConfigContentProvider implements ContentProvider {

	protected final File directory;
	protected final String prefix;

	public ConfigContentProvider(String prefix) {
		directory = new File(Loader.instance().getConfigDir() + File.separator + "manualtab");
		if(!directory.exists()) {
			directory.mkdir();
			File defLangDir = new File(directory, "en_US");
			if(!defLangDir.exists()) {
				defLangDir.mkdir();
			}
		}
		this.prefix = prefix;
	}

	@Override
	public Iterable<String> getContent(String path) {
		path = path.startsWith("/") ? path.substring(1) : path;
		if(!path.startsWith(this.prefix)) {
			return null;
		}
		InputStream stream = null;
		try {
			path = path.substring(this.prefix.length() + 1);
			File file = new File(directory, (path.startsWith("/") ? path.substring(1) : path));
			if(file.exists()) {
				stream = new FileInputStream(file);
				BufferedReader reader = new BufferedReader(new InputStreamReader(stream, Charsets.UTF_8));
				ArrayList<String> lines = new ArrayList<String>();
				String line;
				while((line = reader.readLine()) != null) {
					lines.add(line);
				}
				return lines;
			}
		} catch(Throwable t) {
			return null;
		} finally {
			if(stream != null) {
				try {
					stream.close();
				} catch(IOException ignored) {
				}
			}
		}
		return null;
	}
}
