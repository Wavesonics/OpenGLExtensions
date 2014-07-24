package com.darkrockstudios.apps.openglextensions;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by abrown on 7/22/2014.
 */
public class ExtensionsAdapter extends ArrayAdapter<String> implements Filterable
{
	private List<String> m_extensions;

	public ExtensionsAdapter( final Context context )
	{
		super( context, android.R.layout.simple_list_item_1, android.R.id.text1 );
		m_extensions = new ArrayList<>();
	}

	public void setExtensions( final String extensionStr )
	{
		clear();
		final String[] extensions = extensionStr.split( "\\s+" );
		Collections.addAll( m_extensions, extensions );
		addAll( m_extensions );
	}

	@Override
	public Filter getFilter()
	{
		return new ExtensionFilter();
	}

	public class ExtensionFilter extends Filter
	{
		public ExtensionFilter()
		{

		}

		@Override
		protected FilterResults performFiltering( final CharSequence constraint )
		{
			FilterResults results = new FilterResults();
			ArrayList<String> filteredExtensions = new ArrayList<>();

			final String lowerCaseConstraint = constraint.toString().toLowerCase();
			for( String extension : m_extensions )
			{
				if( extension.toLowerCase().contains( lowerCaseConstraint ) )
				{
					filteredExtensions.add( extension );
				}
			}

			results.count = filteredExtensions.size();
			results.values = filteredExtensions;

			return results;
		}

		@Override
		protected void publishResults( final CharSequence charSequence, final FilterResults filterResults )
		{
			clear();

			if( filterResults.count > 0 )
			{
				addAll( (List<String>) filterResults.values );
			}

			notifyDataSetChanged();
		}
	}

}
