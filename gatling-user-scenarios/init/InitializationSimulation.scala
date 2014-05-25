package initialization

import com.excilys.ebi.gatling.core.Predef._
import com.excilys.ebi.gatling.http.Predef._
import com.excilys.ebi.gatling.jdbc.Predef._
import com.excilys.ebi.gatling.http.Headers.Names._
import akka.util.duration._
import bootstrap._

class InitializationSimulation extends Simulation {

	val httpConf = httpConfig
		.baseURL("http://localhost:8080")
		.acceptCharsetHeader("ISO-8859-1,utf-8;q=0.7,*;q=0.7")
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
		.disableFollowRedirect

	val headers_JSON = Map(
		"Accept" -> "application/json, */*; q=0.01",
		"Keep-Alive" -> "115")
        val csvFeed = csv("user_information.csv")

	val scn = scenario("Initialization")
		.repeat(csvFeed.length){
		feed(csvFeed)
			  .exec(http("createUsers")
				.post("/users")
				.param("username", "${username}")
				.param("age", "${theage}")
				.headers(headers_JSON)
				.check(status.is(201)))
	}
	setUp(scn.users(1).ramp(10).protocolConfig(httpConf))
}
