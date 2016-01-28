package com.tr.rdss.generic.model.iqm;

import java.util.ArrayList;
import java.util.List;

public class EntityList {

    List<Entity> entityList = new ArrayList<Entity>();

    public int size() {
        return entityList.size();
    }

    public List<Entity> getEntityList() {
        return entityList;
    }

    public boolean isEmpty() {
        return entityList.size() == 0;
    }

    public boolean contains(Entity e) {
        return entityList.contains(e);
    }

    public void add(Entity e) {
        entityList.add(e);
    }

    public boolean remove(Entity e) {
        return entityList.remove(e);
    }

    public void clear() {
        entityList.clear();
    }

    public Entity get(int index) {
        return entityList.get(index);
    }

    public Entity set(int index, Entity element) {
        return entityList.set(index, element);

    }

    public void add(int index, Entity element) {
        entityList.add(index, element);

    }

    public Entity remove(int index) {
        return entityList.remove(index);
    }

    public int indexOf(Entity o) {
        return entityList.indexOf(o);
    }

    public int lastIndexOf(Entity o) {
        return entityList.lastIndexOf(o);
    }

}
