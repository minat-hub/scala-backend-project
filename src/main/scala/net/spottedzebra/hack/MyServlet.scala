package net.spottedzebra.hack

import org.scalatra._
import scalate.ScalateSupport
import org.scalatra.CorsSupport

import com.mongodb.casbah.Imports._
import scala.xml._

class MyServlet extends ScalatraServlet with ScalateSupport with CorsSupport {

  val mongo = MongoConnection()
  val col = mongo("blog")("msgs")

  get("/") {
    println(col)
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say <a href="hello-scalate">hello to Scalate</a>.
      </body>
    </html>
  }

  notFound {
    // remove content type in case it was set through an action
    contentType = null
    // Try to render a ScalateTemplate if no route matched
    findTemplate(requestPath) map { path =>
      contentType = "text/html"
      layoutTemplate(path)
    } orElse serveStaticResource() getOrElse resourceNotFound()
  }
}
