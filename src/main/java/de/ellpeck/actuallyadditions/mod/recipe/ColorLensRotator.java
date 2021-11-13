//package de.ellpeck.actuallyadditions.mod.recipe;
//
//import de.ellpeck.actuallyadditions.api.internal.IAtomicReconstructor;
//import de.ellpeck.actuallyadditions.api.recipe.IColorLensChanger;
//import net.minecraft.block.BlockState;
//import net.minecraft.item.DyeColor;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.math.BlockPos;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class ColorLensRotator implements IColorLensChanger {
//
//    public static final Map<String, DyeColor> STRING_TO_ENUM = new HashMap<>();
//
//    static {
//        String[] dyes = {"White", "Orange", "Magenta", "LightBlue", "Yellow", "Lime", "Pink", "Gray", "LightGray", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Black"};
//        for (int i = 0; i < dyes.length; i++) {
//            STRING_TO_ENUM.put("dye" + dyes[i], DyeColor.byMetadata(i));
//        }
//    }
//
//    final List<ItemStack> rotations;
//
//    public ColorLensRotator(List<ItemStack> rotations) {
//        this.rotations = rotations;
//    }
//
//    @Override
//    public ItemStack modifyItem(ItemStack stack, BlockState hitBlockState, BlockPos hitBlock, IAtomicReconstructor tile) {
//
//        int idx = -1;
//
//        for (int i : OreDictionary.getOreIDs(stack)) {
//            String s = OreDictionary.getOreName(i);
//            if (s.startsWith("dye")) {
//                DyeColor color = STRING_TO_ENUM.get(s);
//                if (color != null) {
//                    idx = color.getMetadata();
//                    break;
//                }
//            }
//        }
//
//        if (idx == -1) {
//            return ItemStack.EMPTY;
//        }
//
//        ItemStack s = this.rotations.get((idx + 1) % this.rotations.size()).copy();
//        s.setCount(stack.getCount());
//        return s;
//    }
//
//}
