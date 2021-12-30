package com.shyroka.engine;
public abstract class AbstractCore
{

    public abstract void update(CoreGame core, float dt);
    public abstract void render(CoreGame core, Renderer render);

}