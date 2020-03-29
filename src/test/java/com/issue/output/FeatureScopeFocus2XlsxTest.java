/**
 * 
 */
package com.issue.output;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Test;

/**
 * The Class FeatureScopeFocus2XlsxTest.
 *
 * @author branislav.beno
 */
class FeatureScopeFocus2XlsxTest {

	/**
	 * Test.
	 *
	 * @throws NoSuchMethodException the no such method exception
	 */
	@Test
	void testPrivateConstructorsForCodeCoverage() throws NoSuchMethodException {
		Class<FeatureScopeFocus2Xlsx> clazz = FeatureScopeFocus2Xlsx.class;
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		for (Constructor<?> constructor : constructors) {
			constructor.setAccessible(true);
			assertThrows(InvocationTargetException.class, constructor::newInstance);
		}
	}
}
