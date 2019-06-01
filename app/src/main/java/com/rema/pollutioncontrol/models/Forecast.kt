package com.rema.pollutioncontrol.models

import org.joda.time.DateTime
import java.io.Serializable

class Forecast(var date: DateTime, var weather: Weather):Serializable {
}