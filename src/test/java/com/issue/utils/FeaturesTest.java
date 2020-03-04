/**
 * 
 */
package com.issue.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;

import org.junit.jupiter.api.Test;

import com.issue.configuration.GlobalParams;
import com.issue.entity.Feature;
import com.issue.enums.FeatureScope;
import com.issue.iface.FeatureDao;

/**
 * The Class FeaturesTest.
 *
 * @author branislav.beno
 */
class FeaturesTest {

	/**
	 * Read all.
	 *
	 * @param reader the reader
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static String readAll(final Reader reader) throws IOException {
		StringBuilder sb = new StringBuilder();
		int c;
		while ((c = reader.read()) != -1) {
			sb.append((char) c);
		}
		return String.valueOf(sb);
	}

	/**
	 * Read file content.
	 *
	 * @param filePath the file path
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static String readFileContent(final String filePath) throws IOException {
		String content = null;
		File file = new File(filePath);
		try (Reader fileReader = new FileReader(file)) {
			content = readAll(fileReader);
		}
		return content;
	}

	/**
	 * Test features private constructors for code coverage.
	 *
	 * @throws NoSuchMethodException the no such method exception
	 */
	@Test
	void testFeaturesPrivateConstructorsForCodeCoverage() throws NoSuchMethodException {
		Class<Features> clazz = Features.class;
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		for (Constructor<?> constructor : constructors) {
			constructor.setAccessible(true);
			assertThrows(InvocationTargetException.class, constructor::newInstance);
		}
	}

	/**
	 * Test positive extract features.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	void testPositiveExtractFeatures() throws IOException {
		// Get Json string
		String jsonString = readFileContent("src/test/resources/features.json");

		// extract features from json
		FeatureDao<String, Feature> features = Features.extractFeatures(jsonString);
		assertThat(features.getAll().size()).isEqualTo(12);

		// get particular feature
		Feature feature = features.get("ISSUE-10415").get();
		assertThat(feature.getFeatureScope()).isEqualTo(FeatureScope.ADVANCED);
	}

	/**
	 * Test negative extract null features.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	void testNegativeExtractNullFeatures() throws IOException {
		// extract features from null json
		FeatureDao<String, Feature> features = Features.extractFeatures(null);

		assertThat(features.getAll().size()).isEqualTo(0);
	}

	/**
	 * Test negative features json with non existing properties file.
	 *
	 * @throws InterruptedException the interrupted exception
	 * @throws IOException          Signals that an I/O exception has occurred.
	 */
	@Test
	void testNegativeFeaturesJsonWithNonExistingPropertiesFile() throws InterruptedException, IOException {
		// Test non existing properties file
		assertThrows(FileNotFoundException.class,
				() -> Utils.provideGlobalParams("src/test/resources/application.properties"));
	}

	/**
	 * Test negative features json with false properties.
	 *
	 * @throws InterruptedException the interrupted exception
	 * @throws IOException          Signals that an I/O exception has occurred.
	 */
	@Test
	void testNegativeFeaturesJsonWithFalseProperties() throws InterruptedException, IOException {
		// provide global parameters
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_negative1_application.properties");

		// Get features
		FeatureDao<String, Feature> features = Features.createFeaturesRepo(globalParams);

		assertThat(features.getAll().size()).isEqualTo(0);
	}

	/**
	 * Test negative features json with false authentication. features
	 * 
	 * @throws InterruptedException the interrupted exception
	 * @throws IOException          Signals that an I/O exception has occurred.
	 */
	@Test
	void testNegativeFeaturesJsonWithFalseAuthentication() throws InterruptedException, IOException {
		// provide global parameters
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_negative2_application.properties");

		// Get features
		FeatureDao<String, Feature> features = Features.createFeaturesRepo(globalParams);

		assertThat(features.getAll().size()).isEqualTo(0);
		assertThat(globalParams.getCompletedSprints().size()).isEqualTo(1);
	}

	/**
	 * Test negative features json with no connection.
	 *
	 * @throws InterruptedException the interrupted exception
	 * @throws IOException          Signals that an I/O exception has occurred.
	 */
	@Test
	void testNegativeFeaturesJsonWithNoConnection() throws InterruptedException, IOException {
		// provide global parameters
		GlobalParams globalParams = Utils.provideGlobalParams("src/test/resources/test_real_application.properties");
		globalParams.setUsername("");
		globalParams.setPassword("");

		assertThrows(ConnectException.class, () -> Features.createFeaturesRepo(globalParams));
	}

	/**
	 * Test positive features json.
	 *
	 * @throws InterruptedException the interrupted exception
	 * @throws IOException          Signals that an I/O exception has occurred.
	 */
	@Test
	void testPositiveFeaturesJson() throws InterruptedException, IOException {
		// provide global parameters
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_positive_application.properties");

		// Get features
		FeatureDao<String, Feature> features = Features.createFeaturesRepo(globalParams);

		assertThat(features.getAll().size()).isEqualTo(0);
		assertThat(globalParams.getCompletedSprints().size()).isEqualTo(2);
	}
}