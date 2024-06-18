package org.example;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

public class ConfigAgent {
    private List<String> categories;
    private Map<String, Integer> priorities;
    private PropertyChangeSupport support;

    public ConfigAgent(List<String> categories, Map<String, Integer> priorities) {
        this.categories = categories;
        this.priorities = priorities;
        this.support = new PropertyChangeSupport(this);
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        List<String> oldCategories = this.categories;
        this.categories = categories;
        support.firePropertyChange("categories", oldCategories, categories);
    }

    public Map<String, Integer> getPriorities() {
        return priorities;
    }

    public void setPriorities(Map<String, Integer> priorities) {
        Map<String, Integer> oldPriorities = this.priorities;
        this.priorities = priorities;
        support.firePropertyChange("priorities", oldPriorities, priorities);
    }

    public void changeCategoryPriority(String category, int priority) {
        int oldPriority = priorities.getOrDefault(category, 0);
        priorities.put(category, priority);
        support.firePropertyChange("priority", oldPriority, priority);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}

