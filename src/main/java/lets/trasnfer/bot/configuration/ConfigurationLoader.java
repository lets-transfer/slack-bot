package lets.trasnfer.bot.configuration;

import org.cfg4j.provider.ConfigurationProvider;
import org.cfg4j.provider.ConfigurationProviderBuilder;
import org.cfg4j.source.ConfigurationSource;
import org.cfg4j.source.classpath.ClasspathConfigurationSource;
import org.cfg4j.source.context.filesprovider.ConfigFilesProvider;

import java.nio.file.Paths;
import java.util.Collections;

public class ConfigurationLoader {

	public static <T> T load(Class<T> klass) {
		String path = resolveFilePath(klass);
		return load(klass, path);
	}

	private static <T> T load(Class<T> klass, String path) {
		ConfigFilesProvider configFilesProvider = () -> Collections.singletonList(Paths.get(path));
		ConfigurationSource source = new ClasspathConfigurationSource(configFilesProvider);

		ConfigurationProvider provider = new ConfigurationProviderBuilder()
				.withConfigurationSource(source)
				.build();

		return provider.bind("", klass);
	}

	private static <T> String resolveFilePath(Class<T> klass) {
		Configuration configurationAnnotation = klass.getAnnotation(Configuration.class);
		return configurationAnnotation.path();
	}

}
