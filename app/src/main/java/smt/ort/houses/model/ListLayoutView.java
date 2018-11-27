package smt.ort.houses.model;

public enum ListLayoutView {

    LIST("list"), GRID("grid"), LIST_ITEM("list_item");

    private String name;

    private ListLayoutView(String name) {
        this.name = name;
    }

    public static ListLayoutView getFromString(String name) {
        for (ListLayoutView llv : ListLayoutView.values()) {
            if (llv.getName().equalsIgnoreCase(name)) {
                return llv;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
