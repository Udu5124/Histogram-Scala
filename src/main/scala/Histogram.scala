import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import scala.util.control.Breaks._

object Histogram 
{
  def main ( args: Array[ String ] ) 
  {
	val conf = new SparkConf().setAppName("Colour_Count_RGB_Spark")
	val sc = new SparkContext(conf)
	
	val textfile = sc.textFile(args(0))
	val num_elements = 3 * textfile.count()
	var pixel = textfile.flatMap((line) => line.split(","))
	var i = 0;
	
	var pixel_intensity = pixel.map(c => { if (i == (num_elements + 1))  {  break  }
										   else if (i%3 == 0)  {  i=i+1;("1\t"+c,1)  }
										   else if (i%3 == 1)	{  i=i+1;("2\t"+c,1)  }
										   else  {  i=i+1;("3\t"+c,1)  }})	
										   
	var result = pixel_intensity.reduceByKey( (a,b) => a+b ).map( (x) => x._1 + "\t" + x._2 +"\t")
	result.collect().foreach ( println )
    sc.stop()
  }
}
			