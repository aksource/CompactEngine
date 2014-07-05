package compactengine;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.world.World;

import java.util.HashMap;

/**
 * Created by A.K. on 14/07/03.
 */
public class ExtendedShapedRecipe extends ShapedRecipes {

    public ExtendedShapedRecipe(ItemStack output, Object... objects) {
        this(3, 3, getItemStackArray(objects), output);

    }


    public ExtendedShapedRecipe(int width, int height, ItemStack[] inputs, ItemStack output) {
        super(width, height, inputs, output);
    }

    public static ItemStack[] getItemStackArray(Object... objects) {
        String s = "";
        int i = 0;
        int j = 0;
        int k = 0;

        if (objects[i] instanceof String[])
        {
            String[] astring = (String[])((String[])objects[i++]);

            for (int l = 0; l < astring.length; ++l)
            {
                String s1 = astring[l];
                ++k;
                j = s1.length();
                s = s + s1;
            }
        }
        else
        {
            while (objects[i] instanceof String)
            {
                String s2 = (String)objects[i++];
                ++k;
                j = s2.length();
                s = s + s2;
            }
        }

        HashMap<Character, ItemStack> hashmap;

        for (hashmap = new HashMap<>(); i < objects.length; i += 2)
        {
            Character character = (Character)objects[i];
            ItemStack itemstack1 = null;

            if (objects[i + 1] instanceof Item)
            {
                itemstack1 = new ItemStack((Item)objects[i + 1]);
            }
            else if (objects[i + 1] instanceof Block)
            {
                itemstack1 = new ItemStack((Block)objects[i + 1], 1, 32767);
            }
            else if (objects[i + 1] instanceof ItemStack)
            {
                itemstack1 = (ItemStack)objects[i + 1];
            }

            hashmap.put(character, itemstack1);
        }

        ItemStack[] aitemstack = new ItemStack[j * k];

        for (int i1 = 0; i1 < j * k; ++i1)
        {
            char c0 = s.charAt(i1);

            if (hashmap.containsKey(Character.valueOf(c0)))
            {
                aitemstack[i1] = ((ItemStack)hashmap.get(Character.valueOf(c0))).copy();
            }
            else
            {
                aitemstack[i1] = null;
            }
        }
        return aitemstack;
    }

    @Override
    public boolean matches(InventoryCrafting inventoryCrafting, World world)
    {
        for (int i = 0; i <= 3 - this.recipeWidth; ++i)
        {
            for (int j = 0; j <= 3 - this.recipeHeight; ++j)
            {
                if (this.checkMatch(inventoryCrafting, i, j, true))
                {
                    return true;
                }

                if (this.checkMatch(inventoryCrafting, i, j, false))
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks if the region of a crafting inventory is match for the recipe.
     */
    private boolean checkMatch(InventoryCrafting p_77573_1_, int p_77573_2_, int p_77573_3_, boolean p_77573_4_)
    {
        for (int k = 0; k < 3; ++k)
        {
            for (int l = 0; l < 3; ++l)
            {
                int i1 = k - p_77573_2_;
                int j1 = l - p_77573_3_;
                ItemStack itemstack = null;

                if (i1 >= 0 && j1 >= 0 && i1 < this.recipeWidth && j1 < this.recipeHeight)
                {
                    if (p_77573_4_)
                    {
                        itemstack = this.recipeItems[this.recipeWidth - i1 - 1 + j1 * this.recipeWidth];
                    }
                    else
                    {
                        itemstack = this.recipeItems[i1 + j1 * this.recipeWidth];
                    }
                }

                ItemStack itemstack1 = p_77573_1_.getStackInRowAndColumn(k, l);

                if (itemstack1 != null || itemstack != null)
                {
                    if (itemstack1 == null || itemstack == null)
                    {
                        return false;
                    }
                    if (!itemstack.isItemEqual(itemstack1) || !ItemStack.areItemStackTagsEqual(itemstack, itemstack1)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
