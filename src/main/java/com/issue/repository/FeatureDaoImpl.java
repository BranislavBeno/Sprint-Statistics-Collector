package com.issue.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.issue.entity.Feature;
import com.issue.iface.FeatureDao;

/**
 * The Class FeatureDao.
 *
 * @author branislav.beno
 */
public class FeatureDaoImpl implements FeatureDao<String, Feature> {

	/** The features. */
	private Map<String, Feature> features = new HashMap<>();

	/**
	 * Gets the feature.
	 *
	 * @param key the key
	 * @return the optional
	 */
	@Override
	public Optional<Feature> get(String key) {
		return Optional.ofNullable(features.get(key));
	}

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Override
	public Map<String, Feature> getAll() {
		return features;
	}

	/**
	 * Save.
	 *
	 * @param theFeature the the feature
	 */
	@Override
	public void save(Feature theFeature) {
		features.put(theFeature.getKey(), theFeature);
	}

	/**
	 * Save all.
	 *
	 * @param map the map
	 */
	@Override
	public void saveAll(Map<String, Feature> map) {
		features.putAll(map);
	}
}
