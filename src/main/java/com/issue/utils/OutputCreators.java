/**
 * 
 */
package com.issue.utils;

import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.issue.configuration.GlobalParams;
import com.issue.entity.Engineer;
import com.issue.entity.Sprint;
import com.issue.entity.Team;
import com.issue.iface.Dao2Output;
import com.issue.iface.EngineerDao;
import com.issue.iface.SprintDao;
import com.issue.iface.TeamDao;
import com.issue.output.TeamDao2Xlsx;

/**
 * The Class Utils.
 *
 * @author branislav.beno
 */
public class OutputCreators {
	/**
	 * Utility classes should not have public constructors.
	 */
	private OutputCreators() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Default font.
	 *
	 * @param workbook the workbook
	 * @return the XSSF font
	 */
	public static XSSFFont defaultFont(Workbook workbook) {
		XSSFFont contentFont = ((XSSFWorkbook) workbook).createFont();
		contentFont.setFontName(XSSFFont.DEFAULT_FONT_NAME);
		contentFont.setFontHeightInPoints((short) 11);

		return contentFont;
	}

	/**
	 * Apply border style.
	 *
	 * @param contentStyle the content style
	 */
	private static void applyBorderStyle(CellStyle contentStyle) {
		contentStyle.setBorderRight(BorderStyle.THIN);
		contentStyle.setBorderLeft(BorderStyle.THIN);
		contentStyle.setBorderTop(BorderStyle.THIN);
		contentStyle.setBorderBottom(BorderStyle.THIN);
	}

	/**
	 * Caption style.
	 *
	 * @param workbook  the workbook
	 * @param alignment the alignment
	 * @return the cell style
	 */
	public static CellStyle captionStyle(Workbook workbook, final HorizontalAlignment alignment) {
		// Define font
		XSSFFont font = ((XSSFWorkbook) workbook).createFont();
		font.setFontName(XSSFFont.DEFAULT_FONT_NAME);
		font.setFontHeightInPoints((short) 8);
		font.setBold(true);

		// Create style
		CellStyle headerStyle = workbook.createCellStyle();

		// Style settings
		headerStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setFont(font);
		headerStyle.setAlignment(alignment);
		applyBorderStyle(headerStyle);

		return headerStyle;
	}

	/**
	 * Column style.
	 *
	 * @param workbook  the workbook
	 * @param alignment the alignment
	 * @return the cell style
	 */
	public static CellStyle columnStyle(Workbook workbook, final HorizontalAlignment alignment) {
		// Set style
		CellStyle contentStyle = workbook.createCellStyle();
		contentStyle.setFont(defaultFont(workbook));
		contentStyle.setAlignment(alignment);
		applyBorderStyle(contentStyle);
		return contentStyle;
	}

	/**
	 * Column percentage style.
	 *
	 * @param workbook  the workbook
	 * @param alignment the alignment
	 * @return the cell style
	 */
	private static CellStyle columnPercentageStyle(Workbook workbook, final HorizontalAlignment alignment) {
		// Set style
		CellStyle contentStyle = workbook.createCellStyle();
		contentStyle.setFont(defaultFont(workbook));
		contentStyle.setDataFormat(workbook.createDataFormat().getFormat("0.00%"));
		contentStyle.setAlignment(alignment);
		applyBorderStyle(contentStyle);
		return contentStyle;
	}

	/**
	 * Caption 2 cell.
	 *
	 * @param workbook the workbook
	 * @param cell     the cell
	 * @param value    the value
	 */
	public static void caption2Cell(Workbook workbook, Cell cell, final String value) {
		cell.setCellValue(value);
		cell.setCellStyle(captionStyle(workbook, HorizontalAlignment.LEFT));
	}

	/**
	 * Header 2 cell.
	 *
	 * @param workbook the workbook
	 * @param cell     the cell
	 * @param value    the value
	 */
	public static void header2Cell(Workbook workbook, Cell cell, final String value) {
		cell.setCellValue(value);
		cell.setCellStyle(captionStyle(workbook, HorizontalAlignment.CENTER));
	}

	/**
	 * Content 2 cell.
	 *
	 * @param workbook the workbook
	 * @param cell     the cell
	 * @param value    the value
	 */
	public static void content2Cell(Workbook workbook, Cell cell, final int value) {
		cell.setCellValue(value);
		cell.setCellStyle(columnStyle(workbook, HorizontalAlignment.CENTER));
	}

	/**
	 * Content 2 cell.
	 *
	 * @param workbook the workbook
	 * @param cell     the cell
	 * @param value    the value
	 */
	public static void content2Cell(Workbook workbook, Cell cell, final double value) {
		if (value >= 0) {
			cell.setCellValue(value);
			cell.setCellStyle(columnPercentageStyle(workbook, HorizontalAlignment.CENTER));
		} else {
			cell.setCellValue("N/A");
			cell.setCellStyle(columnStyle(workbook, HorizontalAlignment.CENTER));
		}
	}

	/**
	 * Content 2 cell.
	 *
	 * @param workbook the workbook
	 * @param cell     the cell
	 * @param value    the value
	 */
	public static void content2Cell(Workbook workbook, Cell cell, final long value) {
		cell.setCellValue(value);
		cell.setCellStyle(columnStyle(workbook, HorizontalAlignment.CENTER));
	}

	/**
	 * Write header cell.
	 *
	 * @param workbook the workbook
	 * @param sheet    the sheet
	 * @param col      the col
	 * @param row      the row
	 * @param value    the value
	 */
	public static void writeHeaderCell(Workbook workbook, final int sheet, final int col, final int row,
			final String value) {
		Row content = workbook.getSheetAt(sheet).getRow(row);
		header2Cell(workbook, content.createCell(col), value);
	}

	/**
	 * Write cell.
	 *
	 * @param workbook the workbook
	 * @param sheet    the sheet
	 * @param col      the col
	 * @param row      the row
	 * @param value    the value
	 */
	public static void writeCell(Workbook workbook, final int sheet, final int col, final int row, final int value) {
		Row content = workbook.getSheetAt(sheet).getRow(row);
		content2Cell(workbook, content.createCell(col), value);
	}

	/**
	 * Write cell.
	 *
	 * @param workbook the workbook
	 * @param sheet    the sheet
	 * @param col      the col
	 * @param row      the row
	 * @param value    the value
	 */
	public static void writeCell(Workbook workbook, final int sheet, final int col, final int row, final double value) {
		Row content = workbook.getSheetAt(sheet).getRow(row);
		content2Cell(workbook, content.createCell(col), value);
	}

	/**
	 * Write cell.
	 *
	 * @param workbook the workbook
	 * @param sheet    the sheet
	 * @param col      the col
	 * @param row      the row
	 * @param value    the value
	 */
	public static void writeCell(Workbook workbook, final int sheet, final int col, final int row, final long value) {
		Row content = workbook.getSheetAt(sheet).getRow(row);
		content2Cell(workbook, content.createCell(col), value);
	}

	/**
	 * Creates the xlsx output.
	 *
	 * @param globalParams the global params
	 * @param teams        the teams
	 * @param sprints      the sprints
	 * @param engineers    the engineers
	 * @return the dao 2 output
	 */
	public static Dao2Output createXlsxOutput(final GlobalParams globalParams, final TeamDao<String, Team> teams,
			final SprintDao<String, Sprint> sprints, final EngineerDao<String, Engineer> engineers) {
		// Initialize output
		Dao2Output xlsxOutput = null;

		// print features to XLSX
		if (globalParams != null && teams != null && sprints != null && engineers != null) {
			xlsxOutput = new TeamDao2Xlsx(globalParams, teams, sprints, engineers);
			xlsxOutput.provideContent();
		}

		return xlsxOutput;
	}

	/**
	 * Creates the caption column.
	 *
	 * @param workbook the workbook
	 * @param sheetIdx the sheet idx
	 * @param captions the captions
	 */
	public static void createCaptionColumn(Workbook workbook, final int sheetIdx, List<String> captions) {
		// Initialize column counter
		int colIdx = 0;

		// Initialize teams
		int rowIdx = 0;
		// Create caption column
		for (String caption : captions) {

			// Create new row
			Row content = workbook.getSheetAt(sheetIdx).createRow(rowIdx++);

			// Fill row
			OutputCreators.caption2Cell(workbook, content.createCell(colIdx), caption);
		}
	}
}
