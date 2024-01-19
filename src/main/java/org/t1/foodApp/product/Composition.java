package org.t1.foodApp.product;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class Composition {
    private List<Ingredient> ingredients = new ArrayList<>();
    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    public boolean isVegan() {
        for (Ingredient ingredient : ingredients) {
            if (!ingredient.isVegan()) {
                return false;
            }
        }
        return true;
    }

    public boolean isVegetarian() {
        for (Ingredient ingredient : ingredients) {
            if (!ingredient.isVegetarian()) {
                return false;
            }
        }
        return true;
    }
}