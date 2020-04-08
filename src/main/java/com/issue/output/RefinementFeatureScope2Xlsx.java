/**
 * 
 */
package com.issue.output;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.issue.entity.Sprint;
import com.issue.enums.FeatureScope;
import com.issue.iface.SprintDao;
import com.issue.utils.OutputCreators;

/**
 * The Class RefinementFeatureScope2Xlsx.
 */
public class RefinementFeatureScope2Xlsx {

	/** The logger. */
	private static Logger logger = LogManager.getLogger(RefinementFeatureScope2Xlsx.class);

	/**
	 * Utility classes should not have public constructors.
	 */
	private RefinementFeatureScope2Xlsx() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Prepare sprints list.
	 *
	 * @param dao the dao
	 * @return the list
	 */
	private static List<Sprint> prepareSprintsList(final SprintDao<String, Sprint> dao) {
		// Prepare sprints list
		List<Sprint> values = dao.getAll().values().stream().collect(Collectors.toList());

		// Sort sprints according to sprint label
		Collections.sort(values, (a, b) -> a.getSprintLabel().compareTo(b.getSprintLabel()));

		return List.copyOf(values);
	}

	/**
	 * Generate worksheet with features.
	 *
	 * @param workbook the workbook
	 * @param dao      the dao
	 * @param sheetIdx the sheet idx
	 */
	public static void createWorksheet(Workbook workbook, final SprintDao<String, Sprint> dao, final int sheetIdx) {
		// Worksheet name
		String sheetName = "Refinement";

		// Create sheet
		Sheet sheet = workbook.createSheet(sheetName);

		// Create column with captions
		OutputCreators.createCaptionColumn(workbook, sheetIdx, List.of("", "Finished Basic SP", "Finished Advanced SP",
				"Finished Comercial SP", "Finished Future SP"));

		// Convert and sort sprints
		List<Sprint> sprints = prepareSprintsList(dao);

		// Initialize column
		int colIdx = 1;
		for (Sprint sprint : sprints) {
			// Initialize row
			int rowIdx = 0;

			// Row 0 - Sprint label
			OutputCreators.writeHeaderCell(workbook, sheetIdx, colIdx, rowIdx++, sprint.getSprintLabel());

			// Rows 1 - 4
			final int cIdx = colIdx;
			final int rIdx = rowIdx;
			sprint.getRefinedStoryPoints().ifPresent(points -> {
				int cI = cIdx;
				int rI = rIdx;
				OutputCreators.writeCell(workbook, sheetIdx, cI, rI++, points.get(FeatureScope.BASIC));
				OutputCreators.writeCell(workbook, sheetIdx, cI, rI++, points.get(FeatureScope.ADVANCED));
				OutputCreators.writeCell(workbook, sheetIdx, cI, rI++, points.get(FeatureScope.COMMERCIAL));
				OutputCreators.writeCell(workbook, sheetIdx, cI, rI++, points.get(FeatureScope.FUTURE));
			});

			colIdx++;
		}

		// Autosize columns
		IntStream.range(0, dao.getAll().size() + 1).forEach(sheet::autoSizeColumn);

		logger.info("Worksheet {} created successfully.", sheetName);
	}
}
