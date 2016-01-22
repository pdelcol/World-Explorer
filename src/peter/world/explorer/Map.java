package peter.world.explorer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import org.lwjgl.util.Point;
import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.GL11.*;

public class Map {
	final int SIZE = 16;
	final int OCTAVE = 16;
	final int NUM_CHUNKS_LOADED = 6;
	final int NUM_ENEMIES = 1;
	Player player;
	Texture EnemyTex1, EnemyTex2;
	ArrayList<Chunk> chunks = new ArrayList<Chunk>();
	GenerateTerrain generator = new GenerateTerrain();
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	float[][] map; 
	int posX = 4, posY = 4;
	Random rand = new Random();
	public Map(int pId, Player camera, Texture texture, Texture texture2)
	{
		this.player = camera;
		EnemyTex1 = texture;
		EnemyTex2 = texture2;
		OnGameLoad();
	}
	public void OnGameLoad()
	{

			for(int x = 3; x < NUM_CHUNKS_LOADED; x++)
			{
				for(int y = 3; y < NUM_CHUNKS_LOADED; y++)
				{
					chunks.add(load(x,y));
				}
			}
			
		GenerateEnemies();
	}
	
	public void GenerateEnemies()
	{
		for(int i = 0; i < NUM_ENEMIES; i++)
		{
			enemies.add(new Enemy(Rand(),Rand(),Rand(),EnemyTex1,EnemyTex2,this,i));
		}
	}
	public int Rand()
	{
		int x = 0;
//		x = rand.nextInt(16) * 4;
		return x;

		
	}
	
	public boolean CheckForFiles(int x, int y)
	{
		try {
			// Read from disk using FileInputStream
			FileInputStream f_in = new FileInputStream("map/" + x + "," + y
					+ ".txt");
			
			f_in.close();
			return true;
			
		} catch (Exception e) {
			return false;
		}
	}
	public Chunk load(int x, int y) {
		try {
			// Read from disk using FileInputStream
			FileInputStream f_in = new FileInputStream("map/" + x + "," + y
					+ ".txt");

			// Read object using ObjectInputStream
			ObjectInputStream obj_in = new ObjectInputStream(f_in);

			// Read an object
			Object obj = obj_in.readObject();

			if (obj instanceof Chunk) {
				obj_in.close();
				f_in.close();
				return (Chunk) obj;
			}
			
		} catch (Exception e) {
			return CreateChunk(x, y);
		}
		return null;
	}
	
	public void save(Chunk chunk) {

		
			
				try {
					FileOutputStream f_out = new FileOutputStream("map/"
							+ chunk.x + "," + chunk.y + ".txt");

					// Write object with ObjectOutputStream
					ObjectOutputStream obj_out = new ObjectOutputStream(f_out);

					// Write object out to disk
					obj_out.writeObject(chunk);
					obj_out.close();
					f_out.close();
				} catch (Exception e) {
					System.err.println(e);
				}
			
		
	}

	public Chunk CreateChunk(int j, int k)
	{
		Block[][][] blocks = new Block[SIZE][100][SIZE];
		map = generator.generate2(SIZE, OCTAVE);
				for(int x = 0; x < blocks.length; x++)
				{
					for(int z = 0; z < blocks.length; z++)
					{
						int q = j * SIZE;
						int w = k * SIZE;
						if (map[x][z] < .15f) {
							blocks[x][(int) map[x][z]][z] = new Sand(x + q,
									(int) map[x][z], z + w);
						} else {
							blocks[x][(int) map[x][z]][z] = new Grass(x + q,
									(int) map[x][z], z + w);
						}

					}
				}
				return new Chunk(j,k,blocks); 
	}
	
	public void Update()
	{
		//System.out.println(player.getPosition());
		if(-player.getPosition().x >= posX * SIZE)
		{
			for(int i = 0; i < chunks.size(); i++)
			{
				if(chunks.get(i).x == posX-1)
				{
					int y1 = chunks.get(i).y;
					save(chunks.get(i));
					chunks.remove(i);
					chunks.add(load(posX+1,y1));
					i=0;
				}
			}
			posX++;

		}
		else if(-player.getPosition().z >= posY * SIZE)
		{		

			for(int i = 0; i < chunks.size(); i++)
			{
				if(chunks.get(i).y == posY-1)
				{
					int x1 = chunks.get(i).x;
					save(chunks.get(i));
					chunks.remove(i);
					chunks.add(load(x1,posY+1));
					i=0;
				}
			}
			posY++;

		}
		else if(-player.getPosition().x <= ((posX-1)*SIZE))
		{
			for(int i = 0; i < chunks.size(); i++)
			{
				if(chunks.get(i).x == posX+1)
				{
					int y1 = chunks.get(i).y;
					save(chunks.get(i));
					chunks.remove(i);
					chunks.add(load(posX-1,y1));
					i=0;
				}
			}
			posX--;

		}
		else if(-player.getPosition().z <= ((posY-1)*SIZE))
		{
			for(int i = 0; i < chunks.size(); i++)
			{
				if(chunks.get(i).y == posY+1)
				{
					int x1 = chunks.get(i).x;
					save(chunks.get(i));
					chunks.remove(i);
					chunks.add(load(x1,posY-1));
					i=0;
				}
			}
			posY--;

		}
		//System.out.println(""+posX+" "+posY+"");
		//Clean();
		for(int i = 0; i < enemies.size(); i++)
		{
			enemies.get(i).Update();
			if(enemies.get(i).health <= 0)
			{
				enemies.remove(i);
			}
		}
	}
	
	public void Clean()
	{
		for(int i = 0; i < chunks.size(); i++)
		{
			if(chunks.get(i).x < posX-1)
			{
				chunks.remove(i);
			}
			if(chunks.get(i).y < posY+1)
			{
				chunks.remove(i);

			}
			if(chunks.get(i).y < posY-1)
			{
				chunks.remove(i);

			}
			if(chunks.get(i).x > posX+1)
			{
				chunks.remove(i);

			}
		}
	}
	
	public void Draw() {

		for (int x = 0; x < chunks.size(); x++) {
			
				// if (chunk[x][y] != null) {
				chunks.get(x).Render();
				
				// }
			
		}
		for (int x = 0; x < enemies.size(); x++)
		{
			enemies.get(x).Draw();
		}
	}

}
