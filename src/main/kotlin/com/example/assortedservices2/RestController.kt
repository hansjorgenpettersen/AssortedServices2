package com.example.assortedservices2

import com.example.assortedservices2.DataReciver.DataRevicer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class RestController {

    var homeIp = ""
    var powerUse = "0"
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
    @CrossOrigin(origins = ["185.7.61.179:80"])
    @GetMapping ("api/getNodeRedData")
    fun getNodeRedData():DataRevicer {
        return recentData
    }
    @CrossOrigin(origins = ["185.7.61.179:80"])
    @GetMapping ("api/power/setLivePowerUsage")
    fun setLivePowerUsage(@RequestParam liveUsage:String){
        powerUse = liveUsage
        println("Live Power Usage: " + liveUsage)
    }
    @GetMapping ("api/power/live")
    fun apiPowerLive():String {
        return powerUse
    }
}