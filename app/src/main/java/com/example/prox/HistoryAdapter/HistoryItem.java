package com.example.prox.HistoryAdapter;

public class HistoryItem {
    String search = "";
    String found = "";

    public HistoryItem(String name, String store) {
        this.search = name;
        this.found = store;
    }


    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getFound() {
        return found;
    }

    public void setFound(String found) {
        this.found = found;
    }
}
