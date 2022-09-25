package Model
import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

open abstract class Animals(open val id:Int, open var nama:String?, open var jenis:String?, open var usia:Int? ){
    var imageUri:String = ""
    open var animalsound:String = ""
    open var animalsfood:String = ""
}
