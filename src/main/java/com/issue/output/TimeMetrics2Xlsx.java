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
import com.issue.iface.TeamDao;
import com.issue.utils.OutputCreators;

/**
 * The Class TimeMetrics2Xlsx.
 */
public class TimeMetrics2Xlsx {

	/** The logger. */
	private static Logger logger = LogManager.getLogger(TimeMetrics2Xlsx.class);

	/**
	 * Utility classes should not have public constructors.
	 */
	private TimeMetrics2Xlsx() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Creates the worksheet.
	 *
	 * @param workbook the workbook
	 * @param dao      the dao
	 * @param sheetIdx the sheet idx
	 */
	public static void createWorksheet(Workbook workbook, final TeamDao<String, Team> dao, final int sheetIdx) {
		// Worksheet name
		String sheetName = "Time metrics";

		// Create sheet
		Sheet sheet = workbook.createSheet(sheetName);

		// Create column with captions
		OutputCreators.createCaptionColumn(workbook, sheetIdx,
				List.of("", "Sprint", "Capacity", "Estimation", "Planned", "Spent"));

		// Initialize column
		int colIdx = 1;
		for (Team team : dao.getAll().values()) {
			// Row 0 - Team name
			OutputCreators.writeHeaderCell(workbook, sheetIdx, colIdx, 0, team.getTeamName());

			// Row 1 - Sprint label
			OutputCreators.writeHeaderCell(workbook, sheetIdx, colIdx, 1, team.getSprintLabel());

			// Row 2 - Capacity (always zero)
			OutputCreators.writeCell(workbook, sheetIdx, colIdx, 2, 0);

			// Row 3 - Time estimation
			OutputCreators.writeCell(workbook, sheetIdx, colIdx, 3, team.getTimeEstimation());

			// Row 4 - Time plan
			OutputCreators.writeCell(workbook, sheetIdx, colIdx, 4, team.getTimePlanned());

			// Row 5 - Time spent
			OutputCreators.writeCell(workbook, sheetIdx, colIdx, 5, team.getTimeSpent());

			colIdx++;
		}

		// Autosize columns
		IntStream.range(0, dao.getAll().size() + 1).forEach(sheet::autoSizeColumn);

		logger.info("Worksheet {} created successfully.", sheetName);
	}
}
