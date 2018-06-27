package com.isinonet.tests

import java.io.{BufferedReader, InputStreamReader}
import java.net.URLEncoder

import com.isinonet.ismartnet.beans.{UAType, Website}
import com.isinonet.ismartnet.mapper.{UATypeMapper, WebsiteMapper}
import com.isinonet.ismartnet.utils.JDBCHelper
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.log4j.Logger
import org.slf4j.LoggerFactory

object T {
  def main(args: Array[String]): Unit = {

    val logger = Logger.getLogger("FILE")
    import scala.collection.JavaConversions._
    val session = JDBCHelper.getSession
    val mapper = session.getMapper(classOf[UATypeMapper])
    val websites = mapper.findAll()

    val httpClient = new DefaultHttpClient


    for(x:UAType <- websites) {
      val get = new HttpGet("http://9n4.cn/?ua=" + URLEncoder.encode(x.getpType, "utf8"))
      val httpResponse = httpClient.execute(get)

      val br = new BufferedReader(new InputStreamReader(httpResponse.getEntity()
        .getContent(), "utf-8"));
      val sb = new StringBuffer();
      sb.append(x.getId)
      var line = "";
      while (line != null) {
        sb.append(line);
        line = br.readLine()
      }
      br.close()
      logger.info(sb.toString)
    }
  }
}
