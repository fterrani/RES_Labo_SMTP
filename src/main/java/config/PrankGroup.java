package config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PrankGroup {
    private String sender;
    private final List<String> victims;
    private final int size;

    private PrankGroup(int groupSize) {
        victims = new ArrayList<>(groupSize - 1);
        size = groupSize;
    }

    public String[] getVictims() {
        return victims.toArray(new String[0]);
    }

    public String getSender() {
        return sender;
    }

    /**
     * Creates nbGroups containing 1 sender and at least 2 receivers randomly chosen in the list of victims.
     * @param victims  List of victims
     * @param nbGroups Number of groups to create
     * @return An array of PrankGroup
     */
    public static PrankGroup[] createPrankGroups(String[] victims, int nbGroups) {
        int groupsSize = victims.length / nbGroups;
        int remainder = victims.length % nbGroups;

        PrankGroup[] groups = new PrankGroup[nbGroups];
        for (int i = 0; i < nbGroups; ++i) {
            if (i < remainder)
                groups[i] = new PrankGroup(groupsSize + 1);
            else
                groups[i] = new PrankGroup(groupsSize);
        }

        Random rand = new Random();
        int remainingMails = victims.length;
        String selectedMail;
        for (PrankGroup group : groups) {
            selectedMail = selectRandomMail(victims, remainingMails, rand);
            group.setSender(selectedMail);
            --remainingMails;

            while (!group.isFull()) {
                selectedMail = selectRandomMail(victims, remainingMails, rand);
                group.addVictim(selectedMail);
                --remainingMails;
            }
        }
        return groups;
    }

    private static String selectRandomMail(String[] list, int length, Random rand) {
        int i  = rand.nextInt(length);

        String tmp = list[i];
        list[i] = list[length - 1];
        list[length - 1] = tmp;

        return tmp;
    }

    private void addVictim(String victim) {
        victims.add(victim);
    }

    private void setSender(String sender) {
        this.sender = sender;
    }

    private boolean isFull() {
        return size - 1 == victims.size();
    }
}
