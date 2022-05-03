package config;

import java.util.ArrayList;
import java.util.List;

public class PrankGroup {
    private String sender;
    private final List<String> victims;
    private final int size;

    public PrankGroup(int groupSize) {
        victims = new ArrayList<>(groupSize - 1);
        size = groupSize;
    }

    public String[] getVictims() {
        return victims.toArray(new String[0]);
    }

    public String getSender() {
        return sender;
    }

    public void addVictim(String victim) {
        victims.add(victim);
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public boolean isFull() {
        return size - 1 == victims.size();
    }
}
