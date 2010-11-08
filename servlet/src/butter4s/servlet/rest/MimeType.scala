/**************************************************************************
 *
 * Copyright (c) Adstream Holdings Pty Ltd
 *
 * This software is the confidential and proprietary information of
 * Adstream Holdings Pty Ltd ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Adstream Holdings Pty Ltd.
 *
 ************************************************************************
 */

package butter4s.servlet.rest

/**
 * @author Vladimir Kirichenko <vladimir.kirichenko@gmail.com> 
 */
object MimeType {
	final val APPLICATION_JSON = "application/json"
	final val APPLICATION_JSON_WRAPPED_VALUE = "application/x-json-wrapped-value"
	final val TEXT_JAVASCRIPT = "text/javascript"
	final val APPLICATION_JAVA_CLASS = Constants.APPLICATION_JAVA_CLASS

	final val jsonTypes = Array( APPLICATION_JSON, APPLICATION_JSON_WRAPPED_VALUE )

	def isJson( mt: String ) = jsonTypes.contains( mt )
}