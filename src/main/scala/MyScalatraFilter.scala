package com.example

import org.scalatra._
import java.net.URL
import scalate.ScalateSupport

import com.mongodb._
import com.mongodb.casbah.Imports._
import scala.xml._

class MyScalatraFilter extends ScalatraFilter with ScalateSupport {
  
  val mongo = MongoConnection()
  val coll = mongo("graffito")("tags")
  
  get("/") {
    redirect("/tags")
  }

  post("/tags") {
    val builder = MongoDBObject.newBuilder
    
    builder += "location" -> params("location")
    builder += "tag" -> params("tag")
     
    coll += builder.result.asDBObject
    redirect("/tags")
  }


  get("/tags") {
    <html>
    <head>
      <title>graffito</title>
      <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;"/>
      <link rel="apple-touch-icon" href="/iui/iui-logo-touch-icon.png" />
      <meta name="apple-touch-fullscreen" content="YES" />
      <link href="/iui/iui.css" rel="stylesheet" type="text/css" />
      <script type="application/x-javascript" src="/iui/iui.js"></script>
      <script type="text/javascript" src="/js/location.js"></script>
      <link href="/style.css" rel="stylesheet" type="text/css" />
      
    </head>

    <body>
    
      <div class="toolbar">
        <h1 id="pageTitle"></h1>
        <a class="button" href="#scrawl">Tag!</a>
      </div>
      
      <ul id="home" title="graffito" selected="true">
        {var tag_msgs: List[com.mongodb.casbah.Imports.DBObject] = List()
        for (i <- coll) { tag_msgs = i :: tag_msgs } 
        for (l <- tag_msgs) yield <li> <i>         
          {l.getOrElse("tag", "???")} </i>                       
         <div class="location">{l.getOrElse("location", "???")}</div></li>}            
      </ul> 
      
      <form id="scrawl" class="dialog" method="post" action="/tags">
        <script language="javascript">
          logLocation();
          setTimeout(logLocation, 5000);
        </script>
        
        <fieldset>
        
        <input type="hidden" name="location" />
        <input type="text" name="tag" />
        
        <a class="button leftButton" type="cancel">Cancel</a>
        <a class="button blueButton" type="submit">Tag!</a>
        </fieldset>
        
      </form>
      
      
    </body>
    </html>
  }
  

  notFound {
    // If no route matches, then try to render a Scaml template
    val templateBase = requestPath match {
      case s if s.endsWith("/") => s + "index"
      case s => s
    }
    val templatePath = "/WEB-INF/scalate/templates/" + templateBase + ".scaml"
    servletContext.getResource(templatePath) match {
      case url: URL => 
        contentType = "text/html"
        templateEngine.layout(templatePath)
      case _ => 
        filterChain.doFilter(request, response)
    } 
  }
}
