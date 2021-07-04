package com.example.sendemailactivity.data.model

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class SendEmailHandler(
    val email: String? = null,
    val subject:String? = null
): Thread() {
    public override fun run(){
        var reqParam = URLEncoder.encode("destination", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")
        reqParam += "&" + URLEncoder.encode("query", "UTF-8") + "=" + URLEncoder.encode(subject, "UTF-8")

        val mURL = URL("http://192.168.0.104:5000/search/getViaEmail?"+reqParam)

        with(mURL.openConnection() as HttpURLConnection) {
            // optional default is GET
            requestMethod = "GET"

            println("URL : $url")
            println("Response Code : $responseCode")

            BufferedReader(InputStreamReader(inputStream)).use {
                val response = StringBuffer()

                var inputLine = it.readLine()
                while (inputLine != null) {
                    response.append(inputLine)
                    inputLine = it.readLine()
                }
                it.close()
                println("Response : $response")
            }
        }
    }



}
