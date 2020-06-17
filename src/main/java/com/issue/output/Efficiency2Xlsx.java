package com.issue.output;

import java.util.List;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.issue.entity.Engineer;
import com.issue.iface.EngineerDao;
import com.issue.utils.OutputCreators;

/**
 * The Class Efficiency2Xlsx.
 */
public class Efficiency2Xlsx {

	/** The logger. */
	private static Logger logger = LogManager.getLogger(Efficiency2Xlsx.class);

	/**
	 * Utility classes should not have public constructors.
	 */
	private Efficiency2Xlsx() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Creates the worksheet.
	 *
	 * @param workbook the workbook
	 * @param dao      the dao
	 * @param sheetIdx the sheet idx
	 */
	public static void createWorksheet(Workbook workbook, final EngineerDao<String, Engineer> dao, final int sheetIdx) {
		// Worksheet name
		String sheetName = "Efficiency";

		// Create sheet
		Sheet sheet = workbook.createSheet(sheetName);

		// Create column with captions
		OutputCreators.createCaptionColumn(workbook, sheetIdx, List.of("", "Sprint", "Finished SP", "Not finished SP"));

		// Initialize column
		int colIdx = 1;
		for (Engineer engineer : dao.getAll().values()) {
			// Initialize row
			int rowIdx = 0;

			// Row 0 - Engineer name
			OutputCreators.writeHeaderCell(workbook, sheetIdx, colIdx, rowIdx++, engineer.getName());

			// Row 1 - Sprint label
			OutputCreators.writeHeaderCell(workbook, sheetIdx, colIdx, rowIdx++, engineer.getSprintLabel());

			// Row 2 - Finished SP
			OutputCreators.writeCell(workbook, sheetIdx, colIdx, rowIdx++, engineer.getFinishedStoryPoints().orElse(0));

			// Row 3 - Not finished SP
			OutputCreators.writeCell(workbook, sheetIdx, colIdx, rowIdx,
					engineer.getNotFinishedStoryPoints().orElse(0));

			colIdx++;
		}

		// Autosize columns
		IntStream.range(0, dao.getAll().size() + 1).forEach(sheet::autoSizeColumn);

		logger.info("Worksheet {} created successfully.", sheetName);
	}
}
