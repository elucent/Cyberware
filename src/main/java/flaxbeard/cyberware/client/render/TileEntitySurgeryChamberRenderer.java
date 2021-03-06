package flaxbeard.cyberware.client.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;

import org.lwjgl.opengl.GL11;

import flaxbeard.cyberware.client.ClientUtils;
import flaxbeard.cyberware.common.CyberwareContent;
import flaxbeard.cyberware.common.block.BlockSurgeryChamber;
import flaxbeard.cyberware.common.block.tile.TileEntitySurgeryChamber;

public class TileEntitySurgeryChamberRenderer extends TileEntitySpecialRenderer<TileEntitySurgeryChamber>
{
	private static ModelSurgeryChamber model = new ModelSurgeryChamber();
	private static String texture = "cyberware:textures/models/surgery_chamber_door.png";

	@Override
	public void renderTileEntityAt(TileEntitySurgeryChamber te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		if (te != null)
		{
			float ticks = Minecraft.getMinecraft().player.ticksExisted + partialTicks;
			
			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glTranslated(x+.5, y+.5, z+.5);
			
			IBlockState state = te.getWorld().getBlockState(te.getPos());
			if (state.getBlock() == CyberwareContent.surgeryChamber)
			{
				
				EnumFacing facing = state.getValue(BlockSurgeryChamber.FACING);
				
				switch (facing)
				{
					case EAST:
						GL11.glRotatef(90F, 0F, 1F, 0F);
						break;
					case NORTH:
						GL11.glRotatef(180F, 0F, 1F, 0F);
						break;
					case SOUTH:
						break;
					case WEST:
						GL11.glRotatef(270F, 0F, 1F, 0F);
						break;
					default:
						break;
				}
				ClientUtils.bindTexture(texture);
	
				boolean isOpen = state.getValue(BlockSurgeryChamber.OPEN);
				if (isOpen != te.lastOpen)
				{
					te.lastOpen = isOpen;
					te.openTicks = ticks;
				}
				
				float ticksPassed = Math.min(10, ticks - te.openTicks);
				float rotate = (float) (Math.sin(ticksPassed * ((Math.PI / 2) / 10F)) * 90F);
						
				if (!isOpen)
				{
					rotate = 90F - (float) (Math.sin(ticksPassed * ((Math.PI / 2) / 10F)) * 90F);
				}
	
				GL11.glPushMatrix();
				GL11.glTranslatef(-6F / 16F, 0F, -6F / 16F);
				GL11.glRotatef(-rotate, 0F, 1F, 0F);
				model.render(null, 0, 0, 0, 0, 0, .0625f);
				GL11.glPopMatrix();
				
				GL11.glPushMatrix();
				GL11.glTranslatef(6F / 16F, 0F, -6F / 16F);
				GL11.glRotatef(rotate, 0F, 1F, 0F);
				model.renderRight(null, 0, 0, 0, 0, 0, .0625f);
				GL11.glPopMatrix();
					
				GL11.glPopMatrix();
			}
		}
	}

}
