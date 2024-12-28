package uk.ac.sheffield.com1003.assignment.gui;

import uk.ac.sheffield.com1003.assignment.codeprovided.*;
import uk.ac.sheffield.com1003.assignment.codeprovided.gui.AbstractPlayerDashboardPanel;

import javax.swing.*;
import java.util.ArrayList;

/**
 * SKELETON IMPLEMENTATION
 */

public class PlayerDashboardPanel extends AbstractPlayerDashboardPanel
{

    // Constructor
    public PlayerDashboardPanel(AbstractPlayerCatalog playerCatalog) {

        super(playerCatalog);

        populatePlayerDetailsComboBoxes();

        updateStatistics();

        updatePlayerCatalogDetailsBox();

    }

    @Override
    public void populatePlayerDetailsComboBoxes() {
        // TODO remove code below and implement, the comboboxes should be dynamically updated based on user actions

        //Adding values to the specified lists
        for (PlayerEntry playerEntry : filteredPlayerEntriesList) {
            playerNamesList.add(playerEntry.getPlayerName());
            nationList.add(playerEntry.getNation());
            teamList.add(playerEntry.getTeam());
            positionList.add(playerEntry.getPosition());
        }

        // Populating the combo box for player names
        comboPlayerNames.removeAllItems();

        //Setting the first element in the drop-down menu to a blank box
        playerNamesList.add(0,"");

        for (String name : playerNamesList) {
            comboPlayerNames.addItem(name);
        }


        // Populating the combo box for nations
        comboNations.removeAllItems();

        //Setting the first element in the drop-down menu to a blank box
        nationList.add(0,"");

        for (String nation : nationList) {
            comboNations.addItem(nation);
        }


        // Populating the combo box for position
        comboPositions.removeAllItems();

        //Setting the first element in the drop-down menu to a blank box
        positionList.add(0,"");

        for (String position : positionList) {
            comboPositions.addItem(position);
        }


        // Populating the combo box for teams
        comboTeams.removeAllItems();

        //Setting the first element in the drop-down menu to a blank box
        teamList.add(0,"");

        for (String team : teamList) {
            comboTeams.addItem(team);
        }
}

    /**
     * addListeners method - adds relevant actionListeners to the GUI components
     */
    
    @SuppressWarnings("unused")
    @Override
    public void addListeners() {
        // TODO implement

        //Adding relevant actionListeners to the GUI components
        buttonAddFilter.addActionListener(e -> addFilter());

        buttonClearFilters.addActionListener(e -> clearFilters());

        comboRadarChartCategories.addActionListener(e -> updateRadarChart());

        comboLeagueTypes.addActionListener(e -> {
            selectedLeagueType = (League.fromName(String.valueOf(comboLeagueTypes.getSelectedItem()).toUpperCase())) ;
        });

        comboPlayerNames.addActionListener(e -> {
            selectedPlayerName = String.valueOf(comboPlayerNames.getSelectedItem());
        });

        comboNations.addActionListener(e -> {
            selectedPlayerNation = String.valueOf(comboNations.getSelectedItem());
        });

        comboPositions.addActionListener(e -> {
            selectedPlayerPosition = String.valueOf(comboPositions.getSelectedItem());
        });

        comboTeams.addActionListener(e -> {
            selectedTeam = String.valueOf(comboTeams.getSelectedItem());
        });
    }

    /**
     * clearFilters method - clears all filters from the subQueryList ArrayList and updates
     * the relevant GUI components
     */
    @Override
    public void clearFilters() {
        // TODO implement

        // clear all filters from subQueryList
        subQueryList.clear();

        // updating the GUI components
        executeQuery();
        updateStatistics();
        updatePlayerCatalogDetailsBox();
        updateRadarChart();
    }

    @Override
    public void updateRadarChart() {
        // TODO implement

    }

    /**
     * updateStats method - updates the table with statistics after any changes which may
     * affect the JTable which holds the statistics
     */
    @Override
    public void updateStatistics() {
        // TODO implement
        statisticsTextArea.setText("");

        //When there are no players to calculate statistics for
        if (filteredPlayerEntriesList.isEmpty()) {
            return;
        }

        //Defining String builder objects for header, min, max, avg
        StringBuilder header = new StringBuilder("\t");
        StringBuilder min = new StringBuilder("Minimum:\t");
        StringBuilder max = new StringBuilder("Maximum:\t");
        StringBuilder avg = new StringBuilder("Average:\t");

        //adding values in the Player Catalog Statistics box
        for (PlayerProperty playerProperty : PlayerProperty.values()) {
            header.append(playerProperty.getName()).append("\t");

            double minValue = playerCatalog.getMinimumValue(playerProperty, filteredPlayerEntriesList);
            double maxValue = playerCatalog.getMaximumValue(playerProperty, filteredPlayerEntriesList);
            double avgValue = playerCatalog.getMeanAverageValue(playerProperty, filteredPlayerEntriesList);

            min.append(String.format("%.2f\t", minValue));
            max.append(String.format("%.2f\t", maxValue));
            avg.append(String.format("%.2f\t", avgValue));
        }

        statisticsTextArea.append(header + "\n");
        statisticsTextArea.append(min + "\n");
        statisticsTextArea.append(max + "\n");
        statisticsTextArea.append(avg + "\n");

}

    /**
     * updatePlayerCatalogDetailsBox method - updates the list of players when changes are made
     */
    @Override
    public void updatePlayerCatalogDetailsBox() {
        // TODO implement

        filteredPlayerEntriesTextArea.setText("");

        // Adding values for the header
        StringBuilder header = new StringBuilder();
        header.append("League Type\tID\tPlayer\t\tNation\tPosition\tTeam");
        for (PlayerProperty playerProperty : PlayerProperty.values()) {
            header.append("\t" + playerProperty.getName());
        }

        // Adding values into the Player Entries box
        StringBuilder data = new StringBuilder();
        for (PlayerEntry playerEntry : filteredPlayerEntriesList) {
            StringBuilder row = new StringBuilder();
            row.append(playerEntry.getLeagueType() + "\t" +
                    playerEntry.getId() + "\t" +
                    playerEntry.getPlayerName() + "\t" + "\t" +
                    playerEntry.getNation() + "\t" +
                    playerEntry.getPosition() + "\t" +
                    playerEntry.getTeam() + "\t");
            for (PlayerProperty playerProperty : PlayerProperty.values()) {
                row.append("\t" + playerEntry.getProperty(playerProperty));
            }
            data.append(row + "\n");
        }

        // Display the header and data in the text area
        filteredPlayerEntriesTextArea.append(header + "\n");
        filteredPlayerEntriesTextArea.append(data.toString());
    }

    /**
     * executeQuery method - applies chosen query to the relevant list
     */
    @Override
    public void executeQuery() {
        // TODO implement
        Query selectedQuery = new Query(subQueryList, selectedLeagueType);

        filteredPlayerEntriesList = (ArrayList<PlayerEntry>) selectedQuery.executeQuery(playerCatalog);

        updateStatistics();
    }



    /**
     * addFilters method - adds filters input into GUI to subQueryList ArrayList
     */
    @Override
    public void addFilter() {
        // TODO implement

        //Collecting values for property, operator and values to make subquery
        PlayerProperty property = PlayerProperty.fromPropertyName((String) comboQueryProperties.getSelectedItem());
        String operator = (String) comboOperators.getSelectedItem();
        double queryValue = Double.parseDouble(value.getText());

        //Creating a new subQuery with the above values
        SubQuery subQuery = new SubQuery(property,operator, queryValue);

        //Adding subQuery to the list
        subQueryList.add(subQuery);
        subQueriesTextArea.append(subQuery.toString());

        //Updating the GUI
        executeQuery();
        updateStatistics();
        updatePlayerCatalogDetailsBox();
    }

    @Override
    public boolean isMinCheckBoxSelected() {
        // TODO implement
        return minCheckBox.isSelected();
    }

    @Override
    public boolean isMaxCheckBoxSelected() {
        // TODO implement
        return maxCheckBox.isSelected();
    }

    @Override
    public boolean isAverageCheckBoxSelected() {
        // TODO implement
        return averageCheckBox.isSelected();
    }

}
