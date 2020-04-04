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
 * The Class SprintProgress2Xlsx.
 */
public class SprintProgress2Xlsx {

	/** The logger. */
	private static Logger logger = LogManager.getLogger(SprintProgress2Xlsx.class);

	/**
	 * Utility classes should not have public constructors.
	 */
	private SprintProgress2Xlsx() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Creates worksheet with sprint progress.
	 *
	 * @param workbook the workbook
	 * @param dao      the dao
	 * @param sheetIdx the sheet idx
	 */
	public static void createWorksheet(Workbook workbook, final TeamDao<String, Team> dao, final int sheetIdx) {
		// Worksheet name
		String sheetName = "Sprint progress";

		// Create sheet
		Sheet sheet = workbook.createSheet(sheetName);

		// Create column with captions
		OutputCreators.createCaptionColumn(workbook, sheetIdx,
				List.of("", "Sprint", "ToDo SP", "In progress SP", "Done SP"));

		// Initialize column
		int colIdx = 1;
		for (Team team : dao.getAll().values()) {
			// Row 0 - Team name
			OutputCreators.writeHeaderCell(workbook, sheetIdx, colIdx, 0, team.getTeamName());

			// Row 1 - Team name
			OutputCreators.writeHeaderCell(workbook, sheetIdx, colIdx, 1, team.getSprintLabel());

			// Row 2 - Delta number SP
			OutputCreators.writeCell(workbook, sheetIdx, colIdx, 2, team.getToDoStoryPointsSum());

			// Row 3 - Finished SP percentage
			OutputCreators.writeCell(workbook, sheetIdx, colIdx, 3, team.getInProgressStoryPointsSum());

			// Row 4 - Finished story points
			OutputCreators.writeCell(workbook, sheetIdx, colIdx, 4, team.getFinishedStoryPointsSum());

			colIdx++;
		}

		// Autosize columns
		IntStream.range(0, dao.getAll().size() + 1).forEach(sheet::autoSizeColumn);

		logger.info("Worksheet {} created successfully.", sheetName);
	}
}
