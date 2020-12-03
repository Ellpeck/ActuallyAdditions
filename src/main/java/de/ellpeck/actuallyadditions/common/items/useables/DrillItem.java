package de.ellpeck.actuallyadditions.common.items.useables;

import de.ellpeck.actuallyadditions.common.config.Config;
import de.ellpeck.actuallyadditions.common.container.DrillContainer;
import de.ellpeck.actuallyadditions.common.items.CrystalFluxItem;
import de.ellpeck.actuallyadditions.common.utilities.Help;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

public class DrillItem extends CrystalFluxItem {
    public DrillItem() {
        super(
                baseProps()
                    .maxDamage(0)
                    .setNoRepair()
                    .addToolType(ToolType.PICKAXE, 4)
                    .addToolType(ToolType.SHOVEL, 4),
                Config.ITEM_CONFIG.drillMaxEnergy::get,
                1000
        );
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand handIn) {
        if (player.isSneaking() && !worldIn.isRemote && handIn == Hand.MAIN_HAND) {
            NetworkHooks.openGui((ServerPlayerEntity) player, new SimpleNamedContainerProvider(
                    (windowId, playerInv, playerEntity) -> new DrillContainer(windowId, playerInv),
                    Help.trans("gui.name.drill")
            ));
        }

        return super.onItemRightClick(worldIn, player, handIn);
    }
}
