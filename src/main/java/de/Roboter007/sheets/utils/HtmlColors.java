package de.Roboter007.sheets.utils;

import net.kyori.adventure.text.format.TextColor;

import java.util.ArrayList;
import java.util.List;

public enum HtmlColors {

    black("#000000"),
    dark_blue("#0000AA"),
    dark_green("#00AA00"),
    dark_aqua("#00AAAA"),
    dark_red("#AA0000"),
    dark_purple("#AA00AA"),
    gold("#FFAA00"),
    gray("#AAAAAA"),
    dark_gray("#555555"),
    blue("#5555FF"),
    green("#55FF55"),
    aqua("#55FFFF"),
    red("#FF5555"),
    light_purple("#FF55FF"),
    yellow("#FFFF55"),
    white("#FFFFFF");

    private final String htmlColor;

    HtmlColors(String htmlColor) {
        this.htmlColor = htmlColor;
    }

    public String getHtmlColor() {
        return htmlColor;
    }

    public TextColor getTextColor() {
        return TextColor.fromHexString(this.htmlColor);
    }

    public static boolean isHtmlColor(String color) {
        return colors().contains(color);

    }

    public static List<String> colors() {
        List<String> colors = new ArrayList<>();
        for(HtmlColors htmlColors : HtmlColors.values()) {
            colors.add(htmlColors.toString());
        }
        return colors;
    }

    public static String getColor(String colorName) {
        if(isHtmlColor(colorName)) {
            HtmlColors htmlColors = HtmlColors.valueOf(colorName);
            return htmlColors.getHtmlColor();
        } else {
            return colorName;
        }
    }
}
