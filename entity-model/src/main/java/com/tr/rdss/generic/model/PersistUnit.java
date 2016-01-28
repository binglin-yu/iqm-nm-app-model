package com.tr.rdss.generic.model;

public abstract class PersistUnit {

    public DaoAction action;

    public PersistUnit(DaoAction action) {
        this.action = action;
    }

    public void setAction(DaoAction daoaction) {
        action = daoaction;
    }

    public DaoAction getAction() {
        return action;
    }
}


/*
 * $LastChangedDate$ 
 * $LastChangedRevision$
 * 
 */

// End of file
