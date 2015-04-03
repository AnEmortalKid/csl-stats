package com.anemortalkid.csl.stats.ui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;

import com.anemortalkid.csl.data.util.CSLHtmlExportUtils;
import com.anemortalkid.csl.datastore.CSLDataStore;
import com.anemortalkid.csl.stats.CSLTeamEnum;
import com.anemortalkid.csl.stats.CSLTeamStatsGather;

public class CSLStatsGatheringView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1702050302374364733L;
	private JPanel mainPanel;
	private JComboBox<CSLTeamEnum> teamURLS = new JComboBox<CSLTeamEnum>();
	private JTextArea rankArea;

	public CSLStatsGatheringView() {
		super();
		this.setLayout(new BorderLayout());
		mainPanel = new JPanel(new MigLayout("", "[grow][][grow]", "[]"));
		mainPanel.add(new JLabel("Select team:"));
		populateURLS();
		mainPanel.add(teamURLS);
		mainPanel.add(new JButton(new GatherStatsAction()), "wrap");
		rankArea = new JTextArea();
		JScrollPane pane = new JScrollPane(rankArea);
		rankArea.setRows(15);
		mainPanel.add(pane, "span, grow");
		this.add(mainPanel, BorderLayout.CENTER);
		this.pack();
	}

	private void populateURLS() {
		for (CSLTeamEnum team : CSLTeamEnum.values())
			teamURLS.addItem(team);
	}

	private class GatherStatsAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = -7483896865177785016L;

		private GatherStatsAction() {
			super("Gather Stats");
		}

		public void actionPerformed(ActionEvent arg0) {
			CSLTeamEnum team = (CSLTeamEnum) teamURLS.getSelectedItem();

			CSLTeamStatsGather gatherer = null;
			try {
				setEnabled(false);
				CSLStatsGatheringView.this.setCursor(Cursor
						.getPredefinedCursor(Cursor.WAIT_CURSOR));

				gatherer = new CSLTeamStatsGather(team.getSchoolName(),
						team.getCSLTeamID(), System.out);
				gatherer.gatherStats();
				rankArea.setText(gatherer.getTeamRecord());
			} finally {
				CSLStatsGatheringView.this.setCursor(Cursor
						.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				setEnabled(true);
			}
			// on the background
			// JSONUtils.writeToFile(gatherer.getTeamData());
			CSLDataStore.saveToDatastore(gatherer.getTeamData());
			CSLHtmlExportUtils.generateHTMLPage(gatherer.getTeamData());
		}

	}

	public static void main(String[] args) {
		CSLStatsGatheringView gsv = new CSLStatsGatheringView();
		gsv.setTitle("UI 2.0");
		gsv.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gsv.setVisible(true);
	}
}
