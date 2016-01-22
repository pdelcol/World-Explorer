package peter.world.explorer;


import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_PERSPECTIVE_CORRECTION_HINT;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL20;
import org.newdawn.slick.TrueTypeFont;

public class Runner extends Applet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean close = true;
	long lastFPS;
	int fps = 0;
	float angle1 = 2.0f % 360;
	
	float dx = 0.0f;
	float dy = 0.0f;
	float dt = 0.0f;
	float lastTime = 0.0f;
	float time = 0.0f;
	
	float mouseSensitivity = 0.10f;
	float movementSpeed = 0.5f;
	Player camera = new Player(-61f,-3f,-61f);
	Map map;
	Enemy character;
	Input input;
	
	Random rand = new Random();
	Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
	TrueTypeFont font;
	boolean isWhite = false;
	boolean isWireframe = true;
	
	public int width = 800;
	public int height = 600;
	
	private FloatBuffer lightPosition;
    private FloatBuffer whiteLight; 
    private FloatBuffer lModelAmbient;
    
	Canvas display_parent;
	Thread gameThread;
	boolean running = true;
	
    int vId;
    int pId;
    int fsId;
    
	
	
	/**
	 * Tell game loop to stop running, after which the LWJGL Display will 
	 * be destoryed. The main thread will wait for the Display.destroy().
	 */
	
    
    
	public void Load() {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		
		Display.setTitle("World Explorer");
		
		
		initRendering();
		initGameFunctions();
		
		Tick();
		//destroy();
	}
	
	public int loadShader(String filename, int type) {
		StringBuilder shaderSource = new StringBuilder();
		int shaderID = 0;
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = reader.readLine()) != null) {
				shaderSource.append(line).append("\n");
			}
			reader.close();
		} catch (IOException e) {
			System.err.println("Could not read file.");
			e.printStackTrace();
			System.exit(-1);
		}
		
		shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		
		return shaderID;
	}
	public void initRendering() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(45.0f,Display.getWidth() / Display.getHeight(), 1.0f,1000.0f);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glClearColor(0.0f,0.0f,0.0f,0.5f);
		//glClearDepth(1.0f);
		glDepthFunc(GL_LEQUAL);
		glEnable(GL_DEPTH_TEST);
		glShadeModel(GL_SMOOTH);
		
		//glEnable(GL11.GL_CULL_FACE);
		//glCullFace(GL_BACK);
		lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(1.0f).put(1.0f).put(100.0f).put(-100.0f).flip();
        
        whiteLight = BufferUtils.createFloatBuffer(4);
        whiteLight.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();

        lModelAmbient = BufferUtils.createFloatBuffer(4);
        lModelAmbient.put(0.5f).put(0.5f).put(0.5f).put(1.0f).flip();
        
		//glLight(GL_LIGHT0, GL_POSITION, lightPosition);                         // sets light position
        //glLight(GL_LIGHT0, GL_SPECULAR, whiteLight);                            // sets specular light to white
        //glLight(GL_LIGHT0, GL_DIFFUSE, whiteLight);                                     // sets diffuse light to white
        //glLightModel(GL_LIGHT_MODEL_AMBIENT, lModelAmbient);            // global ambient light 
        //glEnable(GL_LIGHTING);                                                                          // enables lighting
        //glEnable(GL_LIGHT0);
		
		//glEnable(GL_LIGHTING);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		glPolygonMode(GL_FRONT,GL_FILL);
		//glEnable(GL11.GL_CULL_FACE);
		glEnable(GL_TEXTURE_2D);               
		//vId = this.loadShader("src/peter/world/explorer/VertShader.glsl",GL20.GL_VERTEX_SHADER);
		//fsId = this.loadShader("src/peter/world/explorer/FragShader.glsl",GL20.GL_FRAGMENT_SHADER);
		
		pId = GL20.glCreateProgram();
		GL20.glAttachShader(pId, vId);
		GL20.glAttachShader(pId, fsId);
		GL20.glLinkProgram(pId);
		
		GL20.glBindAttribLocation(pId,0,"in_Position");
		GL20.glBindAttribLocation(pId,1,"in_Color");
		GL20.glBindAttribLocation(pId, 2, "in_TextureCoord");
		
		GL20.glValidateProgram(pId);
	}
	
	public void initGameFunctions()
	{
		
		TextureBank.Instance().LoadTextures("grass");
		TextureBank.Instance().LoadTextures("sand");
		TextureBank.Instance().LoadTextures("stone");
		TextureBank.Instance().LoadTextures("tree");
		TextureBank.Instance().LoadTextures("leaf");
		TextureBank.Instance().LoadTextures("character");
		TextureBank.Instance().LoadTextures("whiteTexture");
		//TextureBank.bank.textures.add(null);
		try{
			font = new TrueTypeFont(awtFont, true);
		}catch(Exception e){
			e.printStackTrace();
		}
		map = new Map(pId,camera, TextureBank.Instance().GetTextureAtNum(5), TextureBank.Instance().GetTextureAtNum(6));
		//map.GenerateTerrain();
		//character = new Character(map.SIZE/2, (int)map.map[map.SIZE/2][map.SIZE/2] + 4,map.SIZE/2, TextureBank.Instance().GetTextureAtNum(5), TextureBank.Instance().GetTextureAtNum(6), map);
		lastFPS = getTime();
		input = new Input(mouseSensitivity, movementSpeed, camera, map);
	}

	public void Tick() {		
		Mouse.setGrabbed(true);
		
		while (running) {
			Update();
			Render();
		}
		Display.destroy();

	}

	public void Update() {
		if(Display.isCloseRequested() || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			running = false;
		}
		time = Sys.getTime();
		dt = (time - lastTime)/1000.0f;
		lastTime = time;
		
		input.Update(dt);
		
		glLoadIdentity();
		camera.lookThrough();
		getFPS();
		map.Update();
		camera.Update(map, dt);
		//character.Update();
		Display.sync(30);
		Display.update();
	}

	
	public void Render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		
		
		
		map.Draw();
		//character.Draw();
		make2D();
		//2D rendering
		TextureBank.Instance().GetTextureAtNum(5).bind();
		glBegin(GL_LINES);
			glColor3f(1f,1f,1f);
			glVertex2f(-10f,0f);
			glVertex2f(10f,0f);
			glVertex2f(0f,-10f);
			glVertex2f(0f,10f);
		glEnd();
		
		make3D();
		
	}
	
	public void make2D() {
        //Remove the Z axis
		glPolygonMode(GL_FRONT_AND_BACK,GL_FILL);
        glDisable(GL_DEPTH_TEST);
        glMatrixMode(GL_PROJECTION);
        glPushMatrix();
        glLoadIdentity();
        glOrtho(-width/2, width/2, -height/2, height/2, -1, 1);
        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();
        glLoadIdentity();
    }

    public void make3D() {
        //Restore the Z axis
        glMatrixMode(GL_PROJECTION);
        glPopMatrix();
        glMatrixMode(GL_MODELVIEW);
        glPopMatrix();
        glEnable(GL_DEPTH_TEST);
    }

	public void getFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}

	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
}
