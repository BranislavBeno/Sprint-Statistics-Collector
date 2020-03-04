/**
 * 
 */
package com.issue.output;

import java.util.List;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.issue.entity.Team;
import com.issue.enums.FeatureScope;
import com.issue.iface.TeamDao;
import com.issue.utils.OutputCreators;

/**
 * The Class FeatureScopeFocus2Xlsx.
 */
public class FeatureScopeFocus2Xlsx {

	/** The logger. */
	private static Logger logger = LogManager.getLogger(FeatureScopeFocus2Xlsx.class);

	/**
	 * Utility classes should not have public constructors.
	 */
	private FeatureScopeFocus2Xlsx() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Generate worksheet with features.
	 *
	 * @param workbook the workbook
	 * @param dao      the dao
	 * @param sheetIdx the sheet idx
	 */
	public static void createWorksheet(Workbook workbook, final TeamDao<String, Team> dao, final int sheetIdx) {
		// Worksheet name
		String sheetName = "Feature scope focus";

		// Create sheet
		Sheet sheet = workbook.createSheet(sheetName);

		// Create column with captions
		OutputCreators.createCaptionColumn(workbook, sheetIdx, List.of("", "Finished Basic SP", "Finished Advanced SP",
				"Finished Comercial SP", "Finished Future SP"));

		// Initialize column
		int colIdx = 1;
		for (Team team : dao.getAll().values()) {
			// Initialize row
			int rowIdx = 0;

			// Row 0 - Team name
			OutputCreators.writeHeaderCell(workbook, sheetIdx, colIdx, rowIdx++, team.getTeamName().orElse(""));

			// Rows 1 - 4
			final int cIdx = colIdx;
			final int rIdx = rowIdx;
			team.getFinishedStoryPoints().ifPresent(points -> {
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
