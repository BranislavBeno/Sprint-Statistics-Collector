/**
 * 
 */
package com.issue.utils;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.issue.configuration.GlobalParams;
import com.issue.entity.Feature;
import com.issue.enums.FeatureScope;
import com.issue.iface.FeatureDao;
import com.issue.repository.FeatureDaoImpl;

/**
 * The Class Features.
 *
 * @author branislav.beno
 */
public class Features {

	/** The feature name field id. */
	public static final String FEATURE_NAME_FIELD_ID = "customfield_12641";

	/**
	 * Utility classes should not have public constructors.
	 */
	private Features() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Creates the request uri.
	 *
	 * @param globalParams the global params
	 * @param fields       the fields
	 * @return the string
	 */
	public static String createRequestUri(GlobalParams globalParams, String fields) {
		StringBuilder sb = new StringBuilder();
		sb.append(globalParams.getIssueTrackerUri()).append("?").append("jql=")
				.append(Utils.prepareUrl(globalParams.getFeaturesJql())).append("&maxResults=1000").append("&fields=")
				.append(fields);
		return sb.toString();
	}

	/**
	 * Parses the feature scope.
	 *
	 * @param summary the summary
	 * @return the feature scope
	 */
	private static FeatureScope parseFeatureScope(String summary) {
		FeatureScope scope = FeatureScope.BASIC;
		Pattern pattern = Pattern.compile("\\[(.+?)\\]");
		Matcher matcher = pattern.matcher(summary);
		if (matcher.find()) {
			switch (matcher.group()) {
			case "[Basic]":
				scope = FeatureScope.BASIC;
				break;
			case "[Advanced]":
				scope = FeatureScope.ADVANCED;
				break;
			case "[Commercial]":
				scope = FeatureScope.COMMERCIAL;
				break;
			case "[Future]":
				scope = FeatureScope.FUTURE;
				break;
			default:
				scope = FeatureScope.BASIC;
			}
		}
		return scope;
	}

	/**
	 * Ask issue tracker.
	 *
	 * @param globalParams the global params
	 * @return the string
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	private static String askIssueTracker(GlobalParams globalParams) throws IOException, InterruptedException {
		String request = createRequestUri(globalParams, "key," + FEATURE_NAME_FIELD_ID);

		return Utils.gatherJsonString(globalParams.getUsername(), globalParams.getPassword(), request);
	}

	/**
	 * Parses the key.
	 *
	 * @param issue the issue
	 * @return the string
	 */
	private static String parseKey(JsonNode issue) {
		// Get json node "summary"
		JsonNode keyField = Optional.ofNullable(issue.get("key")).orElseThrow();

		return keyField.asText();
	}

	/**
	 * Parses the summary.
	 *
	 * @param issueFields the issue fields
	 * @return the string
	 */
	private static String parseSummary(JsonNode issueFields) {
		// Get json node "summary"
		JsonNode summaryField = Optional.ofNullable(issueFields.get(FEATURE_NAME_FIELD_ID))
				.orElse(new ObjectNode(null));

		return summaryField.asText();
	}

	/**
	 * Extract features.
	 *
	 * @param jsonString the json string
	 * @return the i feature dao
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static FeatureDao<String, Feature> extractFeatures(final String jsonString) throws IOException {
		// Create empty features map
		FeatureDao<String, Feature> features = new FeatureDaoImpl();

		// Get json node "issues"
		if (jsonString != null) {
			JsonNode issuesNode = null;
			issuesNode = new ObjectMapper().readTree(jsonString).get("issues");
			// run through issues
			Optional.ofNullable(issuesNode).ifPresent(i -> i.forEach(issue -> {

				// Get json node "fields"
				JsonNode issueFields = issue.get("fields");

				if (issueFields != null) {
					// Get key
					String key = parseKey(issue);

					// Get issue summary
					String summary = parseSummary(issueFields);

					// Get feature scope
					FeatureScope scope = parseFeatureScope(summary);

					// Add new feature into map
					features.save(new Feature(key, scope));
				}
			}));
		}

		return features;
	}

	/**
	 * Creates the features repo.
	 *
	 * @param globalParams the global params
	 * @return the i feature dao
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	public static FeatureDao<String, Feature> createFeaturesRepo(GlobalParams globalParams)
			throws IOException, InterruptedException {

		// Create empty features list
		FeatureDao<String, Feature> features = new FeatureDaoImpl();

		// Get json response
		String jsonFeatures = askIssueTracker(globalParams);

		// Extract features from json
		features.saveAll(Features.extractFeatures(jsonFeatures).getAll());

		return features;
	}
}
