package com.ssintern.structural.decorator;

import com.ssintern.structural.composite.MenuComponent;

/**
 * Decorator that adds an extra ingredient to a MenuComponent.
 * Uses polymorphism and the Prototype pattern (createModifiedCopy) to clone and
 * modify items.
 * 
 * No instanceof checks - pure OOP design!
 * 
 * Example: new ExtraIngredientDecorator(burger, "Extra Cheese", 2.0)
 */
public class ExtraIngredientDecorator extends MenuItemDecorator {
    private String ingredientName;
    private double extraCost;
    private MenuComponent modifiedItem;

    public ExtraIngredientDecorator(MenuComponent item, String ingredientName, double extraCost) {
        super(item);
        this.ingredientName = ingredientName;
        this.extraCost = extraCost;

        try {
            // Try to create a modified copy (works for MenuItem)
            this.modifiedItem = item.createModifiedCopy();
            this.modifiedItem.addIngredient(ingredientName);
            this.modifiedItem.setPrice(modifiedItem.getPrice() + extraCost);
        } catch (UnsupportedOperationException e) {
            // Component doesn't support modification (e.g., MenuCategory)
            // This is fine - in practice you won't decorate categories
            this.modifiedItem = null;
        }
    }

    @Override
    public String getName() {
        return wrappedItem.getName() + " + " + ingredientName;
    }

    @Override
    public double getPrice() {
        return modifiedItem != null
                ? modifiedItem.getPrice()
                : wrappedItem.getPrice() + extraCost;
    }

    @Override
    public String getDescription() {
        return wrappedItem.getDescription() + ", with extra " + ingredientName.toLowerCase();
    }

    @Override
    public void display(int depth) {
        String indent = "  ".repeat(depth);
        System.out.println(indent + getName() + " - $" + String.format("%.2f", getPrice())
                + " - " + getDescription());
    }

    /**
     * Gets the modified component (with added ingredient).
     * Returns the modified copy if available, otherwise the original wrapped item.
     */
    public MenuComponent getModifiedItem() {
        return modifiedItem != null ? modifiedItem : wrappedItem;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public double getExtraCost() {
        return extraCost;
    }
}
