package de.Roboter007.sheets.utils;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;

public class AttributeUtils {

    public static void setAttribute(Attribute attribute,  LivingEntity entity, double d) {
        AttributeInstance currentScale = entity.getAttribute(attribute);
        if(currentScale != null) {
            currentScale.setBaseValue(d);
        }
    }

    public static void resetAttribute(Attribute attribute,  LivingEntity entity) {
        setAttribute(attribute, entity, getDefault(attribute, entity));
    }

    public static double getAttribute(Attribute attribute, LivingEntity entity) {
        AttributeInstance attributeInstance = entity.getAttribute(attribute);
        if(attributeInstance != null) {
            return attributeInstance.getBaseValue();
        }
        return 1;
    }

    public static double getDefault(Attribute attribute, LivingEntity entity) {
        AttributeInstance attributeInstance = entity.getAttribute(attribute);
        if(attributeInstance != null) {
            return attributeInstance.getDefaultValue();
        }
        return 1;
    }
}
