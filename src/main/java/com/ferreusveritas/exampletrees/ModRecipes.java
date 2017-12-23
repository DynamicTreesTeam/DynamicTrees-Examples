package com.ferreusveritas.exampletrees;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = ModConstants.MODID)
public class ModRecipes {

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void registerRecipes(final RegistryEvent.Register<IRecipe> event) {
		final IForgeRegistry<IRecipe> registry = event.getRegistry();
			
		GameRegistry.addSmelting(ModBlocks.ironLog, new ItemStack(Items.IRON_INGOT), 0);
	}
	
}
