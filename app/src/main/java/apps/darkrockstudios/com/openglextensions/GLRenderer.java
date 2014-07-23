package apps.darkrockstudios.com.openglextensions;

import android.opengl.EGLConfig;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by abrown on 7/22/2014.
 */
public class GLRenderer implements GLSurfaceView.Renderer
{
	private final GlInfoListener m_listener;

	public GLRenderer( GlInfoListener listener )
	{
		m_listener = listener;
	}

	public void onSurfaceCreated( GL10 unused, EGLConfig config )
	{
		// Set the background frame color
		GLES20.glClearColor( 0.0f, 0.0f, 0.0f, 1.0f );
	}

	public void onDrawFrame( GL10 unused )
	{
		// Redraw background color
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void onSurfaceCreated( final GL10 gl10, final javax.microedition.khronos.egl.EGLConfig eglConfig )
	{
		OpenGLInfo glInfo = new OpenGLInfo( gl10 );
		m_listener.gotGlExtensions( glInfo );
	}

	public void onSurfaceChanged(GL10 unused, int width, int height) {
		GLES20.glViewport(0, 0, width, height);
	}
}
