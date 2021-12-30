package com.shyroka.game;


import com.shyroka.engine.Audio;

public class SoundManager {
    Audio main_menu_music;
    Audio level_music;
    Audio boss_level_music;
    boolean music_load_menu = true;
    boolean music_load_level = true;
    boolean music_load_boss = true;


    public SoundManager() {
        try {
            main_menu_music = new Audio("/Audio/menu.wav");
            level_music = new Audio("/Audio/level.wav");
            boss_level_music = new Audio("/Audio/boss_level.wav");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void update(GameManager gm) {
        if (gm.Menu.Sett.AudioOn) {

            if (!gm.Menu.isRunning) {
                if (music_load_menu) {
                    level_music.stop();
                    boss_level_music.stop();
                    main_menu_music.play();
                    music_load_menu = false;
                }

            } else {
                if (gm.saveLevel == 3) {
                    if (music_load_boss) {
                        level_music.stop();
                        boss_level_music.play();
                        music_load_boss = false;
                    }
                }
                //other levels
                //System.out.println("we are here");
                if (music_load_level) {
                    main_menu_music.stop();
                    level_music.play();
                    music_load_level = false;
                }
            }


        }
        else
        {
            main_menu_music.stop();
            level_music.stop();
            boss_level_music.stop();
        }
    }
}

