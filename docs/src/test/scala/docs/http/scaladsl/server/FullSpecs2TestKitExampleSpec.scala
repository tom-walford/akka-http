/*
 * Copyright (C) 2009-2017 Lightbend Inc. <http://www.lightbend.com>
 */

package docs.http.scaladsl.server

// format: OFF

//#source-quote

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.Specs2RouteTest
import akka.http.scaladsl.server._
import Directives._
import org.specs2.mutable.Specification

class FullSpecs2TestKitExampleSpec extends Specification with Specs2RouteTest {

  val smallRoute =
    get {
      pathSingleSlash {
        complete {
          "Captain on the bridge!"
        }
      } ~
        path("ping") {
          complete("PONG!")
        }
    }

  "The service" should {

    "return a greeting for GET requests to the root path" in {
      // tests:
      Get() ~> smallRoute ~> check {
        responseAs[String] === "Captain on the bridge!"
      }
    }

    "return a 'PONG!' response for GET requests to /ping" in {
      // tests:
      Get("/ping") ~> smallRoute ~> check {
        responseAs[String] === "PONG!"
      }
    }

    "leave GET requests to other paths unhandled" in {
      // tests:
      Get("/kermit") ~> smallRoute ~> check {
        handled should beFalse
      }
    }

    "return a MethodNotAllowed error for PUT requests to the root path" in {
      // tests:
      Put() ~> Route.seal(smallRoute) ~> check {
        status === StatusCodes.MethodNotAllowed
        responseAs[String] === "HTTP method not allowed, supported methods: GET"
      }
    }
  }
}
//#source-quote
