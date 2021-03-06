package com.seventhcloud.colorspeed.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.freetype.gwt.FreetypeInjector;
import com.badlogic.gdx.graphics.g2d.freetype.gwt.inject.OnCompletion;
import com.seventhcloud.colorspeed.ColorSpeed;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                // Resizable application, uses available space in browser
                //return new GwtApplicationConfiguration(true);
                // Fixed size application:
                return new GwtApplicationConfiguration(1080/2, 1920/2, true);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new ColorSpeed();
        }

        @Override
        public void onModuleLoad () {
                FreetypeInjector.inject(new OnCompletion() {
                        public void run () {
                                // Replace HtmlLauncher with the class name
                                // If your class is called FooBar.java than the line should be FooBar.super.onModuleLoad();
                                HtmlLauncher.super.onModuleLoad();
                        }
                });
        }
}