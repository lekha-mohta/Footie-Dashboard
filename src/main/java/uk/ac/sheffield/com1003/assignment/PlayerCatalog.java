package uk.ac.sheffield.com1003.assignment;

import uk.ac.sheffield.com1003.assignment.codeprovided.*;

import java.util.*;

/**
 * SKELETON IMPLEMENTATION
 */
public class PlayerCatalog extends AbstractPlayerCatalog
{
    /**
     * Constructor
     */
    public PlayerCatalog(String eplFilename, String ligaFilename) {
        super(eplFilename, ligaFilename);
    }

    @Override
    public PlayerPropertyMap parsePlayerEntryLine(String line) throws IllegalArgumentException
    {
        // TODO implement
        String[] values = line.split(",");
        //if resulting array doesn't have same no. of properties as PlayerProperty array - exception
        if (values.length != PlayerProperty.values().length + PlayerDetail.values().length){
            throw new IllegalArgumentException("Exception : Does not include all the properties.");
        }

        PlayerPropertyMap map = new PlayerPropertyMap();

        //Checks for the first four Player Detail values
        for (int i = 0; i < 4; i++){
            String property = values[i].trim();
            if (property.isEmpty()){
                throw new IllegalArgumentException("Empty Player Detail Entry : " + line);
            }
            try{
                map.putDetail(PlayerDetail.values()[i],property);
            }catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Undefined Property");
            }
        }

        //Checks for all the remaining Player property values
        for (int i = 4; i < values.length; i++){
            double properties = Double.parseDouble(values[i].trim());
            if (values[i].isEmpty()){
                throw new IllegalArgumentException("Malformed Line : " + line);
            }
            try {
                map.put(PlayerProperty.values()[i-4],properties);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Undefined property");
            }
        }

        //Returns the map storing the values for each of its properties
        return map;
    }

    @Override
    public void updatePlayerCatalog() {
        // TODO delete next line and implement

        //Adds player entries from both the leagues (EPL and LIGA) into the playerEntriesMap
        List<PlayerEntry> allPlayerEntries = new ArrayList<>();

        allPlayerEntries.addAll(playerEntriesMap.get(League.EPL));

        allPlayerEntries.addAll(playerEntriesMap.get(League.LIGA));

        playerEntriesMap.put(League.ALL, allPlayerEntries);
    }

    @Override
    public double getMinimumValue(PlayerProperty playerProperty, List<PlayerEntry> playerEntryList)
            throws NoSuchElementException {
        // TODO implement

        //Checks whether the list is empty, throws exception
        if (playerEntryList == null || playerEntryList.isEmpty()) {
            throw new NoSuchElementException("Invalid playerEntryList");
        }

        double minPropertyValue = Double.MAX_VALUE;

        // Finds the minimum value by comparing with property values set above.
        for (PlayerEntry entry : playerEntryList) {
            double propertyValue = entry.getProperty(playerProperty);
            if (propertyValue < minPropertyValue) {
                minPropertyValue = propertyValue;
            }
        }

        //Checks if minimum value exists
        if (minPropertyValue == Double.MAX_VALUE) {
            throw new NoSuchElementException("No minimum value for " + playerProperty);
        }

        return minPropertyValue;
    }

    @Override
    public double getMaximumValue(PlayerProperty playerProperty, List<PlayerEntry> playerEntryList)
            throws NoSuchElementException {
        // TODO implement

        //Checks whether the list is empty, throws exception
        if (playerEntryList == null || playerEntryList.isEmpty()) {
            throw new NoSuchElementException("Invalid playerEntryList");
        }

        double maxPropertyValue = Double.NEGATIVE_INFINITY;

        // Finds the maximum value by comparing with property value set above.
        for (PlayerEntry entry : playerEntryList) {
            double propertyValue = entry.getProperty(playerProperty);
            if (propertyValue > maxPropertyValue) {
                maxPropertyValue = propertyValue;
            }
        }

        // Checks if maximum value exists
        if (maxPropertyValue == Double.NEGATIVE_INFINITY) {
            throw new NoSuchElementException("No maximum value for " + playerProperty);
        }

        return maxPropertyValue;
    }

    @Override
    public double getMeanAverageValue(PlayerProperty playerProperty, List<PlayerEntry> playerEntryList)
            throws NoSuchElementException {
        // TODO implement
        //Checks whether the list is empty, throws exception
        if (playerEntryList == null || playerEntryList.isEmpty()) {
            throw new NoSuchElementException("Invalid playerEntryList");
        }

        double sumPropertyValue = 0.0;

        //gets the sum of all the player entry values
        for (PlayerEntry entry : playerEntryList) {
            sumPropertyValue += entry.getProperty(playerProperty);
        }

        //Calculates average
        double meanPropertyValue = sumPropertyValue / playerEntryList.size();

        return meanPropertyValue;
    }

    @Override
    public List<PlayerEntry> getFirstFivePlayerEntries(League type) {
        // TODO implement

        //Returns the first five players from the leagues
        List<PlayerEntry> playerEntries = playerEntriesMap.get(type);
        if (playerEntries == null) {
            throw new IllegalArgumentException("Invalid league type: " + type);
        }

        return playerEntries.subList(0, Math.min(playerEntries.size(), 5));
    }

}
