package com.darkrockstudios.apps.openglextensions;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Adam on 7/22/2014.
 */
public class OpenGLInfo
{
	public final String m_extensions;
	public final String m_renderer;
	public final String m_vendor;
	public final String m_version;

	public OpenGLInfo( final GL10 gl )
	{
		m_extensions = gl.glGetString( GL10.GL_EXTENSIONS );
		m_renderer = gl.glGetString( GL10.GL_RENDERER );
		m_vendor = gl.glGetString( GL10.GL_VENDOR );
		m_version = gl.glGetString( GL10.GL_VERSION );
	}

	@Override
	public String toString()
	{
		return m_vendor + '\n' +
		       m_renderer + '\n' +
		       m_version + "\n\n" +
		       m_extensions;
	}
}
