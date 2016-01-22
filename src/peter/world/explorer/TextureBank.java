package peter.world.explorer;

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class TextureBank {
	static TextureBank bank = new TextureBank();
	
	ArrayList<Texture> textures = new ArrayList<Texture>();
	
	public static TextureBank Instance()
	{
		return bank;
	}
	
	public void LoadTextures(String imageName)
	{
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("/resources/"+imageName+".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AddTexture(texture);
	}
	
	public Texture GetTextureAtNum(int num)
	{
		return textures.get(num);
	}
	public void AddTexture(Texture texture)
	{
		textures.add(texture);
		System.out.println("Texture Successfully Added at:" + textures.indexOf(texture));
	}
	public void RemoveTexture(Texture texture)
	{
		textures.remove(textures);
	}
	public void RemoveTexture(int index)
	{
		textures.remove(index);
	}
	
}
