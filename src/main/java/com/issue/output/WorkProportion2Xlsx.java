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
 * The Class WorkProportion2Xlsx.
 */
public class WorkProportion2Xlsx {

	/** The logger. */
	private static Logger logger = LogManager.getLogger(WorkProportion2Xlsx.class);

	/**
	 * Utility classes should not have public constructors.
	 */
	private WorkProportion2Xlsx() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Creates the worksheet.
	 *
	 * @param workbook the workbook
	 * @param dao the dao
	 * @param sheetIdx the sheet idx
	 */
	public static void createWorksheet(Workbook workbook, final TeamDao<String, Team> dao, final int sheetIdx) {
		// Worksheet name
		String sheetName = "Work Proportion";

		// Create sheet
		Sheet sheet = workbook.createSheet(sheetName);

		// Create column with captions
		OutputCreators.createCaptionColumn(workbook, sheetIdx, List.of("", "Stories SP", "Bugs SP"));

		// Initialize column
		int colIdx = 1;
		for (Team team : dao.getAll().values()) {
			// Row 0 - Team name
			OutputCreators.writeHeaderCell(workbook, sheetIdx, colIdx, 0, team.getTeamName().orElse(""));

			// Row 1 - Stories SP
			OutputCreators.writeCell(workbook, sheetIdx, colIdx, 1, team.getFinishedStoriesSPSum());

			// Row 2 - Bugs SP
			OutputCreators.writeCell(workbook, sheetIdx, colIdx, 2, team.getFinishedBugsSPSum());

			colIdx++;
		}

		// Autosize columns
		IntStream.range(0, dao.getAll().size() + 1).forEach(sheet::autoSizeColumn);

		logger.info("Worksheet {} created successfully.", sheetName);
	}
}
