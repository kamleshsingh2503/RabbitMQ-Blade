package com.rabbitmq.dmo.demorabbitmq;

import com.blade.Blade;

public class DemoApplication {

	public static void main(String[] args) {
		Blade.of().start(DemoApplication.class,args);
	}

}
