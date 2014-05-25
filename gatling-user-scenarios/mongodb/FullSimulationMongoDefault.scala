package initialization

import com.excilys.ebi.gatling.core.Predef._
import com.excilys.ebi.gatling.http.Predef._
import com.excilys.ebi.gatling.jdbc.Predef._
import com.excilys.ebi.gatling.http.Headers.Names._
import akka.util.duration._
import bootstrap._

class FullSimulationMongoDefault extends Simulation {

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

	val csvFeed = csv("user_information.csv").circular

	val rScn = scenario("Reads")
                .during(150 seconds) {
			feed(csvFeed).exec(
			http("read Users")
				.get("/users/${username}?readConf=PRIMARY")
				.headers(headers_JSON)
				.check(status.is(200)))
		}

	val wScn = scenario("Writes")
                .during(150 seconds) {
			feed(csvFeed).exec(
			http("createUsers")
				.post("/users")
				.param("username", "${username}")
				.param("age", "${theage}")
				.headers(headers_JSON)
				.check(status.is(201)))
		}
	setUp(
  		rScn.users(1).protocolConfig(httpConf),
  		wScn.users(1).protocolConfig(httpConf)
	)
}
