package us.timinc.mc.cobblemon.fossiltweaks

import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.cobblemon.mod.common.pokemon.Pokemon
import net.minecraftforge.event.server.ServerStartedEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import us.timinc.mc.cobblemon.fossiltweaks.config.MainConfig
import us.timinc.mc.cobblemon.fossiltweaks.config.ConfigBuilder
import kotlin.random.Random

@Mod(FossilTweaks.MOD_ID)
object FossilTweaks {
    const val MOD_ID = "fossiltweaks"
    lateinit var config: MainConfig
    var eventsSetup = false

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
    object Registration {
        @SubscribeEvent
        fun onInit(e: ServerStartedEvent) {
            config = ConfigBuilder.load(MainConfig::class.java, MOD_ID)

            if (!eventsSetup) {
                CobblemonEvents.FOSSIL_REVIVED.subscribe { evt ->
                    val pokemon = evt.pokemon
                    val roll = Random.nextInt(config.fossilShinyRate)
                    if (roll == 0) {
                        pokemon.shiny = true
                    }
                }
                eventsSetup = true
            }
        }
    }
}