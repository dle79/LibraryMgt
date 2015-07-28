package controller;

public class SysControllerSingleton {

	private static SysControllerSingleton scs = null;

	private SysControllerSingleton() {

	}

	public static SysControllerSingleton getInstance(){
		if(scs == null)
			return new SysControllerSingleton();
		return scs;

	}

}
