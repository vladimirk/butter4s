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
package butter4s.servlet

import javax.servlet.{FilterChain, ServletResponse, ServletRequest}
import butter4s.servlet._

/**
 * @author Vladimir Kirichenko <vladimir.kirichenko@gmail.com> 
 */
trait Filter extends javax.servlet.Filter {
	def destroy = {}

	def doFilter( request: ServletRequest, response: ServletResponse, chain: FilterChain ) = filter( request, response, chain )

	def filter( request: Request, response: Response, chain: Chain ): Unit

	def init( config: javax.servlet.FilterConfig ) = initialize( config )

	def initialize( config: FilterConfig ) = {}
}

class FilterConfig( val impl: javax.servlet.FilterConfig ) extends Parameterizeable