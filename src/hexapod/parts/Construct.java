package hexapod.parts;

import hexapod.geometry.Base;
import hexapod.geometry.Cone;

public class Construct {

	private final Base b;
	private final Cone[] simpleObjects;
	private final Construct[] constructs;
	
	public Construct(Base b, Cone... obj) {
		this.b = b;
		this.simpleObjects = obj;
		this.constructs = new Construct[0];
	}
	
	public Construct(Base b, Construct... c) {
		this.b = b;
		this.simpleObjects = new Cone[0];
		this.constructs = c;
	}
	
	public Construct(Base b, Cone[] obj, Construct[] c) {
		this.b = b;
		this.simpleObjects = obj;
		this.constructs = c;
	}
	
	public int numberOfCones() {
		return simpleObjects.length;
	}
	
	public Cone getCone(int index) {
		return simpleObjects[index].setBase(b.toAbsdCoords(simpleObjects[index].b));
	}
	
	public int numberOfConstructs() {
		return constructs.length;
	}
	
	public Construct getConstruct(int index) {
		return constructs[index].setBase(b.toAbsdCoords(constructs[index].b));
	}
	
	public Construct setBase(Base b) {
		return new Construct(b, simpleObjects, constructs);
	}
	
}
