package com.keerill.payday;

import com.keerill.payday.entity.ai.EntityAIEatingMoney;

import net.minecraft.entity.passive.EntitySheep;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EventHandler 
{
	@SubscribeEvent
    public static void mobEvent(LivingSpawnEvent event)
    {
		if (event.getEntity() instanceof EntitySheep)
		{
			EntitySheep entity = (EntitySheep) event.getEntity();
			
			if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(entity.world, entity))
			{
				entity.tasks.addTask(0, new EntityAIEatingMoney(entity, 3.0D, 8));
			}
		}
    }
}
