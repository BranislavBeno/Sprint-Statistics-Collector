/**
 * 
 */
package com.issue.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;

import org.junit.jupiter.api.Test;

/**
 * The Class UtilsTest.
 *
 * @author branislav.beno
 */
class UtilsTest {

	/**
	 * Test private constructors for code coverage.
	 *
	 * @throws NoSuchMethodException the no such method exception
	 */
	@Test
	void testPrivateConstructorsForCodeCoverage() throws NoSuchMethodException {
		Class<Utils> clazz = Utils.class;
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		for (Constructor<?> constructor : constructors) {
			constructor.setAccessible(true);
			assertThrows(InvocationTargetException.class, constructor::newInstance);
		}
	}

	/**
	 * Test positive gather json string.
	 *
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testPositiveGatherJsonString() throws IOException, InterruptedException {
		// Get json string
		String jsonString = Utils.gatherJsonString("postman", "password", "https://postman-echo.com/basic-auth");

		assertThat(jsonString).isEqualTo("{\"authenticated\":true}");
	}

	/**
	 * Test negative gather json string.
	 *
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testNegativeGatherJsonString() throws IOException, InterruptedException {
		// Get json string
		String jsonString = Utils.gatherJsonString("postman", "passwd", "https://postman-echo.com/basic-auth");

		assertThat(jsonString).isNull();
	}

	/**
	 * Test full stats run with no connection exception.
	 */
	@Test
	void testFullStatsRunWithNoConnectionException() {
		// Start main routine
		assertThrows(ConnectException.class, () -> Utils.runStats("usr", "passwd", false));
	}
}
