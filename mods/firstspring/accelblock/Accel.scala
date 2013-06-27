package mods.firstspring.accelblock

import java.io.File
import cpw.mods.fml.common.Loader
import cpw.mods.fml.common.Mod
import net.minecraftforge.common.Configuration
import cpw.mods.fml.common.Mod.PreInit
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import net.minecraft.block.Block
import net.minecraft.block.material.Material

@Mod(
  modLanguage = "scala",
  modid = "AccelMod",
  name = "AccelaratorMod"
)
object Accel
{
  lazy val cfg = new Configuration(new File(Loader.instance.getConfigDir, "AccelMod.cfg"))
  
  @PreInit
  def preInit(event : FMLPreInitializationEvent)
  {
    cfg.load
    val id = cfg.getBlock("BlockID", 2194).getInt()
    object blockAccel extends Block(id, Material.iron) with BlockAccel
    blockAccel
    cfg.save
    
  }

}