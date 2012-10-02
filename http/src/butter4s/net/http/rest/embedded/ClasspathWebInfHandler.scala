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
package butter4s.net.http.rest.embedded

import org.apache.http.{HttpResponse, HttpRequest}
import org.apache.http.protocol.{HttpContext, HttpRequestHandler}
import butter4s.lang._
import butter4s.io._
import org.apache.http.entity.ByteArrayEntity
import javax.activation.MimetypesFileTypeMap
import butter4s.logging.Logging

/**
 * @author Vladimir Kirichenko <vladimir.kirichenko@gmail.com>
 */

object ClasspathWebInfHandler extends HttpRequestHandler with Logging {
	val mimeTypes = new MimetypesFileTypeMap()

	def handle( req: HttpRequest, resp: HttpResponse, context: HttpContext ) = {
		log.debug( req.getRequestLine )
		getClass.getResourceAsStream( "/WEB-INF" + req.getRequestLine.getUri ) match {
			case null => resp.setStatusCode( 404 )
			case is => resp.setEntity( new ByteArrayEntity( is.readAs[Array[Byte]] ) {
				setContentType( mimeTypes.getContentType( req.getRequestLine.getUri ) )
			} )
		}
	}
}