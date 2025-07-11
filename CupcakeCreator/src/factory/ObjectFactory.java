package factory;
import kitchen.Object;

// abstract class for the factory
public abstract class ObjectFactory {

	public abstract Object createObject(String type);
}
