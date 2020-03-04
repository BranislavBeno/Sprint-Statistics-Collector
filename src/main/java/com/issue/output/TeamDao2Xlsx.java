/**
 * 
 */
package com.issue.output;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.issue.configuration.GlobalParams;
import com.issue.entity.Engineer;
import com.issue.entity.Sprint;
import com.issue.entity.Team;
import com.issue.iface.Dao2Output;
import com.issue.iface.EngineerDao;
import com.issue.iface.SprintDao;
import com.issue.iface.TeamDao;

/**
 * The Class TeamDao2Xlsx.
 */
public class TeamDao2Xlsx implements Dao2Output {

	/** The logger. */
	private static Logger logger = LogManager.getLogger(TeamDao2Xlsx.class);

	/** The dao. */
	private TeamDao<String, Team> teamDao;

	/** The sprint dao. */
	private SprintDao<String, Sprint> sprintDao;

	/** The engineer dao. */
	private EngineerDao<String, Engineer> engineerDao;

	/** The global params. */
	private GlobalParams globalParams;

	/**
	 * Instantiates a new feature dao 2 csv.
	 *
	 * @param globalParams the global params
	 * @param teamRepo     the dao
	 * @param sprintRepo   the sprints
	 * @param engineerRepo the engineer repo
	 */
	public TeamDao2Xlsx(final GlobalParams globalParams, final TeamDao<String, Team> teamRepo,
			final SprintDao<String, Sprint> sprintRepo, final EngineerDao<String, Engineer> engineerRepo) {
		this.globalParams = globalParams;
		this.teamDao = teamRepo;
		this.sprintDao = sprintRepo;
		this.engineerDao = engineerRepo;
	}

	/**
	 * Provide workbook content to output file.
	 *
	 * @param workbook the workbook
	 */
	private void workbook2File(Workbook workbook) {
		// Throw exception when path to file is null
		String fileLocation = Optional.ofNullable(globalParams.getOutputFileName4Xlsx())
				.orElseThrow(IllegalArgumentException::new);

		// Write the content to output xlsx file
		try (FileOutputStream outputStream = new FileOutputStream(fileLocation);) {
			workbook.write(outputStream);
		} catch (IOException e) {
			logger.error("Content for XLSX file wasn't provided!");
		}
	}

	/**
	 * Provide content.
	 *
	 * @return the string
	 */
	@Override
	public String provideContent() {
		// add page header
		try (Workbook workbook = new XSSFWorkbook();) {
			// Create feature scope focus worksheet for current sprint
			FeatureScopeFocus2Xlsx.createWorksheet(workbook, teamDao, 0);

			// Create velocity worksheet
			Velocity2Xlsx.createWorksheet(workbook, teamDao, 1);

			// Create Kpi worksheet
			Kpi2Xlsx.createWorksheet(workbook, teamDao, 2);

			// Create sprint progress worksheet
			SprintProgress2Xlsx.createWorksheet(workbook, teamDao, 3);

			// Create work proportion worksheet
			WorkProportion2Xlsx.createWorksheet(workbook, teamDao, 4);

			// Create capacity worksheet
			Capacity2Xlsx.createWorksheet(workbook, teamDao, 5);

			// Create feature scope focus worksheet for planned sprints
			RefinementFeatureScope2Xlsx.createWorksheet(workbook, sprintDao, 6);

			// Create engineer's efficiency
			Efficiency2Xlsx.createWorksheet(workbook, engineerDao, 7);

			// Create output file
			workbook2File(workbook);

		} catch (IOException e1) {
			logger.error("File with XLSX content wasn't created!");
		}

		return null;
	}

	/**
	 * Provide output file name.
	 *
	 * @return the string
	 */
	@Override
	public String provideOutputFileName() {
		return globalParams.getOutputFileName4Xlsx();
	}
}
