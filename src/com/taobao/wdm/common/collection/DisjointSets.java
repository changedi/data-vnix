package com.taobao.wdm.common.collection;
/**
 * 并查集简单实现
 * @author Jia Yu
 *
 */
public class DisjointSets				
{
	public DisjointSets( int numElements )
	{
		s = new int [ numElements ];
		for( int i = 0; i < s.length; i++ )
			s[ i ] = -1;
	}

	public void union( int root1, int root2 )
	{
		assertIsRoot( root1 );
		assertIsRoot( root2 );
		if( root1 == root2 )
			throw new IllegalArgumentException( "Union: root1 == root2 " + root1 );

		if( s[ root2 ] < s[ root1 ] )  
			s[ root1 ] = root2;        
		else
		{
			if( s[ root1 ] == s[ root2 ] )
				s[ root1 ]--;          
			s[ root2 ] = root1;       
		}
	}

	public int find( int x )
	{
		assertIsItem( x );
		if( s[ x ] < 0 )
			return x;
		else
			return s[ x ] = find( s[ x ] );
	}

	private int [ ] s;


	private void assertIsRoot( int root )
	{
		assertIsItem( root );
		if( s[ root ] >= 0 )
			throw new IllegalArgumentException( "Union: " + root + " not a root" );
	}

	private void assertIsItem( int x )
	{
		if( x < 0 || x >= s.length )
			throw new IllegalArgumentException( "Disjoint sets: " + x + " not an item" );       
	}

}
