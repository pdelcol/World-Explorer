package peter.world.explorer;

import java.io.Serializable;

public class Chunk implements Serializable{
	Block[][][] blocks = new Block[16][100][16];
	// X and Y used for identifying where it exists in the world
	int x,y;
	
	public Chunk(int x, int y, Block[][][] blocks)
	{
		this.x = x;
		this.y = y;
		this.blocks = blocks;
	}
	public void Update()
	{
		for(int x = 0; x < blocks.length; x++)
		{
			for(int y = 0; y < blocks[0].length; y++)
			{
				for(int z = 0; z < blocks[0][0].length; z++)
				{
					blocks[x][y][z].Update();
				}
			}
		}
	}
	public void Render()
	{
		for(int x = 0; x < blocks.length; x++)
		{
			for(int y = 0; y < blocks[0].length; y++)
			{
				for(int z = 0; z < blocks[0][0].length; z++)
				{
					if(blocks[x][y][z] != null)
						blocks[x][y][z].Draw();
				}
			}
		}
	}
}
