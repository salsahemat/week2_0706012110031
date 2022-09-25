package Model

import kotlinx.android.parcel.Parcelize

class Sapi(override var id:Int, override var nama:String?, override var jenis:String?,override var usia:Int?): Animals(0,"","",0) {
    override var animalsound: String = "Moo... Moo..."
    override var animalsfood: String = "You feed your cow grass!"

}