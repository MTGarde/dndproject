package org.martagarde;

import java.util.HashMap;

public class Action {
    public String description;
    public String result;
    public Action nextAction = null;
    public String requirement; //TODO ?
    public HashMap<String, Integer> producesItems;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Action getNextAction() {
        return nextAction;
    }

    public void setNextAction(Action nextAction) {
        this.nextAction = nextAction;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Action (String desc, String res) {
        setDescription(desc);
        setResult(res);
    }
}
