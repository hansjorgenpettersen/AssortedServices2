package com.example.assortedservices2

import com.example.assortedservices2.DataReciver.DataRevicer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class RestController {

    var homeIp = ""
    var recentData = DataRevicer()

    private var request: HttpServletRequest? = null

    @Autowired
    fun setRequest(request: HttpServletRequest?) {
        this.request = request
    }
    @GetMapping("/setHomeAddress")
    fun setHomeAddress(): String? {
        var remoteAddr: String? = ""
        if (request != null) {
            remoteAddr = request!!.getHeader("X-FORWARDED-FOR")
            if (remoteAddr == null || "" == remoteAddr) {
                remoteAddr = request!!.remoteAddr
            }
        }

        homeIp = remoteAddr.toString()

        println("Home Address Is: " + remoteAddr)

        return remoteAddr
    }
    @GetMapping("/getHomeIP")
    fun getHomeIP():String {
        return homeIp
    }
    @PostMapping("api/nodeRed/revciver")
    fun nodeRedRevicer(@RequestBody data:DataRevicer) : String {
        recentData = data
        return "Data Recived!"
    }
    @GetMapping ("api/getNodeRedData")
    fun getNodeRedData():DataRevicer {
        return recentData
    }
}