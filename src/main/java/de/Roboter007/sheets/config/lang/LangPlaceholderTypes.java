package de.Roboter007.sheets.config.lang;

public enum LangPlaceholderTypes {
    PLAYER_NAME("%playerName%"),
    ENTITY_TYPE("%entityType%"),
    MATERIAL_TYPE("%materialType%");

    private final String placeholder;

    LangPlaceholderTypes(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getPlaceholder() {
        return placeholder;
    }


}
