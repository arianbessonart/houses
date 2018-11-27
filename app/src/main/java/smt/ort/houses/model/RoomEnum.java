package smt.ort.houses.model;

import me.xdrop.fuzzywuzzy.FuzzySearch;

public enum RoomEnum {

    BEDROOM("bedroom", "dormitorio"), KITCHEN("kitchen", "cocina"), BATHROOM("bathroom", "baño"), LIVING("living", "living"), GARAGE("garage", "garaje");

    private String name;

    private String nameEs;

    private RoomEnum(String name, String nameEs) {
        this.name = name;
        this.nameEs = nameEs;
    }

    public static RoomEnum getEnum(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        for (RoomEnum enumItem : RoomEnum.values()) {
            if (FuzzySearch.ratio(enumItem.nameEs.toLowerCase(), name.toLowerCase()) >= 75 ||
                    FuzzySearch.ratio(enumItem.name.toLowerCase(), name.toLowerCase()) >= 75) {
                return enumItem;
            }
        }
        return null;
    }
}
