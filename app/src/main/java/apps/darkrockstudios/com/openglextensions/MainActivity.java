package apps.darkrockstudios.com.openglextensions;

import android.app.Activity;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ShareActionProvider;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends Activity implements GlInfoListener
{
	private GLRenderer          m_glRenderer;
	private OpenGLInfo m_openGLInfo;
	private ShareActionProvider m_shareActionProvider;

	private ExtensionsAdapter m_adapter;

	private MenuItem m_shareItem;

	@InjectView(R.id.gl_surface_view)
	GLSurfaceView m_glView;

	@InjectView(R.id.extensions_list)
	ListView m_extensionsListView;

	@InjectView(R.id.extensions_search_box)
	EditText m_extensionsSearchView;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );
		ButterKnife.inject( this );

		m_glRenderer = new GLRenderer( this );

		m_glView.setEGLContextClientVersion( 2 );
		m_glView.setRenderer( m_glRenderer );
		m_glView.setRenderMode( GLSurfaceView.RENDERMODE_WHEN_DIRTY );

		m_adapter = new ExtensionsAdapter( this );
		m_extensionsListView.setAdapter( m_adapter );

		m_extensionsSearchView.addTextChangedListener( new SearchListener() );
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu )
	{
		getMenuInflater().inflate( R.menu.main, menu );

		m_shareItem = menu.findItem( R.id.menu_item_share );
		m_shareActionProvider = (ShareActionProvider) m_shareItem.getActionProvider();

		setShareIntent( createShareIntent() );

		return true;
	}

	// Call to update the share intent
	private void setShareIntent( Intent shareIntent )
	{
		if( m_shareActionProvider != null && shareIntent != null )
		{
			m_shareActionProvider.setShareIntent( shareIntent );
		}

		if( m_shareItem != null )
		{
			m_shareItem.setEnabled( shareIntent != null );
		}
	}

	@Override
	public void gotGlExtensions( final OpenGLInfo openGLInfo )
	{
		m_openGLInfo = openGLInfo;

		setShareIntent( createShareIntent() );

		runOnUiThread( new UpdateExtensions() );
	}

	private Intent createShareIntent()
	{
		final Intent intent;

		if( m_openGLInfo != null )
		{
			intent = new Intent();
			intent.setAction( Intent.ACTION_SEND );
			intent.putExtra( Intent.EXTRA_TEXT, m_openGLInfo.toString() );
			intent.setType( "text/plain" );
		}
		else
		{
			intent = null;
		}

		return intent;
	}

	private void updateSearch()
	{
		if( m_extensionsSearchView != null )
		{
			final String searchTerm = m_extensionsSearchView.getText().toString();
			if( !TextUtils.isEmpty( searchTerm ) )
			{
				m_adapter.getFilter().filter( searchTerm );
			}
		}
	}

	private class UpdateExtensions implements Runnable
	{
		@Override
		public void run()
		{
			m_adapter.clear();

			if( m_openGLInfo != null )
			{
				m_adapter.setExtensions( m_openGLInfo.m_extensions );
				updateSearch();
			}
			else
			{

			}

			m_adapter.notifyDataSetChanged();
		}
	}

	private class SearchListener implements TextWatcher
	{
		@Override
		public void beforeTextChanged( final CharSequence charSequence, final int i, final int i2, final int i3 )
		{

		}

		@Override
		public void onTextChanged( final CharSequence charSequence, final int i, final int i2, final int i3 )
		{
			m_adapter.getFilter().filter( charSequence );
		}

		@Override
		public void afterTextChanged( final Editable editable )
		{

		}
	}
}
