package peter.world.explorer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.PNGDecoder;
import org.newdawn.slick.opengl.PNGDecoder.Format;
import org.newdawn.slick.opengl.Texture;

public class Block {
	Texture texture;
	float x, y, z;
	int width = 1;
	int height = 1;
	int length = 1;
	int vboId;
	int vaoId;
	int vertexCount = 0;
	int pId;
	int vboiId;
	int indicesCount;
	ByteBuffer indicesBuffer;
	private int texId;
	public Block(float x,float y,float z, Texture texture)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.texture = texture;
		//SetUpVertexArray();
		
	}
	public void loadPid(int pId)
	{
		this.pId = pId;
	}
	public void SetUpVertexArray()
	{
		//Front Face
		TexturedVertex v0 = new TexturedVertex();
		v0.setXYZ(x, y, z); v0.setRGB(1, 0, 0); v0.setST(0, 1);
		TexturedVertex v1 = new TexturedVertex();
		v1.setXYZ(x + width, y, z); v1.setRGB(0, 1, 0); v1.setST(1, 1);
		TexturedVertex v2 = new TexturedVertex();
		v2.setXYZ(x + width, y + height, z); v2.setRGB(0, 0, 1); v2.setST(1, 0);
		TexturedVertex v3 = new TexturedVertex();
		v3.setXYZ(x, y + height, z); v3.setRGB(1, 1, 1); v3.setST(0, 0);
		//Right Face
		TexturedVertex v4 = new TexturedVertex();
		v4.setXYZ(x + width, y, z); v4.setRGB(1, 0, 0); v4.setST(0, 1);
		TexturedVertex v5 = new TexturedVertex();
		v5.setXYZ(x + width, y, z-length); v5.setRGB(0, 1, 0); v5.setST(1, 1);
		TexturedVertex v6 = new TexturedVertex();
		v6.setXYZ(x + width, y + height, z-length); v6.setRGB(0, 0, 1); v6.setST(1, 0);
		TexturedVertex v7 = new TexturedVertex();
		v7.setXYZ(x+width, y + height, z); v7.setRGB(1, 1, 1); v7.setST(0, 0);
		//Left Face
		TexturedVertex v8 = new TexturedVertex();
		v8.setXYZ(x, y, z); v8.setRGB(1, 0, 0); v8.setST(0, 1);
		TexturedVertex v9 = new TexturedVertex();
		v9.setXYZ(x, y, z-length); v9.setRGB(0, 1, 0); v9.setST(1, 1);
		TexturedVertex v10 = new TexturedVertex();
		v10.setXYZ(x, y + height, z-length); v10.setRGB(0, 0, 1); v10.setST(1, 0);
		TexturedVertex v11 = new TexturedVertex();
		v11.setXYZ(x+width, y + height, z); v11.setRGB(1, 1, 1); v11.setST(0, 0);
		//Top Face
		TexturedVertex v12 = new TexturedVertex();
		v12.setXYZ(x, y + height, z-length); v12.setRGB(1, 0, 0); v12.setST(0, 1);
		TexturedVertex v13 = new TexturedVertex();
		v13.setXYZ(x + width, y + height, z-length); v13.setRGB(0, 1, 0); v13.setST(1, 1);
		TexturedVertex v14 = new TexturedVertex();
		v14.setXYZ(x + width, y + height, z); v14.setRGB(0, 0, 1); v14.setST(1, 0);
		TexturedVertex v15 = new TexturedVertex();
		v15.setXYZ(x, y + height, z); v15.setRGB(1, 1, 1); v15.setST(0, 0);
		//Bottom Face
		TexturedVertex v16 = new TexturedVertex();
		v16.setXYZ(x, y, z-length); v16.setRGB(1, 0, 0); v16.setST(0, 1);
		TexturedVertex v17 = new TexturedVertex();
		v17.setXYZ(x + width, y, z-length); v17.setRGB(0, 1, 0); v17.setST(1, 1);
		TexturedVertex v18 = new TexturedVertex();
		v18.setXYZ(x + width, y, z); v18.setRGB(0, 0, 1); v18.setST(1, 0);
		TexturedVertex v19 = new TexturedVertex();
		v19.setXYZ(x, y, z); v19.setRGB(1, 1, 1); v19.setST(0, 0);
		//Back Face
		TexturedVertex v20 = new TexturedVertex();
		v20.setXYZ(x + width, y, z); v20.setRGB(1, 0, 0); v20.setST(0, 1);
		TexturedVertex v21 = new TexturedVertex();
		v21.setXYZ(x + width, y, z-length); v21.setRGB(0, 1, 0); v21.setST(1, 1);
		TexturedVertex v22 = new TexturedVertex();
		v22.setXYZ(x + width, y + height, z-length); v22.setRGB(0, 0, 1); v22.setST(1, 0);
		TexturedVertex v23 = new TexturedVertex();
		v23.setXYZ(x+width, y + height, z); v23.setRGB(1, 1, 1); v23.setST(0, 0);


//		float[] vertices = {
//			//Front Face
//			x,y,z,
//			x + width, y, z,
//			x + width, y + height, z,
//			x,y+height,z,
//			//Right Face
//			x + width,y,z,
//			x + width,y,z - length,
//			x + width,y + height,z - length,
//			x + width,y + height,z,
//			//Left Face
//			x,y,z,
//			x,y,z - length,
//			x,y + height,z - length,
//			x,y + height,z,
//			//Top Face
//			x,y + height,z - length,
//			x + width,y + height,z - length,
//			x + width,y + height,z,
//			x,y + height,z,
//			//Bottom Face
//			x,y,z - length,
//			x + width,y,z - length,
//			x + width,y,z,
//			x,y,z,
//			//Back Face
//			x,y,z - length,
//			x + width,y,z - length,
//			x + width,y + height,z - length,
//			x,y + height, z - length
//		};
//		TexturedVertex[] vertices = new TexturedVertex[] {v0, v1, v2, v3,v4,v5,v6,v7,v8,v9,v10,v11,v12,v13,v14,v15,v16,v17,v18,v19,v20,v21,v22,v23};
//		// Put each 'Vertex' in one FloatBuffer
//		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length *
//				TexturedVertex.elementCount);
//		for (int i = 0; i < vertices.length; i++) {
//			// Add position, color and texture floats to the buffer
//			verticesBuffer.put(vertices[i].getElements());
//		}
//		verticesBuffer.flip();	
//		// OpenGL expects to draw vertices in counter clockwise order by default
//		byte[] indices = {
//				0, 1, 2,
//				2, 3, 0
//		};
//		indicesCount = indices.length;
//		indicesBuffer = BufferUtils.createByteBuffer(indicesCount);
//		indicesBuffer.put(indices);
//		indicesBuffer.flip();
//		
//		// Create a new Vertex Array Object in memory and select it (bind)
//		vaoId = GL30.glGenVertexArrays();
//		GL30.glBindVertexArray(vaoId);
//		
//		// Create a new Vertex Buffer Object in memory and select it (bind)
//		vboId = GL15.glGenBuffers();
//		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
//		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
//		
//		// Put the position coordinates in attribute list 0
//		GL20.glVertexAttribPointer(0, TexturedVertex.positionElementCount, GL11.GL_FLOAT, 
//				false, TexturedVertex.stride, TexturedVertex.positionByteOffset);
//		// Put the color components in attribute list 1
//		GL20.glVertexAttribPointer(1, TexturedVertex.colorElementCount, GL11.GL_FLOAT, 
//				false, TexturedVertex.stride, TexturedVertex.colorByteOffset);
//		// Put the texture coordinates in attribute list 2
//		GL20.glVertexAttribPointer(2, TexturedVertex.textureElementCount, GL11.GL_FLOAT, 
//				false, TexturedVertex.stride, TexturedVertex.textureByteOffset);
//		
//		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
//		
//		// Deselect (bind to 0) the VAO
//		GL30.glBindVertexArray(0);
//		
//		// Create a new VBO for the indices and select it (bind) - INDICES
//		vboiId = GL15.glGenBuffers();
//		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);
//		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
//		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
//		
//		
////		ByteBuffer buf = null;
////		int tWidth = 0;
////		int tHeight = 0;
////		
////		try {
////			// Open the PNG file as an InputStream
////			InputStream in = new FileInputStream("/resources/grass.png");
////			// Link the PNG decoder to this stream
////			PNGDecoder decoder = new PNGDecoder(in);
////			
////			// Get the width and height of the texture
////			tWidth = decoder.getWidth();
////			tHeight = decoder.getHeight();
////			
////			
////			// Decode the PNG file in a ByteBuffer
////			buf = ByteBuffer.allocateDirect(
////					4 * decoder.getWidth() * decoder.getHeight());
////			decoder.decode(buf, decoder.getWidth() * 4, Format.RGBA);
////			buf.flip();
////			
////			in.close();
////		} catch (IOException e) {
////			e.printStackTrace();
////			System.exit(-1);
////		}
////		ByteBuffer buf = ByteBuffer.allocateDirect(texture.getTextureData().length + 4);
////		buf.put(texture.getTextureData());
////		texId = GL11.glGenTextures();
////		GL13.glActiveTexture(texture.getTextureID());
////		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);
////		
////		// All RGB bytes are aligned to each other and each component is 1 byte
////		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
////		
////		// Upload the texture data and generate mip maps (for scaling)
////		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, (int)texture.getWidth(), (int)texture.getHeight(), 0, 
////				GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
////		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
////		
////		// Setup the ST coordinate system
////		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
////		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
////		
////		// Setup what to do when the texture has to be scaled
////		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, 
////				GL11.GL_NEAREST);
////		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, 
////				GL11.GL_LINEAR_MIPMAP_LINEAR);
	}
	public void Update()
	{
		
	}
	public void Draw()
	{
		glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_TEST);
		//texture.bind();
		
//		GL20.glUseProgram(pId);
//		
//		// Bind the texture
//		
//		//GL13.glActiveTexture(GL13.GL_TEXTURE0);
//		//GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);
//		//texture.bind();
//		// All RGB bytes are aligned to each other and each component is 1 byte
//		//GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
//		
//		// Bind to the VAO that has all the information about the vertices
//		GL30.glBindVertexArray(vaoId);
//		GL20.glEnableVertexAttribArray(0);
//		GL20.glEnableVertexAttribArray(1);
//		GL20.glEnableVertexAttribArray(2);
//		
//		// Bind to the index VBO that has all the information about the order of the vertices
//		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiId);
//		// Draw the vertices
//		GL11.glDrawElements(GL11.GL_QUADS, indicesCount, GL11.GL_UNSIGNED_BYTE, 0);
//		//GL11.glDrawElements(GL11.GL_QUADS, indicesBuffer);
//		// Put everything back to default (deselect)
//		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
//		GL20.glDisableVertexAttribArray(0);
//		GL20.glDisableVertexAttribArray(1);
//		GL20.glDisableVertexAttribArray(2);
//		GL30.glBindVertexArray(0);
//		
//		GL20.glUseProgram(0);
//		
		texture.bind();
		glBegin(GL_QUADS);
	        //FRONT FACE
	        glTexCoord3f(0,1,z);
	        glVertex3f(x,y,z);
	
	        glTexCoord3f(1,1,z);
	        glVertex3f(x + width,y,z);
	
	        glTexCoord3f(1,0,z);
	        glVertex3f(x + width,y + height,z);
	
	        glTexCoord3f(0,0,z);
	        glVertex3f(x,y + height, z);
	
	        //RIGHT FACE
	        glTexCoord3f(0,1,z);
	        glVertex3f(x + width,y,z);
	
	        glTexCoord3f(1,1,z - length);
	        glVertex3f(x + width,y,z - length);
	
	        glTexCoord3f(1,0,z - length);
	        glVertex3f(x + width,y + height,z - length);
	
	        glTexCoord3f(0,0,z);
	        glVertex3f(x + width,y + height,z);
	        //LEFT FACE
	        glTexCoord3f(0,1,z);
	        glVertex3f(x,y,z);
	
	        glTexCoord3f(1,1,z - length);
	        glVertex3f(x,y,z - length);
	
	        glTexCoord3f(1,0,z - length);
	        glVertex3f(x,y + height,z - length);
	
	        glTexCoord3f(0,0,z);
	        glVertex3f(x,y + height,z);
	
	        //TOP FACE
	        glTexCoord3f(0,1,z - length);
	        glVertex3f(x,y + height,z - length);
	
	        glTexCoord3f(1,1,z - length);
	        glVertex3f(x + width,y + height,z - length);
	
	        glTexCoord3f(1,0,z);
	        glVertex3f(x + width,y + height,z);
	
	        glTexCoord3f(0,0,z);
	        glVertex3f(x,y + height,z);
	
	        //BOTTOM FACE
	        glTexCoord3f(0,1,z - length);
	        glVertex3f(x,y,z - length);
	
	        glTexCoord3f(1,1,z - length);
	        glVertex3f(x + width,y,z - length);
	
	        glTexCoord3f(1,0,z);
	        glVertex3f(x + width,y,z);
	
	        glTexCoord3f(0,0,z);
	        glVertex3f(x,y,z);
	        
	        //BACK FACE
	        glTexCoord3f(0,1,z);
	        glVertex3f(x,y,z - length);
	
	        glTexCoord3f(1,1,z);
	        glVertex3f(x + width,y,z - length);
	
	        glTexCoord3f(1,0,z);
	        glVertex3f(x + width,y + height,z - length);
	
	        glTexCoord3f(0,0,z);
	        glVertex3f(x,y + height, z - length);
	        
        glEnd();
		//texture.release();
		
	}
	public void DestroyBlock()
	{
		/*// Disable the VBO index from the VAO attributes list
				GL20.glDisableVertexAttribArray(0);
				
				// Delete the vertex VBO
				GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
				GL15.glDeleteBuffers(vboId);
				
				// Delete the index VBO
				GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
				GL15.glDeleteBuffers(vboiId);
				
				// Delete the VAO
				GL30.glBindVertexArray(0);
				GL30.glDeleteVertexArrays(vaoId);*/
	}
}
