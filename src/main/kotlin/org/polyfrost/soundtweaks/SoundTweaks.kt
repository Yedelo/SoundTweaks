package org.polyfrost.soundtweaks

import net.fabricmc.api.ModInitializer
import net.minecraft.client.Minecraft
import net.minecraft.resources.Identifier
import org.polyfrost.oneconfig.api.event.v1.EventManager
import org.polyfrost.oneconfig.api.event.v1.events.ResourceFinishedLoading
import org.polyfrost.soundtweaks.config.SoundTweaksConfig
import org.polyfrost.soundtweaks.mixins.SoundManagerAccessor
import org.polyfrost.soundtweaks.mixins.SoundRegistryAccessor

object SoundTweaks : ModInitializer {
    const val ID = "@MOD_ID@"
    const val NAME = "@MOD_NAME@"

    val volumes = mutableMapOf<Identifier, Float>()

    override fun onInitialize() {
        // Never fires on 1.21.1 and 1.21.4 see OneConfig issue 749
        EventManager.register(ResourceFinishedLoading::class) { _ ->
            if (config == null) {
                config = SoundTweaksConfig()
            }
            config?.preload()
        }
    }

    @JvmStatic
    var config: SoundTweaksConfig? = null

    fun getSounds(): Map<Identifier, Any> {
        val soundManager = Minecraft.getInstance().soundManager
        val registry = (soundManager as? SoundManagerAccessor)?.registry ?: return mutableMapOf()
        //? if 1.8.9 {
        /*return (registry as SoundRegistryAccessor).sounds
        *///?} else {
        return registry
        //?}
    }
}
