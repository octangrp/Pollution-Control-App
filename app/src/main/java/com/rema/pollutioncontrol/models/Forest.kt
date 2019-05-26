package com.rema.pollutioncontrol.models

import android.graphics.drawable.Drawable
import java.io.Serializable

class Forest(var thumbnail: Int, var name: String, var weather: Weather, var description: String) : Serializable{
}