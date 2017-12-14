package streaming.data

object UberPickup {

  /** Base marker trait. */
  @SerialVersionUID(1L)
  sealed trait UberPickup extends Serializable

  case class RawUberPickupData(
                             dt: String ,
                             lat: Double,
                             lon: Double,
                             base: String) extends UberPickup

  object RawUberPickupData {
    def apply(array: Array[String]): RawUberPickupData = {
      RawUberPickupData(
        dt = array(0),
        lat = array(1).toDouble,
        lon = array(2).toDouble,
        base = array(3))
    }
  }

  trait UberPickupAggregate extends UberPickup with Serializable {
    def df: String
  }

  trait Predication extends UberPickupAggregate

  case class DailyPrediction(df: String,
                                year: Int,
                                month: Int,
                                day: Int,
                                predication: Double) extends Predication

}
