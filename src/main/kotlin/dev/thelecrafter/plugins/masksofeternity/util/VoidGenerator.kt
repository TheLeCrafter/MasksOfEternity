package dev.thelecrafter.plugins.masksofeternity.util

import org.bukkit.World
import org.bukkit.generator.ChunkGenerator
import java.util.*

class VoidGenerator: ChunkGenerator() {

    override fun canSpawn(world: World, x: Int, z: Int): Boolean {
        return true
    }

    override fun generateChunkData(world: World, random: Random, x: Int, z: Int, biome: BiomeGrid): ChunkData {
        return createChunkData(world)
    }

    override fun shouldGenerateCaves(): Boolean {
        return false
    }

    override fun shouldGenerateDecorations(): Boolean {
        return false
    }

    override fun shouldGenerateMobs(): Boolean {
        return false
    }

    override fun shouldGenerateStructures(): Boolean {
        return false
    }

    override fun isParallelCapable(): Boolean {
        return true
    }

}