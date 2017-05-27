package com.konieczny_adam;


import com.badlogic.gdx.Game;

public class PongGame extends Game {
	@Override
	public void create() {
		showPongScreen();
	}

	public void showPongScreen()
	{
		setScreen(new PongScreen());
	}
}
