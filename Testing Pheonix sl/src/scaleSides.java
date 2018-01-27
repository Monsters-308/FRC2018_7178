
public class scaleSides 
{
	public static boolean scaleRed;
	public static boolean switchRed;
	
	public scaleSides(boolean swi, boolean sc)
	{
		scaleRed = sc;
		switchRed = swi;
	}
	
	public String scaleSide()
	{
		if(scaleRed == true)
		{
			return "scaleRed";
		}
		else
		{
			return "scaleBlue";
		}
	}
	
	public String switchSide()
	{
		if(switchRed == true)
		{
			return "switchRed";
		}
		else
		{
			return "switchBlue";
		}
	}
}
