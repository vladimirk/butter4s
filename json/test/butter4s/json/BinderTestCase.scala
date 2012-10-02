/*
 * The MIT License
 *
 * Copyright (c) 2010 Vladimir Kirichenko <vladimir.kirichenko@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package butter4s.json

import annotation.{Element, Attribute}
import org.junit.{Test, Assert}
import Assert._
import scala.annotation.target.field
import butter4s.logging.Logging

/**
 * @author Vladimir Kirichenko <vladimir.kirichenko@gmail.com> 
 */
class BinderTestCase extends Logging {
	@Test def bindEnum = {
		assertBind( EnumBean( TestEnum.B ) )
		assertBind( TestEnum.C )
	}

	@Test def bind = assertBind( Bean( "x", 10, Bean2( "y", 15, List( 1, 2, 3 ) ) ) )

	@Test def bindPrimitives = {
		assertBind( true )
		assertBind( false )
		assertBind( null )
		assertBind( 10 )
		assertBind( 10.3 )
		assertBind( 'a' )
		assertBind( 'a )
	}

	@Test def bindList = {
		assertBind( List( true, false ) )
		assertBind( List( BeanGB( BeanGeneric( List( 1, 2, 3 ) ) ), BeanGB( BeanGeneric( List( 4, 5, 6 ) ) ) ) )
	}

	@Test def bindBeanListList = assertBind( ListBean( List( List( 1, 2, 3 ), List( 4, 5, 6 ) ) ) )

	@Test def bindGenericObject = assertBind( BeanGB( BeanGeneric( List( 1, 2, 3 ) ) ) )

	@Test def bindDeepGeneric = assertBind( BeanGB2( BeanGeneric( List( BeanGeneric( List( 1, 2 ) ), BeanGeneric( List( 3, 4 ) ) ) ),
		BeanGeneric( List( BeanGeneric( List( 5, 6 ) ), BeanGeneric( List( 7, 8 ) ) ) ) ) )

	@Test def bindQuote = assertBind( Bean( "\\\n\r\t\"'x", 10, null ) )

	@Test def bindNulls = assertBind( Bean( null, 10, null ) )

	@Test def bindAnyRefField = {
		assertBind( new BeanAnyRef( 'ss ) )
		assertBind( new BeanAnyRef( TestEnum.B ) )
		assertBind( new BeanAnyRef( "str" ) )
		assertBind( new BeanAnyRef( 1 ) )
		assertBind( new BeanAnyRef( EnumBean( TestEnum.B ) ) )
	}

	def assertBind[A: Manifest]( source: A ) = {
		println( "========================================" )
		val json = log.time( "marshal", Binder.marshal( source ) )
		println( "JSON:" )
		println( json )
		val result = log.time( "unmarshal", Binder.unmarshal[A]( json ).get )
		println( "Object:" )
		println( result )
		assertEquals( source, result )
	}

}

case class Bean( @( Attribute@field ) var str: String, @( Attribute@field ) var i: Int, @( Element@field ) var sb2: Bean2 ) {
	def this() = this ( null, 0, null )
}

case class Bean2( @( Element@field ) var s2: String, @( Attribute@field ) var i2: Int, @( Element@field ) list: List[Int] = List() ) {
	def this() = this ( null, 0 )
}

case class ListBean( @( Element@field ) var l: List[List[Int]] ) {
	def this() = this ( List() )
}

case class BeanGeneric[A]( @( Element@field ) var a: A ) {
	def this() = this ( null.asInstanceOf[A] )
}

case class BeanGB( @( Element@field ) var bg: BeanGeneric[List[Int]] ) {
	def this() = this ( null )
}

case class BeanGB2( @( Element@field ) var bg: BeanGeneric[List[BeanGeneric[List[Int]]]],
                    @( Element@field ) var bg2: BeanGeneric[List[BeanGeneric[List[Int]]]] ) {
	def this() = this ( null, null )
}

case class EnumBean( @( Attribute@field ) var v: TestEnum ) {
	def this() = this ( null )
}

case class BeanAnyRef( @( Element@field )( useTypeHint = true ) var a: Any ) {
	def this() = this ( null.asInstanceOf[AnyRef] )
}

