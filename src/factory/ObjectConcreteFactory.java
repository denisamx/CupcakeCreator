package factory;
import kitchen.BakingPowder;
import kitchen.Bowl;
import kitchen.Butter;
import kitchen.Egg;
import kitchen.Flour;
import kitchen.Milk;
import kitchen.Object;
import kitchen.Oven;
import kitchen.Sugar;
import kitchen.Tray;
import kitchen.Vanilla;
import main.CupcakePanel;

// Factory class that creates objects for clients
public class ObjectConcreteFactory extends ObjectFactory {
	
	//creates the kitchen object that is specified
	@Override
	public Object createObject(String type) {
		if (type.equals("bowl"))
			return new Bowl (CupcakePanel.PAN_WIDTH/2-280, CupcakePanel.PAN_HEIGHT/2, 1);
		else if (type.equals("oven"))
			return new Oven (CupcakePanel.PAN_WIDTH/2+180, CupcakePanel.PAN_HEIGHT/3+80, 1);
		else if (type.equals("flour"))
			return new Flour (CupcakePanel.PAN_WIDTH/2-300, CupcakePanel.PAN_HEIGHT/2+100, 1);
		else if (type.equals("bakingPowder"))
			return new BakingPowder (CupcakePanel.PAN_WIDTH/2-300, CupcakePanel.PAN_HEIGHT/2+100, 1);
		else if (type.equals("sugar"))
			return new Sugar (CupcakePanel.PAN_WIDTH/2-300, CupcakePanel.PAN_HEIGHT/2+100, 1);
		else if (type.equals("egg"))
			return new Egg (CupcakePanel.PAN_WIDTH/2-300, CupcakePanel.PAN_HEIGHT/2+100, 1);
		else if (type.equals("milk"))
			return new Milk (CupcakePanel.PAN_WIDTH/2-300, CupcakePanel.PAN_HEIGHT/2+100, 1);
		else if (type.equals("butter"))
			return new Butter (CupcakePanel.PAN_WIDTH/2-300, CupcakePanel.PAN_HEIGHT/2+100, 1);
		else if (type.equals("vanilla"))
			return new Vanilla (CupcakePanel.PAN_WIDTH/2-300, CupcakePanel.PAN_HEIGHT/2+100, 1);
		else if (type.equals("tray"))
			return new Tray (CupcakePanel.PAN_WIDTH/2-370, CupcakePanel.PAN_HEIGHT/2+100, 1);

		
		else return null;
	}
}
