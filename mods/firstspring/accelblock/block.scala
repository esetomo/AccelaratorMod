package mods.firstspring.accelblock

import java.util.ArrayList
import java.util.Random

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World
import net.minecraftforge.common.ForgeDirection
import scala.collection.JavaConversions._

trait BlockAccel extends Block
{
	this.setUnlocalizedName("firstspring/accelblock:block")
	this.setHardness(.5f)
	this.setCreativeTab(CreativeTabs.tabBlock)
	GameRegistry.registerBlock(this)

	override def onBlockActivated(
			world: World,
			x: Int,
			y: Int,
			z: Int,
			player: EntityPlayer,
			side: Int,
			dx: Float,
			dy: Float,
			dz: Float): Boolean =
		{
			if(world.isRemote)
			{
				return true
			}

			var meta = world.getBlockMetadata(x, y, z)

			meta += 1
			if(meta > 14)
			{
				meta = 0
			}
			world.setBlockMetadataWithNotify(x, y, z, meta, 0)
			player.sendChatToPlayer("Change to " + (meta + 2) + "x")
			world.scheduleBlockUpdate(x, y, z, this.blockID, 1)
			true
		}

	override def updateTick(
			world : World,
			x : Int,
			y : Int,
			z : Int,
			rand : Random
			)
	{
		val arr = new ArrayList[TileEntity]
		val dirs = Array(
			ForgeDirection.EAST,
			ForgeDirection.WEST,
			ForgeDirection.SOUTH,
			ForgeDirection.NORTH,
			ForgeDirection.UP,
			ForgeDirection.DOWN)
		for(dir <- dirs)
		{
			val tile = world.getBlockTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)
			if(tile != null)
				arr.add(tile)
		}

		val meta = world.getBlockMetadata(x, y, z)
		for(i <- 1 to meta + 2)
		{
			arr.foreach(tile => tile.updateEntity) 
		}
		world.scheduleBlockUpdate(x, y, z, this.blockID, 1)
	}
}