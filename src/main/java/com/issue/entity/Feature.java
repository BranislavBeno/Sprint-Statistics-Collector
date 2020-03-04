package com.issue.entity;

import java.util.Optional;

import com.issue.enums.FeatureScope;

/**
 * The Class Feature.
 *
 * @author branislav.beno
 */
public class Feature {

	/** The key. */
	private String key;

	/** The scope. */
	private FeatureScope scope;

	/**
	 * Instantiates a new feature.
	 *
	 * @param key   the key
	 * @param scope the scope
	 */
	public Feature(String key, FeatureScope scope) {
		this.key = Optional.ofNullable(key).orElseThrow();
		this.scope = Optional.ofNullable(scope).orElse(FeatureScope.BASIC);
	}

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Gets the feature scope.
	 *
	 * @return the feature scope
	 */
	public FeatureScope getFeatureScope() {
		return scope;
	}
}