package com.badlogic.gdx.scenes.box2d.processor;

import com.badlogic.gdx.scenes.box2d.IB2DSListener.IB2DSAddListener;
import com.badlogic.gdx.scenes.box2d.IB2DSListener.IB2DSRemoveListener;

public abstract class  B2DSProcessor implements IB2DSAddListener, IB2DSRemoveListener
{
	public void dispose() {}
}
