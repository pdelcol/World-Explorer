package peter.world.explorer;

import static org.lwjgl.opengl.GL11.*;

public class Tile {
	public double y;
	public int buffer = 1;
	public double[]adjacentTiles = new double[3];
	public Tile(double y)
	{
//		this.x = x;
		this.y = y;
//		this.z = z;
	}
	public void GetAdjacentTiles(double[] heights)
	{
		adjacentTiles = heights;
	}
	public void Draw(double x, double z)
	{
		
		glTexCoord3f(0.0f,(float)y,0.0f);
		glVertex3d(z,y,x);
		glTexCoord3f(100.0f,(float)y,0.0f);
		glVertex3d(z + buffer,adjacentTiles[1],x + buffer);
		//TextureBank.Instance().GetTextureAtNum(0).release();
		//TextureBank.Instance().GetTextureAtNum(1).release();
	}
}
//  ASK ABOUT COMPUTER CLUB
